package com.cafeteria.java_cafe.observer;

import com.cafeteria.java_cafe.model.Pedido;

public interface Observador {
    void atualizar(Pedido pedido);
}