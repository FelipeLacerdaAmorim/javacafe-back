package com.cafeteria.java_cafe.dao;

import com.cafeteria.java_cafe.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {

}