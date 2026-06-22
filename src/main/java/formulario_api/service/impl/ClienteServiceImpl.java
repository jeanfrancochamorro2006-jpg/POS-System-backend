package formulario_api.service.impl;

import org.springframework.stereotype.Service;



import java.util.List;

import formulario_api.dto.ClienteRequest;
import formulario_api.entity.Cliente;
import formulario_api.repository.ClienteRepository;
import formulario_api.service.ClienteService;
@Service
public class ClienteServiceImpl implements ClienteService {
 private final ClienteRepository clienteRepository;
 public ClienteServiceImpl(ClienteRepository clienteRepository) {
 this.clienteRepository = clienteRepository;
 }
 @Override
 public Cliente registrar(ClienteRequest request) {
 clienteRepository.findByCorreo(request.getCorreo()).ifPresent(c -> {
 throw new RuntimeException("Ya existe un cliente con ese correo");
 });
 Cliente cliente = new Cliente();
 cliente.setNombres(request.getNombres());
 cliente.setApellidos(request.getApellidos());
 cliente.setCorreo(request.getCorreo());
 cliente.setEdad(request.getEdad());
 cliente.setTelefono(request.getTelefono());
 cliente.setTipoDocumento(request.getTipoDocumento());
 cliente.setNumeroDocumento(request.getNumeroDocumento());
 cliente.setAceptaTerminos(request.getAceptaTerminos());
 return clienteRepository.save(cliente);
 }
 @Override
 public List<Cliente> listar() {
 return clienteRepository.findAll();
 }
}