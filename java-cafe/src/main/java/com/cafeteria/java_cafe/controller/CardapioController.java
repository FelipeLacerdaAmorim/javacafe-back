package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.ProdutoDTO;
import com.cafeteria.java_cafe.dto.ProdutoRequestDTO;
import com.cafeteria.java_cafe.model.Produto;
import com.cafeteria.java_cafe.model.enums.TipoProduto;
import com.cafeteria.java_cafe.service.CardapioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseEntity<List<ProdutoDTO>> listarTudo() {
        List<ProdutoDTO> produtos = cardapioService.listarTudo();
        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/sobremesa")
    public ResponseEntity<List<ProdutoDTO>> listarSobremesas() {
        List<ProdutoDTO> sobremesas = cardapioService.listarSobremesas();
        return ResponseEntity.ok(sobremesas);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/acompanhamento")
    public ResponseEntity<List<ProdutoDTO>> listarAcompanhamentos() {
        List<ProdutoDTO> acompanhamentos = cardapioService.listarAcompanhamentos();
        return ResponseEntity.ok(acompanhamentos);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/bebida")
    public ResponseEntity<List<ProdutoDTO>> listarBebidas() {
        List<ProdutoDTO> bebidas = cardapioService.listarBebidas();
        return ResponseEntity.ok(bebidas);
    }

    @PreAuthorize("hasAnyRole('GERENTE', 'ADMINISTRADOR')")
    @PostMapping("/produtos/{tipo}")
    public ResponseEntity<Produto> cadastrarProduto(
            @PathVariable TipoProduto tipo,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        Produto produto = cardapioService.cadastrarProduto(dto, tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
}