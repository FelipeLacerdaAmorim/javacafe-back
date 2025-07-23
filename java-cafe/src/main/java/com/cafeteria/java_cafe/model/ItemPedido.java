package com.cafeteria.java_cafe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int quantidade;

    @Setter
    private BigDecimal precoUnitario;

    @Setter
    @ManyToOne
    private Produto produto;

    @Setter
    @ManyToOne
    private Pedido pedido;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_pedido_ingredientes",
            joinColumns = @JoinColumn(name = "item_pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    @Setter
    private List<Ingrediente> ingredientes = new ArrayList<>();

    @Setter
    private BigDecimal subtotal;

}