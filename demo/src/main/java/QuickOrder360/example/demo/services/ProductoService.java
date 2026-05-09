package QuickOrder360.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import QuickOrder360.example.demo.model.Producto;
import QuickOrder360.example.demo.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto registraProducto(Producto producto) {

        return productoRepository.save(producto);
    }

    public List<Producto> ObtenerTodos() {

        return productoRepository.findAll();
    }

}
