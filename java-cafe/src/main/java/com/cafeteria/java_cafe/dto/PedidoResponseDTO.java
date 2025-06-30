package com.cafeteria.java_cafe.dto;

import java.math.BigDecimal;

public record PedidoResponseDTO(Long id, String status, BigDecimal total) {}