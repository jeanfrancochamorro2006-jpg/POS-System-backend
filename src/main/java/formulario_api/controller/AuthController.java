package formulario_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.dto.AuthResponse;
import formulario_api.dto.LoginRequest;
import formulario_api.entity.Usuario;
import formulario_api.repository.UsuarioRepository;
import formulario_api.security.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();

        UserDetails userDetails = User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().name())
                .build();

        String token = jwtService.generarToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                token, usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getRol().name()));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(org.springframework.security.core.Authentication authentication) {
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(new AuthResponse(
                null, usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getRol().name()));
    }
}
