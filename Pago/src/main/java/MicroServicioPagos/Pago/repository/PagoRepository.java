package MicroServicioPagos.Pago.repository;
import MicroServicioPagos.Pago.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PagoRepository extends JpaRepository <Pagos, Long>{
    List<Pagos> findByPedidoId(Long pedidoId);

}
