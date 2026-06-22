package formulario_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VentaResponse {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String metodoPago;
    private String clienteNombre;
    private String usuarioNombre;
    private List<DetalleResponse> detalles;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    public List<DetalleResponse> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleResponse> detalles) { this.detalles = detalles; }

    public static class DetalleResponse {
        private Long productoId;
        private String productoNombre;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;

        public Long getProductoId() { return productoId; }
        public void setProductoId(Long productoId) { this.productoId = productoId; }
        public String getProductoNombre() { return productoNombre; }
        public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    }
}
