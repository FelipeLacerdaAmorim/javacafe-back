package com.cafeteria.java_cafe.service;

import com.cafeteria.java_cafe.dto.ProdutoDTO;
import com.cafeteria.java_cafe.dto.ProdutoRequestDTO;
import com.cafeteria.java_cafe.factory.ProdutoFactory;
import com.cafeteria.java_cafe.factory.ProdutoFactoryProvider;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.enums.TipoProduto;
import com.cafeteria.java_cafe.repository.ProdutoRepository;
import com.cafeteria.java_cafe.dao.IngredienteDAO;
import com.cafeteria.java_cafe.dto.IngredienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoFactoryProvider factoryProvider;
    private final IngredienteDAO ingredienteDAO;

    @Autowired
    public CardapioService(ProdutoRepository produtoRepository, ProdutoFactoryProvider factoryProvider, IngredienteDAO ingredienteDAO) {
        this.produtoRepository = produtoRepository;
        this.factoryProvider = factoryProvider;
        this.ingredienteDAO = ingredienteDAO;
    }

    public Produto cadastrarProduto(ProdutoRequestDTO dto, TipoProduto tipo) {
        ProdutoFactory factory = factoryProvider.getFactory(tipo);
        Produto produto = factory.criarProduto(dto);
        return produtoRepository.save(produto);
    }

    public List<ProdutoDTO> listarBebidas() {
        List<Produto> bebidas = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.BEBIDA);
        return bebidas.stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getTipo().toString(),
                        produto.getImagemUrl(),
                        ingredienteDAO.buscarIngredientesPorProduto(produto.getId())
                ))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarAcompanhamentos() {
        List<Produto> acompanhamentos = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.ACOMPANHAMENTO);
        return acompanhamentos.stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getTipo().toString(),
                        produto.getImagemUrl(),
                        ingredienteDAO.buscarIngredientesPorProduto(produto.getId())
                ))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarSobremesas() {
        List<Produto> sobremesas = produtoRepository.findByTipoAndAtivoTrue(TipoProduto.SOBREMESA);
        return sobremesas.stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getTipo().toString(),
                        produto.getImagemUrl(),
                        ingredienteDAO.buscarIngredientesPorProduto(produto.getId())
                ))
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarTudo() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getTipo().toString(),
                        produto.getImagemUrl(),
                        ingredienteDAO.buscarIngredientesPorProduto(produto.getId())
                ))
                .collect(Collectors.toList());
    }
}