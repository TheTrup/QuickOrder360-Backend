package MicroServicioDespachos.Despachos.service;

import MicroServicioDespachos.Despachos.dto.PedidoDTO;
import MicroServicioDespachos.Despachos.model.Despachos;
import MicroServicioDespachos.Despachos.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service


public class DespachosService {

    @Autowired
    private DespachoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Despachos crearDespacho(Despachos despacho) {
        // 1. Validar que el Pedido exista (Puerto 8082)
        PedidoDTO pedido;
        try {
            pedido = restTemplate.getForObject("http://localhost:8082/api/v1/pedidos/" + despacho.getPedidoId(), PedidoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: El pedido " + despacho.getPedidoId() + " no existe.");
        }

        // 2. Validar que el pedido esté PAGADO
        if (pedido == null || !"PAGADO".equalsIgnoreCase(pedido.getEstado())) {
            throw new RuntimeException("Error: El pedido debe estar PAGADO para poder despacharlo. Estado actual: " + (pedido != null ? pedido.getEstado() : "Desconocido"));
        }

        // 3. Guardar el despacho
        Despachos despachoGuardado = repository.save(despacho);

        // 4. (Opcional) Actualizar el estado del Pedido a 'EN_DESPACHO'
        try {
            String urlActualizarPedido = "http://localhost:8082/api/v1/pedidos/" + despacho.getPedidoId() + "/estado?nuevoEstado=EN_DESPACHO";
            restTemplate.put(urlActualizarPedido, null);
        } catch (Exception e) {
            System.err.println("Advertencia: Se creó el despacho, pero no se pudo actualizar el estado en Pedidos.");
        }

        return despachoGuardado;
    }

}
