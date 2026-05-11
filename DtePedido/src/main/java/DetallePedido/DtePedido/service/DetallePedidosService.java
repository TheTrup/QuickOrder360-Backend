package DetallePedido.DtePedido.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.validation.Valid;

import DetallePedido.DtePedido.model.DetallePedido;
import DetallePedido.DtePedido.service.DetallePedidosService;
import DetallePedido.DtePedido.repository.DetallePedidoRepository;
import DetallePedido.DtePedido.dto.ProductoDTO;
import DetallePedido.DtePedido.dto.PedidoDTO;

@Service


public class DetallePedidosService {

    @Autowired
    private DetallePedidoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public DetallePedido guardar(DetallePedido detalle) {
        
        // 1. Validar que el Pedido existe (Puerto 8082)
        try {
            restTemplate.getForObject("http://localhost:8082/api/v1/pedidos/" + detalle.getPedidoId(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: El pedido " + detalle.getPedidoId() + " no existe.");
        }

        // 2. Validar que el Producto existe y obtener su precio (Puerto 8083)
        ProductoDTO producto;
        try {
            producto = restTemplate.getForObject("http://localhost:8083/api/v1/productos/" + detalle.getProductoId(), ProductoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: El producto " + detalle.getProductoId() + " no existe.");
        }

        // 3. Asignar precio y calcular subtotal (¡Aquí ya existe 'producto'!)
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(detalle.getCantidad() * producto.getPrecio());

        // 4. Avisarle a Inventario que descuente el stock (Puerto 8085)
        try {
            int cantidadARestar = -detalle.getCantidad(); 
            String urlInventario = "http://localhost:8085/api/v1/inventario/" + detalle.getProductoId() + "?cantidad=" + cantidadARestar;
            
            // Hacemos un POST a inventario
            restTemplate.postForObject(urlInventario, null, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: No se pudo descontar el stock. Verifica el stock en Inventario.");
        }
    
        // 5. Guardar finalmente el detalle del pedido
        return repository.save(detalle);
    }

}
