package com.cafeteria.java_cafe.bo;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.IngredienteDecoratorFactory;
import com.cafeteria.java_cafe.dto.ItemPedidoDTO;
import com.cafeteria.java_cafe.dto.PedidoRequestDTO;
import com.cafeteria.java_cafe.dto.PedidoResponseDTO;
import com.cafeteria.java_cafe.factory.BebidaFactory;
import com.cafeteria.java_cafe.factory.FabricaBebidasProvider;
import com.cafeteria.java_cafe.model.*;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.observer.ClienteObserver;
import com.cafeteria.java_cafe.observer.CozinhaObserver;
import com.cafeteria.java_cafe.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoBO {

    private final UsuarioRepository usuarioRepo; // antes era ClienteRepository
    private final ProdutoRepository produtoRepo;
    private final IngredienteRepository ingredienteRepo;
    private final PedidoRepository pedidoRepo;

    public PedidoBO(
            UsuarioRepository usuarioRepo,
            ProdutoRepository produtoRepo,
            IngredienteRepository ingredienteRepo,
            PedidoRepository pedidoRepo
    ) {
        this.usuarioRepo = usuarioRepo;
        this.produtoRepo = produtoRepo;
        this.ingredienteRepo = ingredienteRepo;
        this.pedidoRepo = pedidoRepo;
    }

    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        // Buscar cliente
        Usuario usuario = usuarioRepo.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.RECEBIDO);

        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepo.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            BebidaFactory factory = FabricaBebidasProvider.getFactory(produto.getTipo());
            Bebida bebida = factory.criarBebida();

            for (Long ingId : itemDTO.ingredientesIds()) {
                Ingrediente ing = ingredienteRepo.findById(ingId)
                        .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));
                bebida = IngredienteDecoratorFactory.decorar(bebida, ing);
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setIngredientes(resolverIngredientes(itemDTO.ingredientesIds()));
            item.setQuantidade(itemDTO.quantidade());

            BigDecimal subtotal = bebida.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            item.setSubtotal(subtotal);

            total = total.add(subtotal);
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setTotal(total);

        ClienteObserver clienteObs = new ClienteObserver();
        CozinhaObserver cozinhaObs = new CozinhaObserver();
        pedido.adicionarObservador(clienteObs);
        pedido.adicionarObservador(cozinhaObs);
        pedido.notificarObservadores(pedido);

        Pedido salvo = pedidoRepo.save(pedido);
        return new PedidoResponseDTO(salvo.getId(), salvo.getStatus().name(), salvo.getTotal());
    }

    private List<Ingrediente> resolverIngredientes(List<Long> ids) {
        return ids.stream()
                .map(id -> ingredienteRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado: " + id)))
                .toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return new PedidoResponseDTO(pedido.getId(), pedido.getStatus().name(), pedido.getTotal());
    }

    public void atualizarStatus(Pedido pedido, StatusPedido novoStatus) {
        pedido.setStatus(novoStatus);
        pedido.notificarObservadores(pedido);
        pedidoRepo.save(pedido);
    }
}