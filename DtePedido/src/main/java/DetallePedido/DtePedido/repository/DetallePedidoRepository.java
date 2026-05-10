package DetallePedido.DtePedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import DetallePedido.DtePedido.model.DetallePedido;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository <DetallePedido, Long> {
    List<DetallePedido> findByPedidoId(Long pedidoId);

}
