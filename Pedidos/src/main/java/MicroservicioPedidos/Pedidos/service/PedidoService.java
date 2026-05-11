package MicroservicioPedidos.Pedidos.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import MicroservicioPedidos.Pedidos.client.ClienteClient;
import MicroservicioPedidos.Pedidos.client.ProductoClient;
import MicroservicioPedidos.Pedidos.client.InventarioClient; // Import corregido
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

    @Autowired
    private InventarioClient inventarioClient;

    @Transactional // Si algo falla después de guardar, se puede revertir
    public Pedido registrarPedido(Pedido pedido) {
        log.info("Iniciando validación para el cliente con ID: {}", pedido.getClienteId());
        
        // 1. Validar Cliente
        try {
            clienteClient.obtenerClientePorId(pedido.getClienteId());
            log.info("Cliente válido.");
        } catch (FeignException.NotFound e) {
            log.error("Error: El cliente ID {} no existe.", pedido.getClienteId());
            throw new RuntimeException("No se puede crear el pedido: El cliente no existe.");
        }

        // 2. Validar Existencia del Producto
        log.info("Verificando existencia del producto ID: {}", pedido.getProductoId());
        try {
            productoClient.obtenerProductoPorId(pedido.getProductoId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("No se puede crear el pedido: El producto no existe.");
        }

        // 3. Descontar Stock en el Microservicio de Inventario
        // Hacemos esto ANTES de guardar el pedido para asegurar que hay existencia
        log.info("Intentando descontar {} unidades del inventario para producto ID: {}", 
                 pedido.getCantidad(), pedido.getProductoId());
        try {
            inventarioClient.descontarStock(pedido.getProductoId(), pedido.getCantidad());
            log.info("Stock descontado exitosamente.");
        } catch (FeignException e) {
            log.error("Error al descontar stock: {}", e.getMessage());
            throw new RuntimeException("Error: Stock insuficiente o problema en Inventario.");
        }

        // 4. Guardar Pedido definitivo
        log.info("Procesando guardado de pedido...");
        pedido.setEstado("CONFIRMADO");
        return pedidoRepository.save(pedido);
    }
}