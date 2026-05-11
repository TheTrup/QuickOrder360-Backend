package MicroservicioPedidos.Pedidos.client;

import MicroservicioPedidos.Pedidos.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Productos")
public interface ProductoClient {

    @GetMapping("/api/v1/productos/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);

    @PutMapping("/{id}/restar-stock")
    void restarStock(@PathVariable("id") Long id, @RequestParam("cantidad") Integer cantidad);
}