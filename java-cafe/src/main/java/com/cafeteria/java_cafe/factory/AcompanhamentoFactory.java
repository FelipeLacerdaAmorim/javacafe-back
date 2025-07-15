package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.dto.ProdutoRequestDTO;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.enums.TipoProduto;
import org.springframework.stereotype.Component;

@Component
public class AcompanhamentoFactory implements ProdutoFactory {

    @Override
    public Produto criarProduto(ProdutoRequestDTO dto) {
        return Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .categoria(dto.getCategoria())
                .imagemUrl(dto.getImagemUrl())
                .ativo(true)
                .tipo(TipoProduto.ACOMPANHAMENTO)
                .build();
    }
}