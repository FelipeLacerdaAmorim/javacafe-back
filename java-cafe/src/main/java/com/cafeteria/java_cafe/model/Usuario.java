package com.cafeteria.java_cafe.model;

import com.cafeteria.java_cafe.model.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private boolean fidelidade;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}