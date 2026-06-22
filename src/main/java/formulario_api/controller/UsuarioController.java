package formulario_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.dto.UsuarioRequest;
import formulario_api.entity.Rol;
import formulario_api.entity.Usuario;
import formulario_api.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody UsuarioRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre de usuario");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.valueOf(request.getRol()));
        usuario.setActivo(request.getActivo() == null ? true : request.getActivo());
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuario.getUsername().equals(request.getUsername())
                && usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre de usuario");
        }
        usuario.setNombre(request.getNombre());
        usuario.setUsername(request.getUsername());
        usuario.setRol(Rol.valueOf(request.getRol()));
        usuario.setActivo(request.getActivo() == null ? true : request.getActivo());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario.getUsername().equals(authentication.getName())) {
            throw new RuntimeException("No puedes eliminar tu propio usuario");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
