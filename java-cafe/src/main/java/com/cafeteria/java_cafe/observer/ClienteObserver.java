package com.cafeteria.java_cafe.observer;

import com.cafeteria.java_cafe.model.Pedido;

public class ClienteObserver implements Observador {

    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Cliente] Pedido #" + pedido.getId() + " agora está: " + pedido.getStatus());
    }
}