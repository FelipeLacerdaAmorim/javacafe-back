package com.cafeteria.java_cafe.strategy;

import java.math.BigDecimal;

public class SemDesconto implements DescontoStrategy {

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorOriginal) {
        return BigDecimal.ZERO;
    }

}