-- liquibase formatted sql

-- changeset JuanJorquera:1
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    estado VARCHAR(50)
);