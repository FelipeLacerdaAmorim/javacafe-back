package com.cafeteria.java_cafe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseEntity<String> getCardapio() {
        return ResponseEntity.ok("Mostrando todos os itens do cardápio.");
    }
}