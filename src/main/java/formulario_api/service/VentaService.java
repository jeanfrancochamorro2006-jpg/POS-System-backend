package formulario_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import formulario_api.dto.VentaRequest;
import formulario_api.dto.VentaResponse;
import formulario_api.entity.Cliente;
import formulario_api.entity.DetalleVenta;
import formulario_api.entity.Producto;
import formulario_api.entity.Usuario;
import formulario_api.entity.Venta;
import formulario_api.repository.ClienteRepository;
import formulario_api.repository.ProductoRepository;
import formulario_api.repository.UsuarioRepository;
import formulario_api.repository.VentaRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
            ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public VentaResponse registrar(VentaRequest request, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setMetodoPago(request.getMetodoPago());

        if (request.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new RuntimeException("El cliente indicado no existe"));
            venta.setCliente(cliente);
        }

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (VentaRequest.DetalleRequest d : request.getDetalles()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado (id " + d.getProductoId() + ")"));

            if (Boolean.FALSE.equals(producto.getActivo())) {
                throw new RuntimeException("El producto '" + producto.getNombre() + "' no está disponible");
            }
            if (producto.getStock() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para '" + producto.getNombre()
                        + "' (disponible: " + producto.getStock() + ")");
            }

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);

            producto.setStock(producto.getStock() - d.getCantidad());
            productoRepository.save(producto);

            total = total.add(subtotal);
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);

        return mapear(ventaRepository.save(venta));
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        return ventaRepository.findAllByOrderByFechaDesc().stream().map(this::mapear).toList();
    }

    @Transactional(readOnly = true)
    public VentaResponse obtener(Long id) {
        return ventaRepository.findById(id).map(this::mapear)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public VentaResponse mapearPublico(Venta venta) {
        return mapear(venta);
    }

    private VentaResponse mapear(Venta venta) {
        VentaResponse res = new VentaResponse();
        res.setId(venta.getId());
        res.setFecha(venta.getFecha());
        res.setTotal(venta.getTotal());
        res.setMetodoPago(venta.getMetodoPago());
        res.setUsuarioNombre(venta.getUsuario() != null ? venta.getUsuario().getNombre() : null);
        if (venta.getCliente() != null) {
            res.setClienteNombre(venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos());
        } else {
            res.setClienteNombre("Público general");
        }
        List<VentaResponse.DetalleResponse> detalles = venta.getDetalles().stream().map(d -> {
            VentaResponse.DetalleResponse dr = new VentaResponse.DetalleResponse();
            dr.setProductoId(d.getProducto().getId());
            dr.setProductoNombre(d.getProducto().getNombre());
            dr.setCantidad(d.getCantidad());
            dr.setPrecioUnitario(d.getPrecioUnitario());
            dr.setSubtotal(d.getSubtotal());
            return dr;
        }).toList();
        res.setDetalles(detalles);
        return res;
    }
}
