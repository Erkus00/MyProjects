SET
SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET
time_zone = "+00:00";

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

DROP TABLE IF EXISTS `carta`;
CREATE TABLE IF NOT EXISTS `carta`
(
    `id` int
(
    11
) NOT NULL AUTO_INCREMENT,
    `nombre` varchar
(
    255
) NOT NULL,
    `tipo` varchar
(
    255
) NOT NULL,
    `precio` float NOT NULL,
    `disponibilidad` tinyint
(
    1
) NOT NULL,
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE IF NOT EXISTS `pedido`
(
    `id` int
(
    11
) NOT NULL AUTO_INCREMENT,
    `identificador` int
(
    11
) NOT NULL,
    `fecha` date NOT NULL,
    `cliente` text NOT NULL,
    `estado` varchar
(
    255
) NOT NULL,
    `cantidad` int
(
    11
) NOT NULL,
    `producto` int
(
    11
) NOT NULL,
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

