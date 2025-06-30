package com.cafeteria.java_cafe.controller;

import com.cafeteria.java_cafe.dto.auth.AuthResponseDTO;
import com.cafeteria.java_cafe.dto.auth.LoginDTO;
import com.cafeteria.java_cafe.dto.auth.RegistroDTO;
import com.cafeteria.java_cafe.model.Usuario;
import com.cafeteria.java_cafe.model.enums.TipoUsuario;
import com.cafeteria.java_cafe.repository.UsuarioRepository;
import com.cafeteria.java_cafe.service.oauth.JwtService;
import com.cafeteria.java_cafe.service.oauth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = authManager.authenticate(authToken);

        var usuario = (UserDetailsImpl) auth.getPrincipal();
        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDTO> registrar(@RequestBody RegistroDTO dto) {
        if (usuarioRepo.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario novo = new Usuario();
        novo.setNome(dto.nome());
        novo.setEmail(dto.email());
        novo.setSenha(passwordEncoder.encode(dto.senha()));
        novo.setTipoUsuario(TipoUsuario.valueOf(dto.tipoUsuario().toUpperCase()));

        usuarioRepo.save(novo);

        String token = jwtService.generateToken(new UserDetailsImpl(novo));
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}