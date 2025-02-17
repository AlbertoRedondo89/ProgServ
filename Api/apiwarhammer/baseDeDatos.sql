CREATE DATABASE IF NOT EXISTS warhmr;
USE warhmr;

CREATE TABLE faccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    raza VARCHAR(50) NOT NULL
);

CREATE TABLE tipo_unidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE unidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    faccion_id INT,
    tipo_unidad_id INT,
    puntos INT NOT NULL,
    FOREIGN KEY (faccion_id) REFERENCES faccion(id) ON DELETE SET NULL,
    FOREIGN KEY (tipo_unidad_id) REFERENCES tipo_unidad(id) ON DELETE SET NULL
);

INSERT INTO faccion (nombre, raza) VALUES
('Ultramarines', 'Humanos'),
('Orkos', 'Orkos');

INSERT INTO tipo_unidad (nombre) VALUES
('Infantería'),
('Vehículo'),
('Monstruo');

INSERT INTO unidades (nombre, faccion_id, tipo_unidad_id, puntos) VALUES
('Marines Espaciales', 1, 1, 100),
('Dreadnought', 1, 2, 200),
('Nobz Orkos', 2, 1, 120);


CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    api_key VARCHAR(255) NOT NULL UNIQUE,
    role ENUM('admin', 'basic') NOT NULL DEFAULT 'basic'
);
