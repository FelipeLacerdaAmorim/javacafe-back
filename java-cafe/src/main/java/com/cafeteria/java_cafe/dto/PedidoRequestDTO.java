package com.cafeteria.java_cafe.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens,
        boolean pagamentoRealizado,
        ClienteTemporarioDTO clienteTemporario
) {}