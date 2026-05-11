package MicroServicioPagos.Pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Pedidos")
public interface PedidoClient {
    @PutMapping("/pedidos/{id}/confirmar-pago")
    void confirmarPago(@PathVariable Long id);
}