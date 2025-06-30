package com.cafeteria.java_cafe.state;

public interface PedidoState {
    void avancar(PedidoContext context);
    String getNomeEstado();
}