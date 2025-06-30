package com.cafeteria.java_cafe.decorator;

import java.math.BigDecimal;

public class Cha extends Bebida {
    @Override
    public String getDescricao() {
        return "Chá";
    }

    @Override
    public BigDecimal getPreco() {
        return BigDecimal.valueOf(4.5);
    }
}