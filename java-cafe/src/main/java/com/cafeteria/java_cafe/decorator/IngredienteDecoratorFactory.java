package com.cafeteria.java_cafe.decorator;

import com.cafeteria.java_cafe.decorator.decoradores.Canela;
import com.cafeteria.java_cafe.decorator.decoradores.LeiteDeAveia;
import com.cafeteria.java_cafe.decorator.decoradores.SemAcucar;
import com.cafeteria.java_cafe.model.Ingrediente;

public class IngredienteDecoratorFactory {

    public static Bebida decorar(Bebida bebida, Ingrediente ingrediente) {
        String nome = ingrediente.getNome().toLowerCase();

        return switch (nome) {
            case "leite de aveia" -> new LeiteDeAveia(bebida, ingrediente.getPrecoAdicional());
            case "canela"         -> new Canela(bebida, ingrediente.getPrecoAdicional());
            case "sem açúcar", "sem acucar" -> new SemAcucar(bebida); // Pode não ter custo
            default -> throw new IllegalArgumentException("Ingrediente não suportado: " + nome);
        };
    }
}