package MicroServicioPagos.Pago.controller;

import MicroServicioPagos.Pago.model.Pagos;
import MicroServicioPagos.Pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping
    public ResponseEntity<Pagos> realizarPago(@RequestBody Pagos pago) {
        try {
            Pagos nuevoPago = service.procesarPago(pago);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
