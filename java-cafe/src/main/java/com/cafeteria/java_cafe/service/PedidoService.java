package com.cafeteria.java_cafe.service;

import com.cafeteria.java_cafe.dto.*;
import com.cafeteria.java_cafe.model.ItemPedido;
import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.model.Pagamento;
import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import com.cafeteria.java_cafe.repository.PedidoRepository;
import com.cafeteria.java_cafe.repository.ProdutoRepository;
import com.cafeteria.java_cafe.repository.UsuarioRepository;
import com.cafeteria.java_cafe.repository.PagamentoRepository;
import com.cafeteria.java_cafe.repository.IngredienteRepository;
import com.cafeteria.java_cafe.strategy.DescontoStrategy;
import com.cafeteria.java_cafe.strategy.DescontoStrategyProvider;
import com.cafeteria.java_cafe.model.Ingrediente;
import com.cafeteria.java_cafe.dto.IngredienteDTO;
import com.cafeteria.java_cafe.observer.PedidoSubject;
import com.cafeteria.java_cafe.observer.PedidoWebSocketObserver;
import com.cafeteria.java_cafe.state.PedidoContext;
import com.cafeteria.java_cafe.command.CancelarPedidoCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final PedidoSubject pedidoSubject;
    private final PedidoWebSocketObserver pedidoWebSocketObserver;

    private List<ItemPedidoDTO> mapItensToDTO(List<ItemPedido> itens) {
        return itens.stream().map(item -> new ItemPedidoDTO(
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getPrecoUnitario(),
                item.getQuantidade(),
                item.getIngredientes() != null ? item.getIngredientes().stream().map(Ingrediente::getId).toList() : List.of(),
                item.getIngredientes() != null ? item.getIngredientes().stream().map(ing -> new IngredienteDTO(ing.getId(), ing.getNome(), ing.getPrecoAdicional())).toList() : List.of()
        )).toList();
    }

    public PedidoDTO toPedidoDTO(Pedido pedido, MetodoPagamento metodo) {
        return new PedidoDTO(
            pedido.getId(),
            mapItensToDTO(pedido.getItens()),
            true,
            metodo,
            pedido.getData(),
            pedido.getNomeCliente(),
            pedido.getStatus() != null ? pedido.getStatus().name() : null,
            pedido.getTotalBase(),
            pedido.getTotal()
        );
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto, Long usuarioId) {
        Usuario cliente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setData(LocalDateTime.now());
        pedido.setNomeCliente(cliente.getNome());
        BigDecimal total = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + itemDTO.produtoId()));
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);
            // Associar ingredientes selecionados
            if (itemDTO.ingredientesIds() != null && !itemDTO.ingredientesIds().isEmpty()) {
                List<Ingrediente> ingredientes = ingredienteRepository.findAllById(itemDTO.ingredientesIds());
                item.setIngredientes(ingredientes);
            }
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
            itens.add(item);
        }
        pedido.setItens(itens);
        pedido.setTotalBase(total);
        // Aplicar desconto via Strategy
        MetodoPagamento metodo = dto.metodoPagamento();
        DescontoStrategy strategy = DescontoStrategyProvider.getStrategy(metodo, false);
        BigDecimal desconto = strategy.calcularDesconto(total);
        BigDecimal valorFinal = total.subtract(desconto);
        pedido.setTotal(valorFinal);
        // Registrar pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setValorPago(valorFinal);
        pagamento.setMetodo(metodo);
        pagamento.setDescontoAplicado(desconto);
        pagamento.setPedido(pedido);
        pedido.setPagamentoRealizado(true);
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pagamento);
        // Notificar observers (WebSocket) sobre novo pedido
        pedidoWebSocketObserver.setEvento("NOVO_PEDIDO");
        pedidoSubject.limparObservadores();
        pedidoSubject.adicionarObservador(pedidoWebSocketObserver);
        pedidoSubject.notificarObservadores(pedido);
        Pagamento pagamentoPedido = pagamentoRepository.findByPedidoId(pedido.getId()).stream().findFirst().orElse(null);
        MetodoPagamento metodoPedido = pagamentoPedido != null ? pagamentoPedido.getMetodo() : null;
        return new PedidoResponseDTO(pedido.getId(), pedido.getStatus().name(), pedido.getTotal(), metodoPedido);
    }

    public List<PedidoDTO> listarPedidosDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> {
            Pagamento pagamento = pagamentoRepository.findByPedidoId(pedido.getId()).stream().findFirst().orElse(null);
            MetodoPagamento metodo = pagamento != null ? pagamento.getMetodo() : null;
            return toPedidoDTO(pedido, metodo);
        }).toList();
    }

    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> {
                    Pagamento pagamento = pagamentoRepository.findByPedidoId(pedido.getId()).stream().findFirst().orElse(null);
                    MetodoPagamento metodo = pagamento != null ? pagamento.getMetodo() : null;
                    return new PedidoResponseDTO(
                        pedido.getId(),
                        pedido.getStatus().name(),
                        pedido.getTotal(),
                        metodo
                    );
                })
                .toList();
    }

    public List<PedidoResponseDTO> listarPedidosPorUsuario(Long usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream()
                .map(pedido -> {
                    Pagamento pagamento = pagamentoRepository.findByPedidoId(pedido.getId()).stream().findFirst().orElse(null);
                    MetodoPagamento metodo = pagamento != null ? pagamento.getMetodo() : null;
                    return new PedidoResponseDTO(
                        pedido.getId(),
                        pedido.getStatus().name(),
                        pedido.getTotal(),
                        metodo
                    );
                })
                .toList();
    }

    public List<Pedido> buscarPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido atualizarStatusPedido(Long pedidoId, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        PedidoContext context = new PedidoContext(pedido);
        // Só permite avançar para o próximo estado válido
        String estadoAtual = pedido.getStatus().name();
        context.avancarEstado();
        String estadoAposAvanco = context.getPedido().getStatus().name();
        if (!estadoAposAvanco.equals(novoStatus)) {
            throw new RuntimeException("Transição de estado inválida: " + estadoAtual + " -> " + novoStatus);
        }
        Pedido atualizado = pedidoRepository.save(context.getPedido());
        // Notificar observers (WebSocket) sobre atualização de status
        pedidoWebSocketObserver.setEvento("STATUS_ATUALIZADO");
        pedidoSubject.limparObservadores();
        pedidoSubject.adicionarObservador(pedidoWebSocketObserver);
        pedidoSubject.notificarObservadores(atualizado);
        return atualizado;
    }

    public Pedido avancarStatusPedido(Long pedidoId) {
        
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        PedidoContext context = new PedidoContext(pedido);
        context.avancarEstado();
        Pedido atualizado = pedidoRepository.save(context.getPedido());
        // Notificar observers (WebSocket) sobre atualização de status
        pedidoWebSocketObserver.setEvento("STATUS_ATUALIZADO");
        pedidoSubject.limparObservadores();
        pedidoSubject.adicionarObservador(pedidoWebSocketObserver);
        pedidoSubject.notificarObservadores(atualizado);
        
        return atualizado;
    }

    @Transactional
    public void deletarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        // Deletar pagamentos associados
        pagamentoRepository.findByPedidoId(pedidoId).forEach(pagamentoRepository::delete);
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId, Usuario usuario) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        CancelarPedidoCommand comando = new CancelarPedidoCommand(pedido, usuario);
        comando.executar();
        pedidoRepository.save(pedido);
        // Notificar observers (WebSocket) sobre cancelamento
        pedidoWebSocketObserver.setEvento("PEDIDO_CANCELADO");
        pedidoSubject.limparObservadores();
        pedidoSubject.adicionarObservador(pedidoWebSocketObserver);
        // Se desejar, pode adicionar observers específicos também:
        // pedidoSubject.adicionarObservador(new CozinhaObserver());
        // pedidoSubject.adicionarObservador(new ClienteObserver());
        pedidoSubject.notificarObservadores(pedido);
    }

    public MetodoPagamento getMetodoPagamentoByPedido(Pedido pedido) {
        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedido.getId()).stream().findFirst().orElse(null);
        return pagamento != null ? pagamento.getMetodo() : null;
    }
}