package MicroservicioPedidos.Pedidos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import MicroservicioPedidos.Pedidos.dto.ProductoDTO;
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
        
        // Se valida el cliente
        String urlMicroservicioClientes = "http://localhost:8081/api/v1/clientes/" + pedido.getClienteId();
        try {
            restTemplate.getForEntity(urlMicroservicioClientes, String.class);
            log.info("Cliente válido.");
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Error: El cliente con ID {} no existe en la base de datos.", pedido.getClienteId());
            throw new RuntimeException("No se puede crear el pedido: El cliente no existe.");
        }

        // Se valida el producto y el sotck
        log.info("Verificando stock del producto ID: {}", pedido.getProductoId());
        String urlMicroservicioProductos = "http://localhost:8083/api/v1/productos/" + pedido.getProductoId();
        
        try {
            ResponseEntity<ProductoDTO> respuestaProducto = restTemplate.getForEntity(urlMicroservicioProductos, ProductoDTO.class);
            ProductoDTO producto = respuestaProducto.getBody();

            // Verificamos la regla de negocio del stock
            if (producto.getStock() < pedido.getCantidad()) {
                throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock());
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("No se puede crear el pedido: El producto no existe en el catálogo.");
        }

        // Si cumple todo se guarda
        log.info("Stock validado correctamente. Guardando pedido definitivo...");
        pedido.setEstado("CREADO");
        return pedidoRepository.save(pedido);
    }

}
