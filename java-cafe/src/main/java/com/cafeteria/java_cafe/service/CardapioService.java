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
import org.springframework.transaction.annotation.Transactional;
import com.cafeteria.java_cafe.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import com.cafeteria.java_cafe.model.Ingrediente;

@Service
public class CardapioService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoFactoryProvider factoryProvider;
    private final IngredienteDAO ingredienteDAO;
    private final IngredienteRepository ingredienteRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CardapioService(ProdutoRepository produtoRepository, ProdutoFactoryProvider factoryProvider, IngredienteDAO ingredienteDAO, IngredienteRepository ingredienteRepository, JdbcTemplate jdbcTemplate) {
        this.produtoRepository = produtoRepository;
        this.factoryProvider = factoryProvider;
        this.ingredienteDAO = ingredienteDAO;
        this.ingredienteRepository = ingredienteRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Produto cadastrarProduto(ProdutoRequestDTO dto, TipoProduto tipo) {
        ProdutoFactory factory = factoryProvider.getFactory(tipo);
        Produto produto = factory.criarProduto(dto);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) {
            return null;
        }
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setImagemUrl(dto.getImagemUrl());
        if (dto.getCategoria() != null) {
            try {
                produto.setTipo(TipoProduto.valueOf(dto.getCategoria().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Categoria inválida, ignora alteração
            }
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public boolean atualizarIngredientesProduto(Long produtoId, List<Long> ingredientesIds) {
        if (!produtoRepository.existsById(produtoId)) return false;
        // Remove todas as associações atuais
        jdbcTemplate.update("DELETE FROM produto_ingrediente WHERE produto_id = ?", produtoId);
        // Adiciona as novas associações
        if (ingredientesIds != null) {
            for (Long ingredienteId : ingredientesIds) {
                jdbcTemplate.update("INSERT INTO produto_ingrediente (produto_id, ingrediente_id) VALUES (?, ?)", produtoId, ingredienteId);
            }
        }
        return true;
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

    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }
}