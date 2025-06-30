package com.cafeteria.java_cafe.decorator;

import java.math.BigDecimal;

public class Suco extends Bebida {
    @Override
    public String getDescricao() {
        return "Suco";
    }

    @Override
    public BigDecimal getPreco() {
        return BigDecimal.valueOf(4.5);
    }
}