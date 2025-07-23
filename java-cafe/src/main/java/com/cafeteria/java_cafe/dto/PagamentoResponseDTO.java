package com.cafeteria.java_cafe.dto;

import com.cafeteria.java_cafe.model.enums.MetodoPagamento;
import java.math.BigDecimal;

public class PagamentoResponseDTO {
    private MetodoPagamento metodoPagamento;
    private BigDecimal valorOriginal;
    private BigDecimal valorFinal;
    private BigDecimal descontoAplicado;

    public PagamentoResponseDTO() {}

    public PagamentoResponseDTO(MetodoPagamento metodoPagamento, BigDecimal valorOriginal, BigDecimal valorFinal, BigDecimal descontoAplicado) {
        this.metodoPagamento = metodoPagamento;
        this.valorOriginal = valorOriginal;
        this.valorFinal = valorFinal;
        this.descontoAplicado = descontoAplicado;
    }

    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public BigDecimal getValorOriginal() { return valorOriginal; }
    public void setValorOriginal(BigDecimal valorOriginal) { this.valorOriginal = valorOriginal; }
    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }
    public BigDecimal getDescontoAplicado() { return descontoAplicado; }
    public void setDescontoAplicado(BigDecimal descontoAplicado) { this.descontoAplicado = descontoAplicado; }
}
