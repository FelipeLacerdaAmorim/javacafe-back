package com.cafeteria.java_cafe.state;

public class EntregueState implements PedidoState {

    @Override
    public void avancar(PedidoContext context) {
        System.out.println("Pedido já foi entregue. Nenhuma transição adicional.");
    }

    @Override
    public String getNomeEstado() {
        return "ENTREGUE";
    }
}