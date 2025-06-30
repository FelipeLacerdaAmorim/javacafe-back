package com.cafeteria.java_cafe.service;

import com.cafeteria.java_cafe.dto.*;
import com.cafeteria.java_cafe.model.ItemPedido;
import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.model.enums.TipoUsuario;
import com.cafeteria.java_cafe.repository.PedidoRepository;
import com.cafeteria.java_cafe.repository.ProdutoRepository;
import com.cafeteria.java_cafe.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Usuario usuario;

        if (dto.clienteId() != null) {

            usuario = usuarioRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        } else {

            if (!dto.pagamentoRealizado()) {
                throw new RuntimeException("Pagamento obrigatório para pedidos sem login.");
            }

            ClienteTemporarioDTO tempDTO = dto.clienteTemporario();
            if (tempDTO == null || tempDTO.nome() == null || tempDTO.nome().isBlank()) {
                throw new RuntimeException("Dados do cliente temporário são obrigatórios.");
            }

            usuario = new Usuario();
            usuario.setNome(tempDTO.nome());
            usuario.setEmail(tempDTO.email());
            usuario.setTipoUsuario(TipoUsuario.CLIENTE_TEMPORARIO);
            usuario = usuarioRepository.save(usuario);
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
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

    @Transactional
    public PedidoDTO criarPedidoComClienteTemporario(PedidoDTO dto) {

        Usuario cliente = new Usuario();
        cliente.setNome(dto.clienteTemporario().nome());
        cliente.setEmail(dto.clienteTemporario().email());
        cliente.setTipoUsuario(TipoUsuario.CLIENTE_TEMPORARIO);
        cliente = usuarioRepository.save(cliente);

        PedidoRequestDTO pedidoRequestDTO = converterParaPedidoRequestDTO(dto, cliente.getId());

        PedidoResponseDTO pedidoResponse = criarPedido(pedidoRequestDTO);

        return converterParaPedidoDTO(pedidoResponse);
    }

    public PedidoRequestDTO converterParaPedidoRequestDTO(PedidoDTO dto, Long clienteId) {
        return new PedidoRequestDTO(
                clienteId,
                dto.itens(),
                dto.pagamentoRealizado(),
                dto.clienteTemporario()
        );
    }

    public PedidoDTO converterParaPedidoDTO(PedidoResponseDTO responseDTO) {
        return new PedidoDTO(
                null,
                Collections.emptyList(),
                true,
                null
        );
    }
}