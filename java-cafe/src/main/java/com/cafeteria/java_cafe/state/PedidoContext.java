package com.cafeteria.java_cafe.state;

import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.enums.StatusPedido;

public class PedidoContext {

    private Pedido pedido;
    private PedidoState estadoAtual;

    public PedidoContext(Pedido pedido) {
        this.pedido = pedido;

        switch (pedido.getStatus()) {
            case RECEBIDO -> this.estadoAtual = new RecebidoState();
            case EM_PREPARO -> this.estadoAtual = new EmPreparoState();
            case PRONTO -> this.estadoAtual = new ProntoState();
            case ENTREGUE -> this.estadoAtual = new EntregueState();
        }
    }

    public void avancarEstado() {
        estadoAtual.avancar(this);
    }

    public void setEstado(PedidoState novoEstado) {
        this.estadoAtual = novoEstado;
        this.pedido.setStatus(StatusPedido.valueOf(novoEstado.getNomeEstado()));
    }

    public Pedido getPedido() {
        return pedido;
    }

    public PedidoState getEstadoAtual() {
        return estadoAtual;
    }
}