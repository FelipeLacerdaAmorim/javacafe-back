package com.cafeteria.java_cafe.repository;

import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.enums.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByTipoAndAtivoTrue(TipoProduto tipo);
}