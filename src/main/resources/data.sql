/*Usuario*/
INSERT IGNORE INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

/*Comunidad*/
INSERT IGNORE INTO Comunidad(id, nombre, descripcion, paisOrigen, idioma) VALUES (1, 'Comunidad de prueba', 'Para probar', 'Argentina', 'Español');

/*Asociar usuario con comunidad*/
INSERT IGNORE INTO comunidad_usuario(id_comunidad_fk, id_usuario_fk) VALUES (1, 1);

/*Artistas*/
INSERT IGNORE INTO Preferencia (nombre, tipo) VALUES
('Duki', 'Artista'),
('Emilia', 'Artista'),
('Michael Jackson', 'Artista'),
('Damas Gratis', 'Artista'),
('Bad Bunny', 'Artista'),
('Miranda', 'Artista'),
('Taylor Swift', 'Artista'),
('Luck Ra', 'Artista'),
('Alejandro Sanz', 'Artista'),
('Eminem', 'Artista'),
('Rels B', 'Artista'),
('Shakira', 'Artista');
/*Generos*/
INSERT IGNORE INTO Preferencia (nombre, tipo) VALUES
('Rock', 'Genero'),
('Pop', 'Genero'),
('Hip Hop', 'Genero'),
('Tango', 'Genero'),
('Reggae', 'Genero'),
('Reggaeton', 'Genero'),
('Soul', 'Genero'),
('Metal', 'Genero'),
('Cumbia', 'Genero'),
('R&B', 'Genero'),
('Electronica', 'Genero'),
('Cuarteto', 'Genero');

/*Regiones*/
INSERT IGNORE INTO Preferencia (nombre, tipo) VALUES
('EEUU', 'Region'),
('Italia', 'Region'),
('Europa', 'Region'),
('Asia', 'Region'),
('Africa', 'Region'),
('Colombia', 'Region'),
('Argentina', 'Region'),
('Caribe', 'Region'),
('Mexico', 'Region'),
('España', 'Region'),
('Brasil', 'Region'),
('America Latina', 'Region');

/*Epocas*/
INSERT IGNORE INTO Preferencia (nombre, tipo) VALUES
('1950s', 'Epoca'),
('1960s', 'Epoca'),
('1970s', 'Epoca'),
('1980s', 'Epoca'),
('1990s', 'Epoca'),
('2000s', 'Epoca'),
('2010s', 'Epoca'),
('2020s', 'Epoca'),
('Clasica', 'Epoca'),
('Contemporanea', 'Epoca'),
('Moderna', 'Epoca'),
('Actual', 'Epoca');

INSERT IGNORE INTO Insignia (DTYPE, nombre, descripcion, imagen) VALUES
('Insignia', 'Participante', 'Participaste en tu primer recital', '/img/insignias/participante.png'),
('Insignia', 'Activo', 'Hiciste 5 publicaciones', '/img/insignias/activo.png'),
('Insignia', 'Top Poster', 'Tu publicacion quedo como destacada', '/img/insignias/top_poster.png'),
('Insignia', 'Fanático', 'Marcaste a un artista como favorito', '/img/insignias/fanatico.png'),
('Insignia', 'Bienvenido', 'Iniciaste sesion', '/img/insignias/bienvenido.png'),
('InsigniaPremium', 'Encuestador', 'Creaste una encuesta', '/img/insignias/encuestador.png'),
('InsigniaPremium', 'Creador de Comunidad', 'Creaste una comunidad', '/img/insignias/creador_comunidad_premium.png'),
('InsigniaPremium', 'Suscriptor Premium', 'Compraste el plan premium', '/img/insignias/suscriptor_premium.png');
