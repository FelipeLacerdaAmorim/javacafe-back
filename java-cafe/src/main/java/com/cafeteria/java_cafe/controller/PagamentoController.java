package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import com.cafeteria.java_cafe.model.Pagamento;
import com.cafeteria.java_cafe.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PagamentoController {
    private final PagamentoRepository pagamentoRepository;

    @GetMapping("/metodos")
    public ResponseEntity<List<Map<String, Object>>> listarMetodosPagamento() {
        List<Map<String, Object>> metodos = Arrays.stream(MetodoPagamento.values())
                .map(metodo -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", metodo.name());
                    map.put("nome", metodo.name());
                    map.put("descricao", getDescricao(metodo));
                    map.put("desconto", getDescontoPercentual(metodo));
                    return map;
                })
                .toList();
        return ResponseEntity.ok(metodos);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        List<Map<String, Object>> resposta = pagamentos.stream().map(pagamento -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", pagamento.getId());
            map.put("valorPago", pagamento.getValorPago());
            map.put("metodo", pagamento.getMetodo());
            map.put("descontoAplicado", pagamento.getDescontoAplicado());
            map.put("pedidoId", pagamento.getPedido() != null ? pagamento.getPedido().getId() : null);
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    private String getDescricao(MetodoPagamento metodo) {
        return switch (metodo) {
            case PIX -> "Pagamento instantâneo via Pix";
            case CARTAO_FIDELIDADE -> "Cartão fidelidade (desconto para clientes fiéis)";
            case CARTAO_CREDITO -> "Cartão de crédito";
            case CARTAO_DEBITO -> "Cartão de débito";
            case DINHEIRO -> "Pagamento em dinheiro";
        };
    }

    private double getDescontoPercentual(MetodoPagamento metodo) {
        return switch (metodo) {
            case PIX -> 10.0;
            case CARTAO_FIDELIDADE -> 10.0;
            case CARTAO_DEBITO -> 5.0;
            case DINHEIRO -> 5.0;
            default -> 0.0;
        };
    }
}
