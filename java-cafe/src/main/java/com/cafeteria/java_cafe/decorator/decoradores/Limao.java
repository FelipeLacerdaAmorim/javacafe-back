package com.cafeteria.java_cafe.decorator.decoradores;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.BebidaDecorator;
import java.math.BigDecimal;

public class Limao extends BebidaDecorator {
    private BigDecimal precoAdicional;

    public Limao(Bebida bebida, BigDecimal precoAdicional) {
        super(bebida);
        this.precoAdicional = precoAdicional;
    }

    @Override
    public BigDecimal getPreco() {
        return bebida.getPreco().add(precoAdicional);
    }

    @Override
    public String getDescricao() {
        return bebida.getDescricao() + ", limão";
    }
} 