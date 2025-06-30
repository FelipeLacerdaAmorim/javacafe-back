package com.cafeteria.java_cafe.observer;

import com.cafeteria.java_cafe.model.Pedido;

public class CozinhaObserver implements Observador {

    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Cozinha] Pedido #" + pedido.getId() + " mudou para: " + pedido.getStatus());
    }

}