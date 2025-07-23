package com.cafeteria.java_cafe.model;

import com.cafeteria.java_cafe.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private BigDecimal total;

    private BigDecimal totalBase;

    private boolean pagamentoRealizado;

    private String observacao;

    private LocalDateTime data;
    private String nomeCliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
}