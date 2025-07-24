package com.cafeteria.java_cafe.dto.auth;

import com.cafeteria.java_cafe.model.enums.TipoUsuario;

public record UsuarioResponseDTO(Long id, String nome, String email, TipoUsuario tipoUsuario) {}