package formulario_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.entity.Categoria;
import formulario_api.repository.CategoriaRepository;
import formulario_api.repository.ProductoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaController(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> crear(@Valid @RequestBody CategoriaRequest request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.nombre)) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre);
        categoria.setDescripcion(request.descripcion);
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setNombre(request.nombre);
        categoria.setDescripcion(request.descripcion);
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        if (productoRepository.existsByCategoriaId(id)) {
            throw new RuntimeException("No se puede eliminar: hay productos en esta categoría");
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public static class CategoriaRequest {
        @NotBlank(message = "El nombre es obligatorio")
        public String nombre;
        public String descripcion;
    }
}
