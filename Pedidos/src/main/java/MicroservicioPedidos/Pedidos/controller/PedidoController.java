package MicroservicioPedidos.Pedidos.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import MicroservicioPedidos.Pedidos.model.Pedido;
import MicroservicioPedidos.Pedidos.repository.PedidoRepository;
import MicroservicioPedidos.Pedidos.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido) {
        // Si el servicio lanza una excepción, el GlobalExceptionHandler la atrapará automáticamente.
        Pedido nuevoPedido = pedidoService.registrarPedido(pedido);
        
        // El controlador solo se encarga de la respuesta exitosa (201 Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> ResponseEntity.ok(pedido))
                .orElse(ResponseEntity.notFound().build());
    }
}