package com.cafeteria.java_cafe.repository;

import com.cafeteria.java_cafe.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);

    boolean existsByEmail(String attr0);
}