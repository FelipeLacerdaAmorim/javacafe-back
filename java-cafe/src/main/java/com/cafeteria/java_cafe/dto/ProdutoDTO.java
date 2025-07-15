package com.cafeteria.java_cafe.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;

    public ProdutoDTO() {}

    public ProdutoDTO(Long id, String nome, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
}