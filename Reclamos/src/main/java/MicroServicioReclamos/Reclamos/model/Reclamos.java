package MicroServicioReclamos.Reclamos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "reclamos")
public class Reclamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_reclamo", updatable = false)
    private LocalDateTime fechaReclamo;

    @Column(name = "resolucion", columnDefinition = "TEXT")
    private String resolucion;

    @PrePersist
    protected void onCreate() {
        this.fechaReclamo = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }

    public Reclamos() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaReclamo() { return fechaReclamo; }
    public void setFechaReclamo(LocalDateTime fechaReclamo) { this.fechaReclamo = fechaReclamo; }
    public String getResolucion() { return resolucion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
}


