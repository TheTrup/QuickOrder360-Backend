package MicroservicioPedidos.Pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    String obtenerClientePorId(@PathVariable("id") Long id);
}