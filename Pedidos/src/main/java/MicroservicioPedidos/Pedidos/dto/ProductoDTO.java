package MicroservicioPedidos.Pedidos.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Integer stock; 
}