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

import formulario_api.dto.ProductoRequest;
import formulario_api.entity.Categoria;
import formulario_api.entity.Producto;
import formulario_api.repository.CategoriaRepository;
import formulario_api.repository.ProductoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @GetMapping("/disponibles")
    public List<Producto> disponibles() {
        return productoRepository.findByActivoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest request) {
        if (productoRepository.existsByCodigoIgnoreCase(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }
        Producto producto = new Producto();
        aplicar(producto, request);
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (!producto.getCodigo().equalsIgnoreCase(request.getCodigo())
                && productoRepository.existsByCodigoIgnoreCase(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }
        aplicar(producto, request);
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void aplicar(Producto producto, ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("La categoría indicada no existe"));
        producto.setNombre(request.getNombre());
        producto.setCodigo(request.getCodigo());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        producto.setActivo(request.getActivo() == null ? true : request.getActivo());
    }
}
