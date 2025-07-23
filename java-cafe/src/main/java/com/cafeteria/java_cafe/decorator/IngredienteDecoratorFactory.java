package com.cafeteria.java_cafe.decorator;

import com.cafeteria.java_cafe.decorator.decoradores.Canela;
import com.cafeteria.java_cafe.decorator.decoradores.LeiteDeAveia;
import com.cafeteria.java_cafe.decorator.decoradores.SemAcucar;
import com.cafeteria.java_cafe.decorator.decoradores.PitadaDeHortela;
import com.cafeteria.java_cafe.decorator.decoradores.GeloExtra;
import com.cafeteria.java_cafe.decorator.decoradores.Limao;
import com.cafeteria.java_cafe.decorator.decoradores.Mel;
import com.cafeteria.java_cafe.decorator.decoradores.Chantilly;
import com.cafeteria.java_cafe.decorator.decoradores.Chocolate;
import com.cafeteria.java_cafe.decorator.decoradores.AcucarMascavo;
import com.cafeteria.java_cafe.model.Ingrediente;

public class IngredienteDecoratorFactory {

    public static Bebida decorar(Bebida bebida, Ingrediente ingrediente) {
        String nome = ingrediente.getNome().toLowerCase();

        return switch (nome) {
            case "leite de aveia" -> new LeiteDeAveia(bebida, ingrediente.getPrecoAdicional());
            case "canela"         -> new Canela(bebida, ingrediente.getPrecoAdicional());
            case "sem açúcar", "sem acucar" -> new SemAcucar(bebida);
            case "pitada de hortelã", "pitada de hortela" -> new PitadaDeHortela(bebida, ingrediente.getPrecoAdicional());
            case "gelo extra"     -> new GeloExtra(bebida, ingrediente.getPrecoAdicional());
            case "limão", "limao" -> new Limao(bebida, ingrediente.getPrecoAdicional());
            case "mel"            -> new Mel(bebida, ingrediente.getPrecoAdicional());
            case "chantilly"      -> new Chantilly(bebida, ingrediente.getPrecoAdicional());
            case "chocolate"      -> new Chocolate(bebida, ingrediente.getPrecoAdicional());
            case "açúcar mascavo", "acucar mascavo" -> new AcucarMascavo(bebida, ingrediente.getPrecoAdicional());
            default -> throw new IllegalArgumentException("Ingrediente não suportado: " + nome);
        };
    }
}