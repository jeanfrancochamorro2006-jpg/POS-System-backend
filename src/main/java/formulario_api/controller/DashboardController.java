package formulario_api.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.repository.ProductoRepository;
import formulario_api.repository.VentaRepository;
import formulario_api.service.VentaService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final VentaService ventaService;

    private static final int UMBRAL_BAJO_STOCK = 10;

    public DashboardController(VentaRepository ventaRepository, ProductoRepository productoRepository,
            VentaService ventaService) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.ventaService = ventaService;
    }

    @GetMapping("/resumen")
    public Map<String, Object> resumen() {
        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioSemana = LocalDate.now().minusDays(6).atStartOfDay();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("ventasHoyTotal", ventaRepository.sumarTotalEntre(inicioHoy, ahora));
        data.put("ventasHoyCount", ventaRepository.countByFechaBetween(inicioHoy, ahora));
        data.put("ingresoSemana", ventaRepository.sumarTotalEntre(inicioSemana, ahora));
        data.put("ingresoTotal", ventaRepository.sumarTotalGeneral());
        data.put("totalProductos", productoRepository.count());
        data.put("productosBajoStock", productoRepository.countByStockLessThanEqual(UMBRAL_BAJO_STOCK));

        Map<String, BigDecimal> porMetodo = new LinkedHashMap<>();
        for (Object[] fila : ventaRepository.totalPorMetodoPago()) {
            porMetodo.put((String) fila[0], (BigDecimal) fila[1]);
        }
        data.put("ventasPorMetodo", porMetodo);

        data.put("ultimasVentas", ventaRepository.findTop5ByOrderByFechaDesc().stream()
                .map(ventaService::mapearPublico).toList());

        return data;
    }
}
