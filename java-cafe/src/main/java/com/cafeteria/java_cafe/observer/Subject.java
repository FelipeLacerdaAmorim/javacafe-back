package com.cafeteria.java_cafe.observer;

import com.cafeteria.java_cafe.model.Pedido;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    @Transient
    private final List<Observador> observadores = new ArrayList<>();

    public void adicionarObservador(Observador o) {
        observadores.add(o);
    }

    public void removerObservador(Observador o) {
        observadores.remove(o);
    }

    public void notificarObservadores(Pedido pedido) {
        for (Observador o : observadores) {
            o.atualizar(pedido);
        }
    }
}