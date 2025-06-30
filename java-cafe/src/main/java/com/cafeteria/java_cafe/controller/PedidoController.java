package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.PedidoDTO;
import com.cafeteria.java_cafe.dto.PedidoRequestDTO;
import com.cafeteria.java_cafe.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/sem-login")
    public ResponseEntity<PedidoDTO> criarPedidoSemLogin(@RequestBody PedidoDTO dto) {
        if (!dto.pagamentoRealizado()) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }

        PedidoDTO pedidoCriado = pedidoService.criarPedidoComClienteTemporario(dto);
        return ResponseEntity.ok(pedidoCriado);
    }


    private PedidoRequestDTO converterParaPedidoRequestDTO(PedidoDTO dto) {

        return new PedidoRequestDTO(
                dto.clienteId(),
                dto.itens(),
                dto.pagamentoRealizado(),
                dto.clienteTemporario()
        );
    }
}