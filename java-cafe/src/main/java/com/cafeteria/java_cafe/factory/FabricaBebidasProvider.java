package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.model.enums.TipoProduto;

public class FabricaBebidasProvider {

    public static BebidaFactory getFactory(TipoProduto tipo) {
        return switch (tipo) {
            case CAFE -> new CafeFactory();
            case CHA -> new ChaFactory();
            case SUCO -> new SucoFactory();
        };
    }
}