package com.cafeteria.java_cafe.dao;

import com.cafeteria.java_cafe.dto.IngredienteDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class IngredienteDAO {
    private final JdbcTemplate jdbcTemplate;

    public IngredienteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IngredienteDTO> buscarIngredientesPorProduto(Long produtoId) {
        String sql = "SELECT i.id, i.nome, i.preco_adicional FROM ingredientes i " +
                "JOIN produto_ingrediente pi ON pi.ingrediente_id = i.id " +
                "WHERE pi.produto_id = ?";
        return jdbcTemplate.query(sql, new Object[]{produtoId}, (rs, rowNum) ->
                new IngredienteDTO(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco_adicional")
                )
        );
    }
}
