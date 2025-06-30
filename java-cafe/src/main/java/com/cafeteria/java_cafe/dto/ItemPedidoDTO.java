package com.cafeteria.java_cafe.dto;

import java.math.BigDecimal;
import java.util.List;

public record ItemPedidoDTO(
        Long produtoId,
        String produtoNome,
        BigDecimal precoUnitario,
        int quantidade,
        List<Long> ingredientesIds
) {}