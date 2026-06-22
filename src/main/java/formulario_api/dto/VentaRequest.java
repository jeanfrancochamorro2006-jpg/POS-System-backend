package formulario_api.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VentaRequest {

    private Long clienteId;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotEmpty(message = "La venta debe tener al menos un producto")
    @Valid
    private List<DetalleRequest> detalles;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public List<DetalleRequest> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRequest> detalles) { this.detalles = detalles; }

    public static class DetalleRequest {
        @NotNull(message = "El producto es obligatorio")
        private Long productoId;

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        private Integer cantidad;

        public Long getProductoId() { return productoId; }
        public void setProductoId(Long productoId) { this.productoId = productoId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}
