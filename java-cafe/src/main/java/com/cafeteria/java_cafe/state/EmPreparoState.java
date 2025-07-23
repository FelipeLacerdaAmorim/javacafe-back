package com.cafeteria.java_cafe.state;

public class EmPreparoState implements PedidoState {

    @Override
    public void avancar(PedidoContext context) {
        context.setEstado(new FinalizadoState());
    }

    @Override
    public String getNomeEstado() {
        return "EM_PREPARO";
    }
}