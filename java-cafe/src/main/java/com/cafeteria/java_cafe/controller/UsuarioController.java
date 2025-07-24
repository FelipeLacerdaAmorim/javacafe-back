package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.auth.UsuarioRequestDTO;
import com.cafeteria.java_cafe.dto.auth.UsuarioResponseDTO;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.TipoUsuario;
import com.cafeteria.java_cafe.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getTipoUsuario()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha()) // Ideal: criptografar senha
                .tipoUsuario(TipoUsuario.valueOf(dto.tipoUsuario().toUpperCase()))
                .build();
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Long usuarioLogadoId = null;
        TipoUsuario tipoUsuarioLogado = null;
        if (principal instanceof com.cafeteria.java_cafe.service.oauth.UserDetailsImpl userDetails) {
            usuarioLogadoId = userDetails.getUsuario().getId();
            tipoUsuarioLogado = userDetails.getUsuario().getTipoUsuario();
        } else if (principal instanceof Usuario usuario) {
            usuarioLogadoId = usuario.getId();
            tipoUsuarioLogado = usuario.getTipoUsuario();
        }
        if (usuarioLogadoId == null || (!usuarioLogadoId.equals(id) && tipoUsuarioLogado != TipoUsuario.ADMINISTRADOR)) {
            return ResponseEntity.status(403).build();
        }
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(dto.senha()); // Ideal: criptografar senha
        }
        usuario.setTipoUsuario(TipoUsuario.valueOf(dto.tipoUsuario().toUpperCase()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponseDTO> getMe(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Usuario usuario = null;
        if (principal instanceof com.cafeteria.java_cafe.service.oauth.UserDetailsImpl userDetails) {
            usuario = userDetails.getUsuario();
        } else if (principal instanceof Usuario u) {
            usuario = u;
        }
        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario()));
    }
} 