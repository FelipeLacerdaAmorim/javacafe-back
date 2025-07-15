package com.cafeteria.java_cafe.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal preco;

    private String categoria;

    private String imagemUrl;

}