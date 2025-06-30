package com.cafeteria.java_cafe.strategy;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;

public class DescontoStrategyProvider {

    public static DescontoStrategy getStrategy(MetodoPagamento metodo, boolean clienteFiel) {
        return switch (metodo) {
            case PIX -> new DescontoPix();
            case CARTAO_FIDELIDADE -> clienteFiel ? new DescontoFidelidade() : new SemDesconto();
            default -> new SemDesconto();
        };
    }
}