package MicroServicioReclamos.Reclamos.service;

import MicroServicioReclamos.Reclamos.dto.PedidoDTO;
import MicroServicioReclamos.Reclamos.model.Reclamos;
import MicroServicioReclamos.Reclamos.repository.ReclamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ReclamosService {

    @Autowired
    private ReclamosRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Reclamos crearReclamo(Reclamos reclamo) {
        // 1. Validar que el Pedido exista (Microservicio Pedidos - 8082)
        try {
            restTemplate.getForObject("http://localhost:8082/api/v1/pedidos/" + reclamo.getPedidoId(), PedidoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: No se puede crear un reclamo para el pedido " + reclamo.getPedidoId() + " porque no existe.");
        }

        // 2. Guardar el reclamo
        return repository.save(reclamo);
    }

}
