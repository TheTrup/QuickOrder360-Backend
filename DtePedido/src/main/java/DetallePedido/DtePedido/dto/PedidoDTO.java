package DetallePedido.DtePedido.dto;

public class PedidoDTO {

    private Long id;
    private Long clienteId;

    // Constructores
    public PedidoDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

}
