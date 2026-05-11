package MicroservicioPedidos.Pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENTES")
public interface ClienteClient {
    @GetMapping("/api/v1/clientes/{id}") 
    String obtenerClientePorId(@PathVariable("id") Long id);
}