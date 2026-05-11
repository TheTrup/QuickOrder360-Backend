package MicroServicioDespachos.Despachos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "despachos")
public class Despachos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(name = "direccion_entrega", nullable = false)
    private String direccionEntrega;

    @Column(name = "repartidor")
    private String repartidor;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_despacho", updatable = false)
    private LocalDateTime fechaDespacho;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @PrePersist
    protected void onCreate() {
        this.fechaDespacho = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "PREPARANDO"; // Estado por defecto
        }
    }

    public Despachos() {}

    // ¡Agrega todos los Getters y Setters aquí!
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public String getRepartidor() { return repartidor; }
    public void setRepartidor(String repartidor) { this.repartidor = repartidor; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaDespacho() { return fechaDespacho; }
    public void setFechaDespacho(LocalDateTime fechaDespacho) { this.fechaDespacho = fechaDespacho; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}

