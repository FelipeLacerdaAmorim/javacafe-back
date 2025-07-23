package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

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
