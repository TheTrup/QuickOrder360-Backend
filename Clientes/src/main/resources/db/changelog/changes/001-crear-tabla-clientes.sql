-- liquibase formatted sql
-- changeset JuanJorquera:1
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);