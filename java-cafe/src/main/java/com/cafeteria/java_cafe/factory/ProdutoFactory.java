package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.dto.ProdutoRequestDTO;
import com.cafeteria.java_cafe.model.Produto;

public interface ProdutoFactory {
    Produto criarProduto(ProdutoRequestDTO dto);
}