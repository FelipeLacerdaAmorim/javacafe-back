package com.cafeteria.java_cafe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @PreAuthorize("permitAll()")
    @GetMapping("/publico")
    public ResponseEntity<String> publico() {
        return ResponseEntity.ok("Este endpoint é público, acesso liberado para todos.");
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/protegido")
    public ResponseEntity<String> protegido() {
        return ResponseEntity.ok("Acesso permitido somente para ADMINISTRADOR.");
    }
}