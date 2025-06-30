package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.Suco;

public class SucoFactory extends BebidaFactory {

    @Override
    public Bebida criarBebida() {
        return new Suco();
    }
}