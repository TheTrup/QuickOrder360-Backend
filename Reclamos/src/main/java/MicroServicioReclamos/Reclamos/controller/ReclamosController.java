package MicroServicioReclamos.Reclamos.controller;

import MicroServicioReclamos.Reclamos.model.Reclamos;
import MicroServicioReclamos.Reclamos.service.ReclamosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reclamos")

public class ReclamosController {

    @Autowired
    private ReclamosService service;

    @PostMapping
    public ResponseEntity<?> crearReclamo(@RequestBody Reclamos reclamo) {
        try {
            Reclamos nuevoReclamo = service.crearReclamo(reclamo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReclamo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
