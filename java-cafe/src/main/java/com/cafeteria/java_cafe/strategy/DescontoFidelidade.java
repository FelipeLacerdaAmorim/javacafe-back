package com.cafeteria.java_cafe.strategy;

import java.math.BigDecimal;

public class DescontoFidelidade implements DescontoStrategy {

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorOriginal) {
        return valorOriginal.multiply(BigDecimal.valueOf(0.10));
    }
}