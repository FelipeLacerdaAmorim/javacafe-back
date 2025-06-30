package com.cafeteria.java_cafe.dto.auth;

import com.cafeteria.java_cafe.dto.ItemPedidoDTO;

import java.util.List;

public record PedidoRequestDTO(Long clienteId, List<ItemPedidoDTO> itens) {}