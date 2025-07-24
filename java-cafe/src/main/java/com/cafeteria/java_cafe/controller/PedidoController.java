package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.PedidoDTO;
import com.cafeteria.java_cafe.dto.PedidoRequestDTO;
import com.cafeteria.java_cafe.dto.PedidoResponseDTO;
import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import com.cafeteria.java_cafe.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.cafeteria.java_cafe.service.oauth.UserDetailsImpl;
import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CLIENTE', 'CLIENTE_TEMPORARIO', 'GERENTE')")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long usuarioId = userDetails.getUsuario().getId();
        PedidoResponseDTO response = pedidoService.criarPedido(dto, usuarioId);
        PedidoDTO pedidoDTO = converterParaPedidoDTO(response);
        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'FUNCIONARIO')")
    public ResponseEntity<List<PedidoDTO>> verPedidos() {
        List<PedidoDTO> dtos = pedidoService.listarPedidosDTO(pedidoService.buscarTodosPedidos());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<PedidoDTO>> verMeusPedidos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long usuarioId = userDetails.getUsuario().getId();
        List<PedidoDTO> dtos = pedidoService.listarPedidosDTO(pedidoService.buscarPedidosPorUsuario(usuarioId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'FUNCIONARIO', 'CLIENTE')")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarTodosPedidos().stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        // Se for CLIENTE, só pode acessar o próprio pedido
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long usuarioId = userDetails.getUsuario().getId();
            if (!pedido.getUsuario().getId().equals(usuarioId)) {
                return ResponseEntity.status(403).body(null);
            }
        }
        MetodoPagamento metodo = pedidoService.getMetodoPagamentoByPedido(pedido);
        return ResponseEntity.ok(pedidoService.toPedidoDTO(pedido, metodo));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('FUNCIONARIO')")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.avancarStatusPedido(id);
        PedidoDTO dto = pedidoService.toPedidoDTO(pedido, pedidoService.getMetodoPagamentoByPedido(pedido));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'FUNCIONARIO')")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'FUNCIONARIO', 'CLIENTE')")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        pedidoService.cancelarPedido(id, userDetails.getUsuario());
        return ResponseEntity.noContent().build();
    }

    private PedidoRequestDTO converterParaPedidoRequestDTO(PedidoDTO dto) {
        return new PedidoRequestDTO(
                dto.itens(),
                dto.pagamentoRealizado(),
                dto.metodoPagamento()
        );
    }

    private PedidoDTO converterParaPedidoDTO(PedidoResponseDTO response) {
        Pedido pedido = pedidoService.buscarTodosPedidos().stream().filter(p -> p.getId().equals(response.id())).findFirst().orElse(null);
        MetodoPagamento metodo = response.metodoPagamento();
        if (pedido == null) {
            return new PedidoDTO(
                null,
                List.of(),
                true,
                metodo,
                (java.time.LocalDateTime) null,
                (String) null,
                (String) null,
                (java.math.BigDecimal) null,
                (java.math.BigDecimal) null
            );
        }
        return pedidoService.toPedidoDTO(pedido, metodo);
    }
}