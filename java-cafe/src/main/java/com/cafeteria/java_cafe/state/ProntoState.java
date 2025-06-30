package com.cafeteria.java_cafe.state;

public class ProntoState implements PedidoState {

    @Override
    public void avancar(PedidoContext context) {
        context.setEstado(new EntregueState());
    }

    @Override
    public String getNomeEstado() {
        return "ENTREGUE";
    }
}