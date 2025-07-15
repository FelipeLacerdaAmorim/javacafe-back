package com.cafeteria.java_cafe.factory;

import com.cafeteria.java_cafe.model.enums.TipoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ProdutoFactoryProvider {

    private final Map<TipoProduto, ProdutoFactory> factories = new EnumMap<>(TipoProduto.class);

    @Autowired
    public ProdutoFactoryProvider(
            BebidaFactory bebidaFactory,
            AcompanhamentoFactory acompanhamentoFactory,
            SobremesaFactory sobremesaFactory
    ) {
        factories.put(TipoProduto.BEBIDA, bebidaFactory);
        factories.put(TipoProduto.ACOMPANHAMENTO, acompanhamentoFactory);
        factories.put(TipoProduto.SOBREMESA, sobremesaFactory);
    }

    public ProdutoFactory getFactory(TipoProduto tipo) {
        if (!factories.containsKey(tipo)) {
            throw new IllegalArgumentException("Tipo de produto não suportado: " + tipo);
        }
        return factories.get(tipo);
    }
}