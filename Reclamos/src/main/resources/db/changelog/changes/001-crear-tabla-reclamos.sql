CREATE TABLE reclamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    estado VARCHAR(50) NOT NULL, -- Ej: 'PENDIENTE', 'EN_REVISION', 'RESUELTO', 'RECHAZADO'
    fecha_reclamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolucion TEXT -- Explicación de cómo se solucionó (vacío al principio)
);