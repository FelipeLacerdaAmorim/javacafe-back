package com.cafeteria.java_cafe.decorator.decoradores;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.BebidaDecorator;
import java.math.BigDecimal;

public class Mel extends BebidaDecorator {
    private BigDecimal precoAdicional;

    public Mel(Bebida bebida, BigDecimal precoAdicional) {
        super(bebida);
        this.precoAdicional = precoAdicional;
    }

    @Override
    public BigDecimal getPreco() {
        return bebida.getPreco().add(precoAdicional);
    }

    @Override
    public String getDescricao() {
        return bebida.getDescricao() + ", mel";
    }
} 