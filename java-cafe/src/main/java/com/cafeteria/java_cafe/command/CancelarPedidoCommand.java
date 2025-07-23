package com.cafeteria.java_cafe.command;

import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.StatusPedido;
import com.cafeteria.java_cafe.model.enums.TipoUsuario;

public class CancelarPedidoCommand implements Comando {
    private final Pedido pedido;
    private final Usuario usuario;

    public CancelarPedidoCommand(Pedido pedido, Usuario usuario) {
        this.pedido = pedido;
        this.usuario = usuario;
    }

    @Override
    public void executar() {
        TipoUsuario tipo = usuario.getTipoUsuario();
        StatusPedido status = pedido.getStatus();
        if (tipo == TipoUsuario.CLIENTE || tipo == TipoUsuario.CLIENTE_TEMPORARIO) {
            if (!pedido.getUsuario().getId().equals(usuario.getId())) {
                throw new RuntimeException("O pedido não pertence ao cliente.");
            }
            if (status != StatusPedido.RECEBIDO) {
                throw new RuntimeException("Cliente só pode cancelar pedidos com status RECEBIDO.");
            }
        } else if (tipo == TipoUsuario.FUNCIONARIO || tipo == TipoUsuario.ADMINISTRADOR) {
            if (!(status == StatusPedido.RECEBIDO || status == StatusPedido.EM_PREPARO)) {
                throw new RuntimeException("Staff/Admin só pode cancelar pedidos com status RECEBIDO ou EM_PREPARO.");
            }
        } else {
            throw new RuntimeException("Tipo de usuário não permitido para cancelar pedido.");
        }
        pedido.setStatus(StatusPedido.CANCELADO);
    }
}
