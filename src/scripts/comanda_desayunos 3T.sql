-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2022 a las 08:51:59
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET
    SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET
    time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `comanda_desayunos`
--
CREATE
    DATABASE IF NOT EXISTS `comanda_desayunos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE
    `comanda_desayunos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta`
--

CREATE TABLE if NOT EXISTS `carta`
(
    `id`             INT(11)      NOT NULL AUTO_INCREMENT,
    `nombre`         VARCHAR(255) NOT NULL,
    `tipo`           VARCHAR(255) NOT NULL,
    `precio`         FLOAT        NOT NULL,
    `disponibilidad` tinyint(1)   NOT NULL,
    PRIMARY KEY
        (
         `id`
            )
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4;

--
-- Truncar tablas antes de insertar `carta`
--

TRUNCATE TABLE carta;
--
-- Volcado de datos para la tabla `carta`
--

INSERT INTO carta (id, nombre, tipo, precio, disponibilidad)
VALUES (1, 'PITUFO MIXTO', 'PANADERIA', 1.5, 1),
       (2, 'COCACOLA', 'BEBIDA', 2, 1),
       (3, 'PITUFO BACON MAYO', 'PANADERIA', 2.5, 1),
       (4, 'CACAHUETES', 'OTRO', 1, 1),
       (5, 'FANTA NARANJA', 'BEBIDA', 2, 1),
       (6, 'PEPSI', 'BEBIDA', 2, 1),
       (7, 'ALTRAMUCES', 'OTRO', 0.5, 1),
       (8, 'PATATAS', 'OTRO', 1.75, 1),
       (9, 'GOLOSINAS', 'OTRO', 0.5, 1),
       (10, 'PISTACHOS', 'OTRO', 2.5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta_pedido`
--

CREATE TABLE if NOT EXISTS `carta_pedido`
(
    `id_producto` INT(11) NOT NULL,
    `cantidad`    INT(11) NOT NULL,
    `id_pedido`   INT(11) NOT NULL,
    PRIMARY KEY
        (
         `id_producto`,
         `id_pedido`
            ),
    KEY `id_producto`
        (
         `id_producto`
            ),
    KEY `id_pedido`
        (
         `id_pedido`
            )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Truncar tablas antes de insertar `carta_pedido`
--

TRUNCATE TABLE carta_pedido;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE if NOT EXISTS `pedido`
(
    `id`      INT(11)      NOT NULL AUTO_INCREMENT,
    `fecha`   DATE         NOT NULL,
    `cliente` text         NOT NULL,
    `estado`  VARCHAR(255) NOT NULL,
    PRIMARY KEY
        (
         `id`
            )
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

--
-- Truncar tablas antes de insertar `pedido`
--

TRUNCATE TABLE pedido;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carta_pedido`
--
ALTER TABLE carta_pedido
    ADD CONSTRAINT carta_pedido_ibfk_1 FOREIGN KEY (id_producto) REFERENCES carta (id),
    ADD CONSTRAINT `carta_pedido_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
