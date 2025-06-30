package com.cafeteria.java_cafe.decorator.decoradores;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.BebidaDecorator;

import java.math.BigDecimal;

public class SemAcucar extends BebidaDecorator {

    public SemAcucar(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescricao() {
        return bebida.getDescricao() + ", sem açúcar";
    }

    @Override
    public BigDecimal getPreco() {
        return bebida.getPreco();
    }
}