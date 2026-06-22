package formulario_api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import formulario_api.entity.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
 Optional<Cliente> findByCorreo(String correo);
}