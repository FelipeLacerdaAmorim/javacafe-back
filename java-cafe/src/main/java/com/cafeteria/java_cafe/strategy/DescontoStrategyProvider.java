package com.cafeteria.java_cafe.strategy;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import com.cafeteria.java_cafe.strategy.DescontoPix;
import com.cafeteria.java_cafe.strategy.DescontoFidelidade;
import com.cafeteria.java_cafe.strategy.DescontoDebito;
import com.cafeteria.java_cafe.strategy.DescontoDinheiro;
import com.cafeteria.java_cafe.strategy.SemDesconto;

public class DescontoStrategyProvider {

    public static DescontoStrategy getStrategy(MetodoPagamento metodo, boolean clienteFiel) {
        return switch (metodo) {
            case PIX -> new DescontoPix();
            case CARTAO_FIDELIDADE -> new DescontoFidelidade();
            case CARTAO_DEBITO -> new DescontoDebito();
            case DINHEIRO -> new DescontoDinheiro();
            default -> new SemDesconto();
        };
    }
}