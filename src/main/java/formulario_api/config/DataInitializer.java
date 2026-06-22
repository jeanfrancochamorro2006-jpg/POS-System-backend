package formulario_api.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import formulario_api.entity.Categoria;
import formulario_api.entity.Producto;
import formulario_api.entity.Rol;
import formulario_api.entity.Usuario;
import formulario_api.repository.CategoriaRepository;
import formulario_api.repository.ProductoRepository;
import formulario_api.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    @Order(2)
    CommandLineRunner seedCatalogo(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        return args -> {
            if (categoriaRepository.count() > 0) {
                return;
            }
            Categoria bebidas = nuevaCategoria(categoriaRepository, "Bebidas", "Gaseosas, aguas y jugos");
            Categoria abarrotes = nuevaCategoria(categoriaRepository, "Abarrotes", "Productos de despensa");
            Categoria snacks = nuevaCategoria(categoriaRepository, "Snacks", "Galletas y golosinas");

            nuevoProducto(productoRepository, "Coca Cola 500ml", "BEB001", "3.50", 50, bebidas);
            nuevoProducto(productoRepository, "Agua San Luis 625ml", "BEB002", "1.50", 80, bebidas);
            nuevoProducto(productoRepository, "Arroz Costeño 1kg", "ABA001", "4.20", 40, abarrotes);
            nuevoProducto(productoRepository, "Aceite Primor 1L", "ABA002", "9.90", 25, abarrotes);
            nuevoProducto(productoRepository, "Galleta Oreo", "SNK001", "2.00", 60, snacks);
            nuevoProducto(productoRepository, "Chocolate Sublime", "SNK002", "1.80", 5, snacks);
        };
    }

    private Categoria nuevaCategoria(CategoriaRepository repo, String nombre, String descripcion) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return repo.save(c);
    }

    private void nuevoProducto(ProductoRepository repo, String nombre, String codigo, String precio, int stock,
            Categoria categoria) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setCodigo(codigo);
        p.setPrecio(new BigDecimal(precio));
        p.setStock(stock);
        p.setCategoria(categoria);
        p.setActivo(true);
        repo.save(p);
    }

    @Bean
    @Order(1)
    CommandLineRunner seedUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!usuarioRepository.existsByUsername("admin")) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                admin.setActivo(true);
                usuarioRepository.save(admin);
            }
            if (!usuarioRepository.existsByUsername("cajero")) {
                Usuario cajero = new Usuario();
                cajero.setNombre("Cajero Demo");
                cajero.setUsername("cajero");
                cajero.setPassword(passwordEncoder.encode("cajero123"));
                cajero.setRol(Rol.CAJERO);
                cajero.setActivo(true);
                usuarioRepository.save(cajero);
            }
        };
    }
}
