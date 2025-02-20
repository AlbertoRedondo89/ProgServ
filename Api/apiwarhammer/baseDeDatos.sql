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

CREATE TABLE habilidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion text
);

CREATE TABLE unidad_habilidad (
    unidad_id INT,
    habilidad_id INT,
    FOREIGN KEY (unidad_id) REFERENCES unidades(id) ON DELETE CASCADE,
    FOREIGN KEY (habilidad_id) REFERENCES habilidad(id) ON DELETE CASCADE,
    PRIMARY KEY (unidad_id, habilidad_id)
);

INSERT INTO faccion (nombre, raza) VALUES
('Ultramarines', 'Humanos'),
('Orkos', 'Orkos'),
('Tau', 'Tau'),
('Necrones', 'Necrones'),
('Guardia Imperial', 'Humanos'),
('Tiránidos', 'Tiránidos'),
('Marines espaciales del Caos', 'Humanos');

INSERT INTO tipo_unidad (nombre) VALUES
('Infantería'),
('Vehículo'),
('Monstruo'),
('Personaje');

-- Insertar más unidades en la tabla unidades
INSERT INTO unidades (nombre, faccion_id, tipo_unidad_id, puntos) VALUES
-- Ultramarines (Humanos)
('Capitán Ultramarine', 1, 4, 150),
('Tactical Squad', 1, 1, 100),
('Terminators Ultramarines', 1, 1, 250),
('Rhino Transport', 1, 2, 120),

-- Orkos
('Warboss Orko', 2, 4, 130),
('Boyz Orkos', 2, 1, 80),
('Trukk Orko', 2, 2, 90),
('Meka Dread', 2, 2, 220),

-- Tau
('Shas’O Tau', 3, 4, 180),
('Fire Warriors', 3, 1, 120),
('Hammerhead Gunship', 3, 2, 250),
('Pathfinders Tau', 3, 1, 100),

-- Necrones
('Overlord Necron', 4, 4, 170),
('Warriors Necrones', 4, 1, 110),
('Ghost Ark', 4, 2, 200),
('Destroyers Necrones', 4, 1, 150),

-- Guardia Imperial
('Comandante Guardia Imperial', 5, 4, 140),
('Infantry Squad', 5, 1, 90),
('Leman Russ', 5, 2, 200),
('Sentinels', 5, 2, 100),

-- Tiránidos
('Hive Tyrant', 6, 4, 180),
('Genestealers', 6, 1, 100),
('Carnifex', 6, 1, 150),
('Tyrannofex', 6, 2, 230),

-- Marines espaciales del Caos
('Lord del Caos', 7, 4, 160),
('Chaos Space Marines', 7, 1, 120),
('Predator Caos', 7, 2, 220),
('Demon Prince', 7, 4, 300);


-- Insertar habilidades en la tabla habilidad
INSERT INTO habilidad (nombre, descripcion) VALUES
('Lone Operative', 'Unless part of an Attached unit (see Leader), this unit can only be selected as the target of a ranged attack if the attacking model is within 12".'),
('Stealth', 'If every model in a unit has this ability, then each time a ranged attack is made against it, subtract 1 from that attack’s Hit roll.'),
('Feel no pain', 'Some models have ‘Feel No Pain x+’ listed in their abilities. Each time a model with this ability suffers damage and so would lose a wound (including wounds lost due to mortal wounds), roll one D6: if the result is greater than or equal to the number denoted by "x’, that wound is ignored and is not lost. If a model has more than one Feel No Pain ability, you can only use one of those abilities each time that model suffers damage and so would lose a wound.'),
('Deadly demise', 'Some models have ‘Deadly Demise x’ listed in their abilities. When such a model is destroyed, roll one D6 before removing it from play (if such a model is a TRANSPORT, roll before any embarked models disembark). On a 6, each unit within 6" of that model suffers a number of mortal wounds denoted by ‘x’ (if this is a random number, roll separately for each unit within 6").'),
('Figth first', 'Units with this ability that are eligible to fight do so in the Fights First step, provided every model in the unit has this ability.'),
('Infiltrator', 'During deployment, if every model in a unit has this ability, then when you set it up, it can be set up anywhere on the battlefield that is more than 9" horizontally away from the enemy deployment zone and all enemy models.'),
('Leader', 'Some CHARACTER units have ‘Leader’ listed on their datasheets. Such CHARACTER units are known as Leaders, and the units they can lead – known as their Bodyguard units – are listed on their datasheet.'),
('Scout', 'Some units have "Scouts x"’ listed in their abilities. If every model in a unit has this ability, then at the start of the first battle round, before the first turn begins, it can make a Normal move of up to x", with the exception that, while making that move, the distance moved by each model in that unit can be greater than that models Move characteristic, as long as it is not greater than x".');


-- Ultramarines
INSERT INTO unidad_habilidad (unidad_id, habilidad_id) VALUES
(1, 2), (1, 7), -- Capitán Ultramarine
(2, 6), (2, 8), -- Tactical Squad
(3, 2), (3, 5), -- Terminators Ultramarines
(4, 4), (4, 1), -- Rhino Transport

-- Orkos
(5, 5), (5, 8), -- Warboss Orko
(6, 3), (6, 6), -- Boyz Orkos
(7, 4), (7, 7), -- Trukk Orko
(8, 1), (8, 5), -- Meka Dread

-- Tau
(9, 7), (9, 8), -- Shas’O Tau
(10, 2), (10, 6), -- Fire Warriors
(11, 4), (11, 3), -- Hammerhead Gunship
(12, 6), (12, 1), -- Pathfinders Tau

-- Necrones
(13, 7), (13, 3), -- Overlord Necron
(14, 2), (14, 8), -- Warriors Necrones
(15, 1), (15, 4), -- Ghost Ark
(16, 5), (16, 6), -- Destroyers Necrones

-- Guardia Imperial
(17, 7), (17, 3), -- Comandante Guardia Imperial
(18, 6), (18, 5), -- Infantry Squad
(19, 4), (19, 8), -- Leman Russ
(20, 2), (20, 1), -- Sentinels

-- Tiránidos
(21, 5), (21, 7), -- Hive Tyrant
(22, 6), (22, 2), -- Genestealers
(23, 3), (23, 8), -- Carnifex
(24, 4), (24, 1), -- Tyrannofex

-- Marines espaciales del Caos
(25, 7), (25, 2), -- Lord del Caos
(26, 3), (26, 6), -- Chaos Space Marines
(27, 4), (27, 5), -- Predator Caos
(28, 8), (28, 1); -- Demon Prince


CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    apiKey VARCHAR(255) NOT NULL UNIQUE,
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user'
);

INSERT INTO usuarios (username, password, apiKey, role) VALUES
('admin1', '1234', '12341234', 'admin');
