package com.cafeteria.java_cafe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull Long usuarioId,
        @NotEmpty List<@Valid ItemPedidoDTO> itens,
        boolean pagamentoRealizado,
        String observacao
) {}