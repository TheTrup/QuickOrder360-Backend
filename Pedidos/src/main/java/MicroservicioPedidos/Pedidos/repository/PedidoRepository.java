package MicroservicioPedidos.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import MicroservicioPedidos.Pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    

}
