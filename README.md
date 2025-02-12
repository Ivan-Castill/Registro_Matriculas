<h1>Proyecto Adicional de Recuperacion</h1>
<p> Gestor de Registro con Conexión a Base de Datos  </p>
<br/>
<br/>
Configuracion de la base de datos en SQL : <br/>
<br/>
CREATE TABLE `datosusuarios` (<br/>
  `id_CodigoUnico` int NOT NULL AUTO_INCREMENT,<br/>
  `Cedula` int NOT NULL,<br/>
  `Nombres` varchar(50) NOT NULL,<br/>
  `Apellidos` varchar(50) NOT NULL,<br/>
  `Provincia` enum('Azuay','Bolívar','Cañar','Carchi','Chimborazo','Cotopaxi','El Oro','Esmeraldas','Galápagos','Guayas','Imbabura','Loja','Los Ríos','Manabí','Morona Santiago','Napo','Orellana','Pastaza','Pichincha','Santa Elena','Santo Domingo','Sucumbíos','Tungurahua','Zamora Chinchipe') NOT NULL,<br/>
  `Capital` varchar(50) NOT NULL,<br/>
  `Quintil` enum('1','2','3','4','5') NOT NULL, <br/>
  `FechaNacimiento` date NOT NULL, <br/>
  `Correo` varchar(100) NOT NULL, <br/>
  `Edad` int NOT NULL, <br/>
  `FechaRegistro` date NOT NULL, <br/>
  `PagoMatricula` decimal(10,2) NOT NULL, <br/>
  `SevicioAdicional` enum('Libros','Certificados','Curso de Inglés','Asignatura Repetida','No asignado') NOT NULL, <br/>
  `FormaPago` enum('Efectivo','Deposito','Transferencia','Tarjeta de Crédito') NOT NULL, <br/>
  `Subtotal` decimal(10,2) NOT NULL, <br/>
  `Total` decimal(10,2) NOT NULL, <br/>
  PRIMARY KEY (`id_CodigoUnico`), <br/>
  UNIQUE KEY `id_CodigoUnico_UNIQUE` (`id_CodigoUnico`), <br/>
  UNIQUE KEY `Cedula_UNIQUE` (`Cedula`), <br/>
  UNIQUE KEY `Correo_UNIQUE` (`Correo`) <br/>
) ENGINE=InnoDB AUTO_INCREMENT=231132124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci <br/>
