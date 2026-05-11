package MicroServicioInventario.Inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import MicroServicioInventario.Inventario.model.Inventario;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    Optional<Inventario> findByProductoId(Long productoId);

}
