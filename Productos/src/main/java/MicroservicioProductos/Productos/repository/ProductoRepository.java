package MicroservicioProductos.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import MicroservicioProductos.Productos.model.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
