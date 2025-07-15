package com.cafeteria.java_cafe.service;

import com.cafeteria.java_cafe.dto.*;
import com.cafeteria.java_cafe.model.ItemPedido;
import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.repository.PedidoRepository;
import com.cafeteria.java_cafe.repository.ProdutoRepository;
import com.cafeteria.java_cafe.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        if (dto.usuarioId() == null) {
            throw new RuntimeException("Usuário deve estar logado para criar pedido.");
        }

        Usuario cliente = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setStatus(StatusPedido.RECEBIDO);

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

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setTotal(total);

        pedido = pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido.getId(), pedido.getStatus().name(), pedido.getTotal());
    }

    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream()
                .map(pedido -> new PedidoResponseDTO(
                        pedido.getId(),
                        pedido.getStatus().name(),
                        pedido.getTotal()
                ))
                .toList();
    }

}