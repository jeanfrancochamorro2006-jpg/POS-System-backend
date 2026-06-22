package formulario_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import formulario_api.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);
    List<Producto> findByActivoTrue();
    boolean existsByCategoriaId(Long categoriaId);
    long countByStockLessThanEqual(int stock);
}
