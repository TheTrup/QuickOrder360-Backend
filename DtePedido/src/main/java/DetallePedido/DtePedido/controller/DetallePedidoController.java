package DetallePedido.DtePedido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DetallePedido.DtePedido.service.DetallePedidosService;
import DetallePedido.DtePedido.model.DetallePedido;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/detalles-pedidos")
public class DetallePedidoController {

    @Autowired
    private DetallePedidosService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody DetallePedido detalle) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(detalle));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
