CREATE TABLE detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT,
    producto_id BIGINT,
    cantidad INT,
    precio_unitario DOUBLE PRECISION(10,2),
    subtotal DOUBLE PRECISION(10,2)
);