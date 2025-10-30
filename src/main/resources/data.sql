INSERT INTO Insignia (nombre, descripcion, imagen, DTYPE) VALUES
('Participante', 'Participaste en tu primer recital', '/img/insignias/participante.png', 'Insignia'),
('Activo', 'Hiciste 5 publicaciones', '/img/insignias/activo.png', 'Insignia'),
('Top Poster', 'Tu publicacion quedo como destacada', '/img/insignias/top_poster.png', 'Insignia'),
('Fanático', 'Marcaste a un artista como favorito', '/img/insignias/fanatico.png', 'Insignia'),
('Bienvenido', 'Iniciaste sesion', '/img/insignias/bienvenido.png', 'Insignia'),
('Encuestador', 'Creaste una encuesta', '/img/insignias/encuestador.png', 'Insignia'),
('Creador de Comunidad', 'Creaste una comunidad', '/img/insignias/creador_comunidad_premium.png', 'Insignia'),
('Suscriptor Premium', 'Compraste el plan premium', '/img/insignias/suscriptor_premium.png', 'Insignia');

INSERT INTO Usuario(id, email, password, rol, activo) VALUES(1, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- ingresar comunidad para ejemplo

INSERT INTO Comunidad(id,nombre,descripcion,paisOrigen,idioma) VALUES ( 1, 'Comunidad de prueba' , 'Para probar', 'Argentina', 'Español');

-- Asociar usuario con comunidad

INSERT INTO comunidad_usuario(id_comunidad_fk, id_usuario_fk) VALUES (1, 1);