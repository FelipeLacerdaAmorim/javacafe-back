package com.cafeteria.java_cafe.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String tipo;
    private String descricao;
    private String imagemUrl;
    private List<IngredienteDTO> ingredientesSuportados;

    public ProdutoDTO() {}

    public ProdutoDTO(Long id, String nome, String descricao, BigDecimal preco, String tipo, String imagemUrl, List<IngredienteDTO> ingredientesSuportados) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
        this.imagemUrl = imagemUrl;
        this.ingredientesSuportados = ingredientesSuportados;
    }
}