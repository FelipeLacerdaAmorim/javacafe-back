package com.cafeteria.java_cafe.bo;

import com.cafeteria.java_cafe.model.Pagamento;
import com.cafeteria.java_cafe.model.Pedido;
import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import com.cafeteria.java_cafe.strategy.DescontoStrategy;
import com.cafeteria.java_cafe.strategy.DescontoStrategyProvider;

import java.math.BigDecimal;

public class PagamentoBO {

    public Pagamento processarPagamento(Pedido pedido, MetodoPagamento metodo) {
        BigDecimal total = pedido.getTotal();

        boolean clienteFiel = pedido.getUsuario() != null && pedido.getUsuario().isFidelidade();

        DescontoStrategy strategy = DescontoStrategyProvider.getStrategy(metodo, clienteFiel);
        BigDecimal desconto = strategy.calcularDesconto(total);
        BigDecimal valorFinal = total.subtract(desconto);

        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo(metodo);
        pagamento.setDescontoAplicado(desconto);
        pagamento.setValorPago(valorFinal);
        pagamento.setPedido(pedido);

        return pagamento;
    }
}