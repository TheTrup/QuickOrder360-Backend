package MicroServicioPagos.Pago.service;

import MicroServicioPagos.Pago.dto.PedidoDTO;
import MicroServicioPagos.Pago.model.Pagos;
import MicroServicioPagos.Pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Pagos procesarPago(Pagos pago) {
        
      
        PedidoDTO pedido;
        try {
            pedido = restTemplate.getForObject("http://localhost:8082/api/v1/pedidos/" + pago.getPedidoId(), PedidoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: El pedido " + pago.getPedidoId() + " no existe.");
        }

        if (pedido == null) {
            throw new RuntimeException("Error: No se obtuvieron datos del pedido.");
        }

       
        pago.setEstado("COMPLETADO");

        Pagos pagoGuardado = repository.save(pago);

        try {
            String urlActualizarPedido = "http://localhost:8082/api/v1/pedidos/" + pago.getPedidoId() + "/estado?nuevoEstado=PAGADO";
            restTemplate.put(urlActualizarPedido, null);
        } catch (Exception e) {
            System.err.println("Advertencia: Se guardó el pago, pero no se pudo actualizar el estado en Pedidos.");
        }

        return pagoGuardado;
    }
}