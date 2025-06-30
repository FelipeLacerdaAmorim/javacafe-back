package com.cafeteria.java_cafe.model;

import com.cafeteria.java_cafe.model.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private TipoProduto tipo;

    private String categoria;

    private String imagemUrl;

    private boolean ativo = true;
}