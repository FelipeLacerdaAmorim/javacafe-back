package com.cafeteria.java_cafe.decorator;

public abstract class BebidaDecorator extends Bebida {
    protected Bebida bebida;

    public BebidaDecorator(Bebida bebida) {
        this.bebida = bebida;
    }
}