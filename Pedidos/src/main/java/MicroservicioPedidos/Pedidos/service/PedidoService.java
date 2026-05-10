package MicroservicioPedidos.Pedidos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import MicroservicioPedidos.Pedidos.model.Pedido;
import MicroservicioPedidos.Pedidos.repository.PedidoRepository;

@Slf4j
@Service

public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Pedido registrarPedido(Pedido pedido) {
        log.info("Iniciando validación para el cliente con ID: {}", pedido.getClienteId());
        
        // 1. Armamos la URL exacta hacia nuestro microservicio de Clientes (Puerto 8081)
        String urlMicroservicioClientes = "http://localhost:8081/api/v1/clientes/" + pedido.getClienteId();

        try {
            // 2. Hacemos una llamada GET "invisible" para ver si el cliente existe
            ResponseEntity<String> respuesta = restTemplate.getForEntity(urlMicroservicioClientes, String.class);
            
            // 3. Si no hay error, el cliente existe. Guardamos el pedido.
            log.info("Cliente validado correctamente. Guardando pedido...");
            pedido.setEstado("CREADO");
            return pedidoRepository.save(pedido);

        } catch (HttpClientErrorException.NotFound e) {
            // 4. Si el puerto 8081 nos responde un 404 (No encontrado), bloqueamos el pedido.
            log.error("Error: El cliente con ID {} no existe en la base de datos.", pedido.getClienteId());
            throw new RuntimeException("No se puede crear el pedido: El cliente no existe.");
        }
    }

}
