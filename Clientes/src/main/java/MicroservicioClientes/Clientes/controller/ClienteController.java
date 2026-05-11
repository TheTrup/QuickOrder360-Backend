package MicroservicioClientes.Clientes.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import MicroservicioClientes.Clientes.model.Cliente;
import MicroservicioClientes.Clientes.repository.ClienteRepository;
import MicroservicioClientes.Clientes.service.ClienteService;
import java.util.Optional;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente) {
        log.info("Recibida petición para registrar cliente con RUT: {}", cliente.getRut());
        Cliente nuevo = clienteService.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        log.info("Consultando lista completa de clientes");
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        // Pedimos al servicio que busque al cliente
        Optional<Cliente> cliente = clienteService.obtenerPorId(id);
        
        if (cliente.isPresent()) {
            // Si el cliente existe, lo devuelve con un estado 200 OK
            return ResponseEntity.ok(cliente.get());
        } else {
            // Si NO existe, devuelve un 404 Not Found 
            return ResponseEntity.notFound().build();
        }
    }
}
