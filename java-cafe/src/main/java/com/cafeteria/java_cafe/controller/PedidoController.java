package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.PedidoDTO;
import com.cafeteria.java_cafe.dto.PedidoRequestDTO;
import com.cafeteria.java_cafe.dto.PedidoResponseDTO;
import com.cafeteria.java_cafe.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CLIENTE', 'CLIENTE_TEMPORARIO', 'GERENTE')")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(converterParaPedidoRequestDTO(dto));

        PedidoDTO pedidoDTO = converterParaPedidoDTO(response);

        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<List<PedidoDTO>> verPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos();

        List<PedidoDTO> dtos = pedidos.stream()
                .map(this::converterParaPedidoDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }


    private PedidoRequestDTO converterParaPedidoRequestDTO(PedidoDTO dto) {
        return new PedidoRequestDTO(
                dto.clienteId(),
                dto.itens(),
                dto.pagamentoRealizado(),
                dto.observacao()
        );
    }

    private PedidoDTO converterParaPedidoDTO(PedidoResponseDTO response) {
        return new PedidoDTO(
                null,
                Collections.emptyList(),
                true,
                null
        );
    }
}