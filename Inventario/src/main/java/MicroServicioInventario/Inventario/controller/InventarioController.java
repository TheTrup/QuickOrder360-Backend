package MicroServicioInventario.Inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import MicroServicioInventario.Inventario.service.InventarioService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/inventario")

public class InventarioController {

    @Autowired
    private InventarioService service;

    // GET: http://localhost:8085/api/v1/inventario/{productoId}
    @GetMapping("/{productoId}")
    public ResponseEntity<?> consultar(@PathVariable Long productoId) {
        try {
            return ResponseEntity.ok(service.consultarStock(productoId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST: http://localhost:8085/api/v1/inventario/{productoId}?cantidad=10
    // Usamos query params para que sea fácil mandar números positivos (entradas) o negativos (salidas)
    @PostMapping("/{productoId}")
    public ResponseEntity<?> actualizar(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(service.actualizarStock(productoId, cantidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/descontar/{productoId}/{cantidad}") 
    public ResponseEntity<String> descontarStock(@PathVariable Long productoId, @PathVariable Integer cantidad) {
        boolean exito = service.restarStock(productoId, cantidad);
        if (exito) {
            return ResponseEntity.ok("Stock descontado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock insuficiente o producto no encontrado");
        }
    }

}
