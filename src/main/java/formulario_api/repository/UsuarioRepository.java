package formulario_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import formulario_api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
