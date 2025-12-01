-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bdtransportistas COLLATE utf8mb4_general_ci;
USE bdtransportistas;

-- Crear tabla para vehículos
CREATE TABLE IF NOT EXISTS vehiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(10) UNIQUE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    capacidad_carga DECIMAL(10,2) NOT NULL
);

-- Crear tabla para transportistas
CREATE TABLE IF NOT EXISTS transportistas (
    id_transportista INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_licencia VARCHAR(10) NOT NULL,
    experiencia INT NOT NULL,
    id_vehiculo INT,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id) ON DELETE SET NULL
);

-- Insertar datos de vehículos
INSERT INTO vehiculos (id, matricula, tipo, capacidad_carga) VALUES
(1,'8765XYZ', 'Tráiler', 24000.00),
(2,'5432ABC', 'Camión rígido', 12000.00),
(3,'1234DEF', 'Camión cisterna', 18000.00),
(4,'7890GHI', 'Furgoneta grande', 3500.00),
(5,'2468JKL', 'Tráiler refrigerado', 22000.00),
(6,'1357MNO', 'Camión volquete', 15000.00);

-- Insertar datos de transportistas
INSERT INTO transportistas (nombre, tipo_licencia, experiencia, id_vehiculo) VALUES
('Miguel Ángel Torres', 'C+E', 15, 1),
('Sarah Chen', 'C', 8, 2),
('Antonio Rossi', 'C+E', 22, 3),
('Elena Petrov', 'C', 6, 4),
('Carlos Mendoza', 'C+E', 18, 5),
('Olga Nowak', 'C', 11, 6);

COMMIT;

SELECT transportistas.*,vehiculos.* FROM transportistas,vehiculos WHERE transportistas.id_vehiculo=vehiculos.id;
