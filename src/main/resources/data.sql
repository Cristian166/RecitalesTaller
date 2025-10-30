INSERT INTO Usuario(id, email, password, rol, activo) VALUES(1, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- ingresar comunidad para ejemplo

INSERT INTO Comunidad(id,nombre,descripcion,paisOrigen,idioma) VALUES ( 1, 'Comunidad de prueba' , 'Para probar', 'Argentina', 'Español');

-- Asociar usuario con comunidad

INSERT INTO comunidad_usuario(id_comunidad_fk, id_usuario_fk) VALUES (1, 1);