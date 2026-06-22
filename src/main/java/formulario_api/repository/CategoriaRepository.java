package formulario_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import formulario_api.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
