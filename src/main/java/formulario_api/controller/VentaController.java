package formulario_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.dto.VentaRequest;
import formulario_api.dto.VentaResponse;
import formulario_api.service.VentaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@Valid @RequestBody VentaRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(ventaService.registrar(request, authentication.getName()));
    }

    @GetMapping
    public List<VentaResponse> listar() {
        return ventaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtener(id));
    }
}
