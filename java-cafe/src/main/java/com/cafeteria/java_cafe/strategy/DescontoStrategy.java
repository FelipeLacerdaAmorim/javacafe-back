package com.cafeteria.java_cafe.strategy;

import java.math.BigDecimal;

public interface DescontoStrategy {
    BigDecimal calcularDesconto(BigDecimal valorOriginal);
}