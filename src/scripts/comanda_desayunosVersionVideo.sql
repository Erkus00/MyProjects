-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-11-2022 a las 20:01:54
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `comanda_desayunos`
--
CREATE DATABASE IF NOT EXISTS `comanda_desayunos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `comanda_desayunos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta`
--

DROP TABLE IF EXISTS `carta`;
CREATE TABLE IF NOT EXISTS `carta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `precio` float NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `carta`
--

INSERT INTO `carta` (`id`, `nombre`, `tipo`, `precio`, `disponibilidad`) VALUES
(1, 'PITUFO MIXTO', 'PANADERIA', 1.5, 1),
(2, 'COCACOLA', 'BEBIDA', 2, 1),
(3, 'PITUFO BACON MAYO', 'PANADERIA', 2.5, 1),
(4, 'CACAHUETES', 'OTRO', 1, 1),
(5, 'FANTA NARANJA', 'BEBIDA', 2, 1),
(6, 'PEPSI', 'BEBIDA', 2, 1),
(7, 'ALTRAMUCES', 'OTRO', 0.5, 1),
(8, 'PATATAS', 'OTRO', 1.75, 1),
(9, 'GOLOSINAS', 'OTRO', 0.5, 1),
(10, 'PISTACHOS', 'OTRO', 2.5, 1),
(11, 'ACEITUNAS', 'OTRO', 2.8, 1),
(12, 'PITUFO CATALANA', 'PANADERIA', 2, 1),
(13, 'PITUFO TORTILLA', 'PANADERIA', 2.3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carta_pedido`
--

DROP TABLE IF EXISTS `carta_pedido`;
CREATE TABLE IF NOT EXISTS `carta_pedido` (
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`,`id_pedido`),
  KEY `id_producto` (`id_producto`),
  KEY `id_pedido` (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `carta_pedido`
--

INSERT INTO `carta_pedido` (`id_producto`, `cantidad`, `id_pedido`) VALUES
(2, 2, 3),
(3, 1, 3),
(11, 6, 3),
(12, 4, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `cliente` text NOT NULL,
  `estado` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `fecha`, `cliente`, `estado`) VALUES
(2, '2022-11-23', 'CARLOS', 'RECOGIDO'),
(3, '2022-11-23', 'CARLOS', 'PENDIENTE');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carta_pedido`
--
ALTER TABLE `carta_pedido`
  ADD CONSTRAINT `carta_pedido_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `carta` (`id`),
  ADD CONSTRAINT `carta_pedido_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
