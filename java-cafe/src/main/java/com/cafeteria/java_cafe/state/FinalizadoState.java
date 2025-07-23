package com.cafeteria.java_cafe.state;

public class FinalizadoState implements PedidoState {

    @Override
    public void avancar(PedidoContext context) {
        // Estado final, não avança mais
        System.out.println("Pedido já está finalizado. Nenhuma transição adicional.");
    }

    @Override
    public String getNomeEstado() {
        return "FINALIZADO";
    }
}