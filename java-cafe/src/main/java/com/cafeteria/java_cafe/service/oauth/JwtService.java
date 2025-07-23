package com.cafeteria.java_cafe.service.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${JWT_PUBLIC_KEY:}")
    private String jwtPublicKeyPem;
    private PublicKey publicKey;
    @PostConstruct
    public void init() {
        if (jwtPublicKeyPem != null && !jwtPublicKeyPem.isBlank()) {
            try {
                String publicKeyPEM = jwtPublicKeyPem
                        .replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");
                byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                this.publicKey = kf.generatePublic(keySpec);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao carregar chave pública JWT", e);
            }
        }
    }

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("nome", userDetails.getUsuario().getNome());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(key)  // corrigido aqui
                .compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        if (publicKey != null) {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } else {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
    }

    public boolean isTokenValido(String token, UserDetails userDetails) {
        final String username = getEmail(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    private boolean isTokenExpirado(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("authorities");

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}