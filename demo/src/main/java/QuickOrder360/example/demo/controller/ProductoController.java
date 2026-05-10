package QuickOrder360.example.demo.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import QuickOrder360.example.demo.model.Producto;
import QuickOrder360.example.demo.services.ProductoService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/productos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto){
        log.info("registrando nuevo producto: {}", producto.getNombre());
        Producto nuevoProducto = productoService.registraProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(productoService.ObtenerTodos());
    }

}
