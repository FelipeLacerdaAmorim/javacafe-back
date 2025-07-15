package com.cafeteria.java_cafe.service;

import com.cafeteria.java_cafe.dto.ProdutoDTO;
import com.cafeteria.java_cafe.dto.ProdutoRequestDTO;
import com.cafeteria.java_cafe.factory.ProdutoFactory;
import com.cafeteria.java_cafe.factory.ProdutoFactoryProvider;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.enums.TipoProduto;
import com.cafeteria.java_cafe.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoFactoryProvider factoryProvider;

    @Autowired
    public CardapioService(ProdutoRepository produtoRepository, ProdutoFactoryProvider factoryProvider) {
        this.produtoRepository = produtoRepository;
        this.factoryProvider = factoryProvider;
    }

    public Produto cadastrarProduto(ProdutoRequestDTO dto, TipoProduto tipo) {
        ProdutoFactory factory = factoryProvider.getFactory(tipo);
        Produto produto = factory.criarProduto(dto);
        return produtoRepository.save(produto);
    }

    public List<ProdutoDTO> listarBebidas() {
        List<Produto> bebidas = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.BEBIDA);
        return bebidas.stream()
                .map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarAcompanhamentos() {
        List<Produto> acompanhamentos = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.ACOMPANHAMENTO);
        return acompanhamentos.stream()
                .map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarSobremesas() {
        List<Produto> sobremesas = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.SOBREMESA);
        return sobremesas.stream()
                .map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarTudo() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()))
                .collect(Collectors.toList());
    }
}