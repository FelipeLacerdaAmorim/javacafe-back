package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.decorator.Bebida;
import com.cafeteria.java_cafe.decorator.Cha;

public class ChaFactory extends BebidaFactory {

    @Override
    public Bebida criarBebida() {
        return new Cha();
    }
}