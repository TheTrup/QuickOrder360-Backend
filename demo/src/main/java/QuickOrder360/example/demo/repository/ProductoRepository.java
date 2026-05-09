package QuickOrder360.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import QuickOrder360.example.demo.model.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
