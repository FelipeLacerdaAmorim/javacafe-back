package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.Cafe;

public class CafeFactory extends BebidaFactory {

    @Override
    public Bebida criarBebida() {
        return new Cafe();
    }
}