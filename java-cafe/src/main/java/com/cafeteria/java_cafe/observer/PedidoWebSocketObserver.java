package com.cafeteria.java_cafe.observer;

import com.cafeteria.java_cafe.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PedidoWebSocketObserver implements Observador {
    private final SimpMessagingTemplate messagingTemplate;
    private String evento = "NOVO_PEDIDO";

    @Autowired
    public PedidoWebSocketObserver(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    @Override
    public void atualizar(Pedido pedido) {
        if (evento.equals("NOVO_PEDIDO")) {
            messagingTemplate.convertAndSend("/topic/pedidos", pedido.getId());
        } else if (evento.equals("STATUS_ATUALIZADO")) {
            messagingTemplate.convertAndSend(
                "/topic/pedidos/" + pedido.getId() + "/status",
                Map.of(
                    "id", pedido.getId(),
                    "status", pedido.getStatus().name()
                )
            );
        } else if (evento.equals("PEDIDO_CANCELADO")) {
            messagingTemplate.convertAndSend(
                "/topic/pedidos/" + pedido.getId() + "/status",
                Map.of(
                    "id", pedido.getId(),
                    "status", pedido.getStatus().name()
                )
            );
        }
    }
} 