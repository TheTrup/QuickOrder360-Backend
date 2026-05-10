package MicroservicioProductos.Productos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import MicroservicioProductos.Productos.model.Producto;
import MicroservicioProductos.Productos.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto registraProducto(Producto producto) {

        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id){
        return productoRepository.findById(id);
    }

    public void restarStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }
        
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

}
