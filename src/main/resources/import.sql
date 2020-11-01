INSERT INTO empresa (activo,direccion,email,fecha_registro,nit,nombre) 	VALUES (true,'JARDÍN PLAZA','FRANCAFE@GMAIL.COM','now()','555555695','CAFETERIA FRANCAFE');
INSERT INTO rol (id, descripcion, rol) VALUES(1, 'Administrador', 'ROLE_ADMIN');
INSERT INTO rol (id, descripcion, rol) VALUES(2, 'Gerente', 'ROLE_GERENTE');
INSERT INTO rol (id, descripcion, rol) VALUES(3, 'Empleado', 'ROLE_EMPLEADO');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(1, 'Registro civil', 'RC');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(2, 'Tarjeta de identidad', 'TI');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(3, 'Cédula de ciudadanía', 'CC');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(4, 'Cédula de extranjería', 'CE');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(5, 'Pasaporte', 'PA');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(6, 'Certificado de nacido vivo', 'NV');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(7, 'Carné diplomático', 'CD');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(8, 'Salvo conducto de permanencia', 'SC');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(9, 'Pasaporte de la ONU', 'PR');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(10, 'Permiso Especial de Permanencia', 'PE');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(11, 'NIT', 'NI');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(12, 'Adulto sin Identificación', 'AS');
INSERT INTO tipo_identificacion(id, descripcion, tipo)VALUES(13, 'Menor sin Identificación', 'MS');
INSERT INTO usuario (id, apellidos, email, "enable", fecha_actualizacion, fecha_registro, identificacion, nombres, "password", username, id_empresa, id_rol, id_tipo_identificacion)
VALUES(1, 'RAMÓN MONTES', 'omarmontes.879@gmail.com', true, '2019-11-11 22:35:53.663', '2019-06-29 15:47:30.000', '1093792354', 'OMAR', '$2a$10$3UGKUUuR.NssADUoTYSmJecIc00GMQGI1wKErenoDrwbpW23JxmEi', 'OMARRM', NULL, 1, 3);

