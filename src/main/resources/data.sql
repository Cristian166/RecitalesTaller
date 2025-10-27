INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

/*Artistas*/
INSERT INTO Preferencia (nombre, tipo) VALUES
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
INSERT INTO Preferencia (nombre, tipo) VALUES
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
INSERT INTO Preferencia (nombre, tipo) VALUES
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
INSERT INTO Preferencia (nombre, tipo) VALUES
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

INSERT INTO Insignia (nombre, descripcion, imagen) VALUES
('Participante', 'Participaste en tu primer recital', '/img/insignias/participante.png'),
('Activo', 'Hiciste 5 publicaciones', '/img/insignias/activo.png'),
('Top Poster', 'Tu publicacion quedo como destacada', '/img/insignias/top_poster.png'),
('Fanático', 'Marcaste a un artista como favorito', '/img/insignias/fanatico.png'),
('Bienvenido', 'Iniciaste sesion', '/img/insignias/bienvenido.png'),
('Encuestador', 'Creaste una encuesta', '/img/insignias/encuestador.png'),
('Creador de Comunidad', 'Creaste una comunidad', '/img/insignias/creador_comunidad_premium.png'),
('Suscriptor Premium', 'Compraste el plan premium', '/img/insignias/suscriptor_premium.png');

