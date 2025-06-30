package com.cafeteria.java_cafe.model;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorPago;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodo;

    private BigDecimal descontoAplicado;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}