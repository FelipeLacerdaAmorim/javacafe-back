package com.cafeteria.java_cafe.decorator;

import java.math.BigDecimal;

public class Cafe extends Bebida {
    @Override
    public String getDescricao() {
        return "Café";
    }

    @Override
    public BigDecimal getPreco() {
        return BigDecimal.valueOf(5.0);
    }
}