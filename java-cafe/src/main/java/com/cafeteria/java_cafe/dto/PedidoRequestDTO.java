package com.cafeteria.java_cafe.dto;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PedidoRequestDTO(
    @NotEmpty List<@Valid ItemPedidoDTO> itens,
    boolean pagamentoRealizado,
    MetodoPagamento metodoPagamento
) {}