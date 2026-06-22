package formulario_api.service;

import java.util.List;

import formulario_api.dto.ClienteRequest;
import formulario_api.entity.Cliente;

public interface ClienteService {
 Cliente registrar(ClienteRequest request);
 List<Cliente> listar();
}

