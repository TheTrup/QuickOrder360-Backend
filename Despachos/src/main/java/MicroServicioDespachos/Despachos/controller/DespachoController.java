package MicroServicioDespachos.Despachos.controller;

import MicroServicioDespachos.Despachos.service.DespachosService;
import MicroServicioDespachos.Despachos.model.Despachos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/despachos")
public class DespachoController {

    @Autowired
    private DespachosService service;

    @PostMapping
    public ResponseEntity<Despachos> crearDespacho(@RequestBody Despachos despacho) {
        try {
            Despachos nuevoDespacho = service.crearDespacho(despacho);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDespacho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
