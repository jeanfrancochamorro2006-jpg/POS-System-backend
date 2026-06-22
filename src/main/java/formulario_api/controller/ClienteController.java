package formulario_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formulario_api.dto.ClienteRequest;
import formulario_api.entity.Cliente;
import formulario_api.service.ClienteService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
 private final ClienteService clienteService;
 public ClienteController(ClienteService clienteService) {
 this.clienteService = clienteService;
 }
 @PostMapping
 public ResponseEntity<Cliente> registrar(@Valid @RequestBody ClienteRequest request) {
 return ResponseEntity.ok(clienteService.registrar(request));
 }
 @GetMapping
 public ResponseEntity<List<Cliente>> listar() {
 return ResponseEntity.ok(clienteService.listar());
 }
}
