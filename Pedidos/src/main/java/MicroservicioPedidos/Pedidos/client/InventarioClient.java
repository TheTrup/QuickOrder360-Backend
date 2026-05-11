package MicroservicioPedidos.Pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTARIO")
public interface InventarioClient {
    @PutMapping("/api/v1/inventario/descontar/{productoId}/{cantidad}")
    void descontarStock(@PathVariable("productoId") Long productoId, @PathVariable("cantidad") Integer cantidad);
}
