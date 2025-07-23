package com.cafeteria.java_cafe.dto;

import java.math.BigDecimal;

public class IngredienteDTO {
    private Long id;
    private String nome;
    private BigDecimal precoAdicional;

    public IngredienteDTO() {}

    public IngredienteDTO(Long id, String nome, BigDecimal precoAdicional) {
        this.id = id;
        this.nome = nome;
        this.precoAdicional = precoAdicional;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoAdicional() { return precoAdicional; }
    public void setPrecoAdicional(BigDecimal precoAdicional) { this.precoAdicional = precoAdicional; }
}
