CREATE SCHEMA bdcloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE bdcloud;

CREATE TABLE plataforma (
  id_pform  varchar(3)  NOT NULL PRIMARY KEY,
  nom_pform varchar(30) DEFAULT NULL,
  web_pform varchar(50) DEFAULT NULL
);

INSERT INTO plataforma (id_pform, nom_pform, web_pform) VALUES
('AWS', 'Amazon Web Services',  'https://aws.amazon.com/es/'),
('AZR', 'Microsoft Azure',		'https://azure.microsoft.com/es-es'),
('GC',  'Google Cloud',			'https://cloud.google.com/?hl=es'),
('OC',  'Oracle Cloud',			'https://www.oracle.com/es/cloud/'),
('IO',  'IONOS',				'https://www.ionos.es/cloud/');


CREATE TABLE infraestructura (
  id_service 	int PRIMARY KEY AUTO_INCREMENT,
  des_service   varchar(30) DEFAULT NULL,
  plataforma    varchar(3) NOT NULL,
  tipo			varchar(10) DEFAULT NULL,
  precio_mes	double DEFAULT 0.0,
  creada 		date DEFAULT NULL,
  foreign key (plataforma) references plataforma(id_pform)   
);

INSERT INTO infraestructura (id_service, des_service, plataforma, tipo, precio_mes, creada) VALUES
(1, 'Ubuntu',   			'AWS', 'VM',	12.50, '2023-11-05'),
(2, 'Debian',   			'AZR', 'VM',	11.25, '2023-11-02'),
(3, 'Drive 100GB',  		'AZR', 'ST',	5.75,  '2023-10-25'),
(4, 'API Rest',  			'GC',  'AW',	3.55,  '2023-10-12'),
(5, 'Database Oracle',		'OC',  'DB',	8.50,  '2023-11-08'),
(6, 'Database SQL Server',	'AWS', 'DB',	12.50, '2023-11-05');


SELECT id_pform, nom_pform, web_pform, id_service, des_service, plataforma, precio_mes, creada
	FROM plataforma, infraestructura
	WHERE plataforma.id_pform=infraestructura.plataforma;
