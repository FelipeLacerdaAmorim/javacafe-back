package com.cafeteria.java_cafe.bo;

import com.cafeteria.java_cafe.dto.ItemPedidoDTO;
import com.cafeteria.java_cafe.dto.PedidoRequestDTO;
import com.cafeteria.java_cafe.model.*;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoBO {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Pedido criarPedido(PedidoRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Long> idsProdutos = dto.itens().stream()
                .map(ItemPedidoDTO::produtoId)
                .collect(Collectors.toList());

        List<Produto> produtos = produtoRepository.findAllById(idsProdutos);

        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        Pedido pedido = new Pedido(); // inicializa para poder associar nos itens
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.RECEBIDO);

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtos.stream()
                    .filter(p -> p.getId().equals(itemDTO.produtoId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);

            itens.add(item);

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
        }

        pedido.setItens(itens);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
}