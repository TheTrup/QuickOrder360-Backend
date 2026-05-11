package MicroServicioReclamos.Reclamos.repository;

import MicroServicioReclamos.Reclamos.model.Reclamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReclamosRepository extends JpaRepository<Reclamos, Long>{

    List<Reclamos> findByUsuarioId(Long usuarioId);
    List<Reclamos> findByPedidoId(Long pedidoId);

}
