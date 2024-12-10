CREATE DATABASE IF NOT EXISTS recetas;

USE recetas;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE recetas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipoCocina VARCHAR(50),
    ingredientes TEXT,
    instrucciones TEXT,
    tiempo_coccion INT, -- Asegúrate de incluir esta columna
    dificultad VARCHAR(50)
);

INSERT INTO usuarios (nombre_usuario, contrasena, email) VALUES
('usuario1', '$2a$10$OBHvxyKALayl/uSoldOWaumcwO6JRbRoWhGPoh8ygT6faoUOWkrgi', 'usuario1@example.com'),
('usuario2', '$2a$10$CwVfArg9ela2b2wlfppJa.ystosCVm5FfrfmfPJtZ2vUYDFLlPg7e', 'usuario2@example.com');