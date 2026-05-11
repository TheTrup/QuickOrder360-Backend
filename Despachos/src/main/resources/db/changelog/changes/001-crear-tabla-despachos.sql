CREATE TABLE despachos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    direccion_entrega VARCHAR(255) NOT NULL,
    repartidor VARCHAR(100), -- Nombre del conductor (puede estar vacío al principio)
    estado VARCHAR(50) NOT NULL, -- Ej: 'PREPARANDO', 'EN_CAMINO', 'ENTREGADO'
    fecha_despacho TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega TIMESTAMP NULL
);