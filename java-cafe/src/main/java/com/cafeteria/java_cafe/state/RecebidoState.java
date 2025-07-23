package com.cafeteria.java_cafe.state;

public class RecebidoState implements PedidoState {

    @Override
    public void avancar(PedidoContext context) {
        context.setEstado(new EmPreparoState());
    }

    @Override
    public String getNomeEstado() {
        return "RECEBIDO";
    }
}