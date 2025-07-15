package com.cafeteria.java_cafe.dto;

import java.util.List;

public record PedidoDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens,
        boolean pagamentoRealizado,
        String observacao
) {}