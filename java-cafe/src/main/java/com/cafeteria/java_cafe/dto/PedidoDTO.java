package com.cafeteria.java_cafe.dto;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
    Long id,
    List<ItemPedidoDTO> itens,
    boolean pagamentoRealizado,
    MetodoPagamento metodoPagamento,
    LocalDateTime data,
    String nomeCliente,
    String status,
    BigDecimal totalBase,
    BigDecimal total
) {}