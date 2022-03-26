-- Géneros --

INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Terror', 'http://www.imagen-genero.com/terror.png');
INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Drama', 'http://www.imagen-genero.com/drama.png');
INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Comedia', 'http://www.imagen-genero.com/comedia.png');
INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Romance', 'http://www.imagen-genero.com/romance.png');
INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Musical', 'http://www.imagen-genero.com/musical.png');
INSERT INTO Generos(id, nombre, imagen_url) VALUES(NEXT VALUE FOR genero_sequence, 'Fantasía', 'http://www.imagen-genero.com/fantasia.png');


-- Películas --

INSERT INTO Peliculas(id, titulo, imagen_url, fecha_creacion, calificacion, genero_id) VALUES(NEXT VALUE FOR pelicula_sequence, 'El Señor de los Anillos: la Comunidad del Anillo', 'http://www.imagen-pelicula.com/lordoftherings1.png', '2001-10-17', 5, 6);
INSERT INTO Peliculas(id, titulo, imagen_url, fecha_creacion, calificacion, genero_id) VALUES(NEXT VALUE FOR pelicula_sequence, 'El Señor de los Anillos: las Dos Torres', 'http://www.imagen-pelicula.com/lordoftherings2.png', '2002-11-08', 4, 6);
INSERT INTO Peliculas(id, titulo, imagen_url, fecha_creacion, calificacion, genero_id) VALUES(NEXT VALUE FOR pelicula_sequence, 'El Señor de los Anillos: el Retorno del Rey', 'http://www.imagen-pelicula.com/lordoftherings3.png', '2003-12-21', 5, 6);
INSERT INTO Peliculas(id, titulo, imagen_url, fecha_creacion, calificacion, genero_id) VALUES(NEXT VALUE FOR pelicula_sequence, 'El Hobbit: un Viaje Inesperado', 'http://www.imagen-pelicula.com/thehobbit1.png', '2012-05-18', 4, 6);
INSERT INTO Peliculas(id, titulo, imagen_url, fecha_creacion, calificacion, genero_id) VALUES(NEXT VALUE FOR pelicula_sequence, 'No Respires', 'http://www.imagen-pelicula.com/norespires.png', '2016-02-02', 3, 1);


-- Personajes --

INSERT INTO Personajes(id, nombre, imagen_url, edad, peso, historia) VALUES(NEXT VALUE FOR personaje_sequence, 'Frodo Bolsón', 'http://www.imagen-personaje.com/frodo.png', 25, 68.5, 'Es un hobbit, llamado también "Portador del Anillo". Fue el encargado de llevar el Anillo Único hasta el Monte del Destino para destruirlo, acompañado por su valiente y fiel compañero Sam.');
INSERT INTO Personajes(id, nombre, imagen_url, edad, peso, historia) VALUES(NEXT VALUE FOR personaje_sequence, 'Gandalf', 'http://www.imagen-personaje.com/gandalf.png', 64, 84.2, 'Es un mago, uno de los espíritus maia enviados a la Tierra Media durante la Tercera Edad del Sol para ayudar a sus habitantes en la lucha contra el «señor oscuro» Sauron.');
INSERT INTO Personajes(id, nombre, imagen_url, edad, peso, historia) VALUES(NEXT VALUE FOR personaje_sequence, 'Boromir', 'http://www.imagen-personaje.com/boromir.png', 42, 79.7, 'Es un dúnadan, hijo mayor del senescal de Gondor Denethor II, hermano mayor de Faramir y predilecto del vigésimo cuarto y último senescal regente del reino de Gondor.');


-- Películas x Personajes --

INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(1, 1);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(2, 1);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(3, 1);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(1, 2);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(2, 2);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(3, 2);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(4, 2);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(1, 3);
INSERT INTO Pelicula_X_Personaje(pelicula_id, personaje_id) VALUES(2, 3);


-- Roles --

INSERT INTO Roles(id, nombre) VALUES(NEXT VALUE FOR rol_sequence, 'User');


-- Usuarios --
-- El password es '1234' encriptado
INSERT INTO Usuarios(id, email, contrasena, rol_id) VALUES(NEXT VALUE FOR usuario_sequence, 'user@disney.com', '$2a$12$nBaU5lntf5Tx7PP2i5kP9O8RzenGC5H383MnKq5qy7v./KeyXKHCa', 1);