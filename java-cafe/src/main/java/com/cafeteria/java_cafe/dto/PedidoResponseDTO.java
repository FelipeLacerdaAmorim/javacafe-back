package com.cafeteria.java_cafe.dto;

import java.math.BigDecimal;
import com.cafeteria.java_cafe.model.enums.MetodoPagamento;

public record PedidoResponseDTO(Long id, String status, BigDecimal total, MetodoPagamento metodoPagamento) {}