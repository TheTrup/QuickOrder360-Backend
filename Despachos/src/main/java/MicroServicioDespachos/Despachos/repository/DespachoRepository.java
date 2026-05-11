package MicroServicioDespachos.Despachos.repository;

import MicroServicioDespachos.Despachos.model.Despachos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DespachoRepository extends JpaRepository<Despachos, Long>{

}
