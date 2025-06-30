package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.auth.ClienteRequestDTO;
import com.cafeteria.java_cafe.dto.auth.ClienteResponseDTO;
import com.cafeteria.java_cafe.service.cliente.ClienteBO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteBO clienteBO;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteBO.criarCliente(dto));
    }
}