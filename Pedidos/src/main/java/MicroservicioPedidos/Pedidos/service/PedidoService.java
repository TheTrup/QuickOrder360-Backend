package MicroservicioPedidos.Pedidos.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import MicroservicioPedidos.Pedidos.client.ClienteClient;
import MicroservicioPedidos.Pedidos.client.ProductoClient;
import MicroservicioPedidos.Pedidos.dto.ProductoDTO;
import MicroservicioPedidos.Pedidos.model.Pedido;
import MicroservicioPedidos.Pedidos.repository.PedidoRepository;

@Slf4j
@Service

public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ProductoClient productoClient;

public Pedido registrarPedido(Pedido pedido) {
        log.info("Iniciando validación para el cliente con ID: {}", pedido.getClienteId());
        
        // Se valida el cliente
        try {
            clienteClient.obtenerClientePorId(pedido.getClienteId());
            log.info("Cliente válido.");
        } catch (FeignException.NotFound e) {
            log.error("Error: El cliente con ID {} no existe en la base de datos.", pedido.getClienteId());
            throw new RuntimeException("No se puede crear el pedido: El cliente no existe.");
        }

        // Se valida el producto y el stock
        log.info("Verificando stock del producto ID: {}", pedido.getProductoId());
        try {
            ProductoDTO producto = productoClient.obtenerProductoPorId(pedido.getProductoId());
            if (producto.getStock() < pedido.getCantidad()) {
                throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock());
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("No se puede crear el pedido: El producto no existe.");
        }

        // Si cumple todo se guarda
        log.info("Stock validado correctamente. Guardando pedido definitivo...");
        pedido.setEstado("CREADO");
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Restar el stock del producto (Llamada al puerto 8083)
        log.info("Descontando stock en el inventario...");
        productoClient.restarStock(pedido.getProductoId(), pedido.getCantidad());
        return pedidoGuardado;
    }

}
