package MicroServicioInventario.Inventario.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import MicroServicioInventario.Inventario.model.Inventario;
import MicroServicioInventario.Inventario.repository.InventarioRepository;

@Service

public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    // 1. Consultar el stock de un producto
    public Inventario consultarStock(Long productoId) {
        return repository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("No hay registro de inventario para el producto: " + productoId));
    }

    // 2. Actualizar el stock (puede ser cantidad positiva para Entrada, o negativa para Salida)
    public Inventario actualizarStock(Long productoId, Integer cantidad) {
        // Buscamos si ya existe inventario para este producto
        Inventario inventario = repository.findByProductoId(productoId)
                .orElse(new Inventario(productoId, 0)); // Si no existe, lo creamos con stock 0

        // Sumamos (o restamos si la cantidad es negativa)
        int nuevoStock = inventario.getStockActual() + cantidad;
        
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente. No puedes tener stock negativo.");
        }

        inventario.setStockActual(nuevoStock);
        return repository.save(inventario);
    }

}
