INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Mascota (id, nombre, descripcion, adoptado, img, salud, comportamiento, edad, tipo, sexo, tamano, nivelEnergia)
VALUES
    (null, 'LUNA', 'Luna fue rescatada de las calles después de un accidente, es una perra muy cariñosa y tranquila que busca un hogar donde le den mucho amor.', false, 'mascotas/perro8.webp', 'sana, tiene todas las vacunas', 'social', 3, 'PERRO', 'HEMBRA', 'CHICO', 'BAJO'),
    (null, 'MAX', 'Max fue encontrado en un parque mientras jugaba con niños. Es un perro protector y lleno de energía que necesita espacio para correr.', false, 'mascotas/perro7.jfif', 'buena salud, desparasitado', 'activo', 2, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO'),
    (null, 'MILA', 'Mila fue adoptada y devuelta por una familia que no pudo cuidarla. Es una gata dulce y calmada que se adapta a hogares con niños.', false, 'mascotas/gato4.jpg', 'sana, vacunada', 'calma', 2, 'GATO', 'HEMBRA', 'CHICO', 'MEDIO'),
    (null, 'VITO', 'Vito vivía en una granja y ahora busca una familia que le brinde mucho ejercicio y cariño, ya que es muy activo y juguetón.', false, 'mascotas/perro5.jpg', 'sano, con chip', 'activo', 5, 'PERRO', 'MACHO', 'GRANDE', 'ALTO'),
    (null, 'NALA', 'Nala fue encontrada en un refugio abandonada, pero es una gata curiosa y sociable que ama la compañía de otros animales.', false, 'mascotas/gato2.jpg', 'salud óptima', 'social', 1, 'GATO', 'HEMBRA', 'CHICO', 'ALTO'),
    (null, 'SIMON', 'Simon fue rescatado de una casa donde lo maltrataban. Ahora es un perro calmado y fiel que solo quiere un hogar seguro.', false, 'mascotas/perro6.jpg', 'buena salud, sin alergias', 'calma', 6, 'PERRO', 'MACHO', 'GRANDE', 'BAJO'),
    (null, 'KITTY', 'Kitty es una gata que fue encontrada en la calle, pequeña y cariñosa, ideal para vivir en departamentos o espacios pequeños.', false, 'mascotas/gato3.jpg', 'sana, todas las vacunas', 'social', 3, 'GATO', 'HEMBRA', 'CHICO', 'MEDIO'),
    (null, 'ZEUS', 'Zeus es un perro inteligente que fue abandonado por su familia anterior. Busca un hogar donde pueda aprender y ser parte de la familia.', false, 'mascotas/perro4.jpg', 'salud estable', 'activo', 4, 'PERRO', 'MACHO', 'GRANDE', 'MEDIO'),
    (null, 'TINA', 'Tina fue encontrada en un refugio y es una gata tranquila que ama descansar y recibir caricias.', false, 'mascotas/gato1.jpg', 'buena salud', 'calma', 5, 'GATO', 'HEMBRA', 'MEDIANO', 'BAJO'),
    (null, 'BRUNO', 'Bruno es un perro que estuvo en un hogar temporal donde demostró ser muy juguetón y amigable con los niños.', false, 'mascotas/perro3.jpg', 'sano y vacunado', 'social', 2, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO'),
    (null, 'SIRA', 'Sira es una perra independiente que vivía en la calle, pero se adaptó rápido a la vida en hogares y busca un espacio tranquilo.', false, 'mascotas/perro2.jpg', 'salud perfecta', 'calma', 4, 'PERRO', 'HEMBRA', 'CHICO', 'BAJO'),
    (null, 'COCO', 'Coco fue rescatado de una situación de abandono y necesita una familia activa que pueda brindarle mucho tiempo y cariño.', false, 'mascotas/perro1.jpg', 'sano, desparasitado', 'activo', 6, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO');

INSERT INTO SolicitudAdopcion
(id, nombre, email, tipoVivienda, espacioDisponible, otrosAnimales, experiencia,fk_mascota, mascotaId, estado)
VALUES
    (null, 'Juan Pérez', 'juan@example.com', 'Casa', 'Patio grande', 1, '5 años con perros grandes', 1, 1,'PENDIENTE');
/*
INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'LUNA', 5, 'HEMBRA', 'PERRO', 'Rescatada de la calle.','perro6.jpg', false,'Vacunada, desparasitada', 'Muy dócil y sociable');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'MILO', 3, 'MACHO', 'PERRO', 'Rescatado de la calle.','perro7.jfif', false,'Sano, con todas las vacunas', 'Muy activo y cariñoso');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'NALA', 6, 'HEMBRA', 'PERRO', 'Rescatada de la calle.','perro2.jpg', false,'Vacunada, desparasitada', 'Muy dócil y sociable');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'COCO', 2, 'MACHO', 'PERRO', 'Rescatado de la calle.','perro4.jpg', false,'Sano, con todas las vacunas', 'Muy activo y cariñoso');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'VITO', 5, 'MACHO', 'PERRO', 'Rescatado de la calle.','perro5.jpg', false,'Sano, con todas las vacunas', 'Muy activo y cariñoso');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'MICHI', 2, 'HEMBRA', 'GATO', 'Rescatado de la calle.','gato1.jpg', false,'Vacunada, desparasitada', 'Muy dócil y sociable');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'TOTO', 2, 'MACHO', 'PERRO', 'Rescatado de la calle.','mascotas/perro8.webp', false,'Sano, con todas las vacunas', 'Muy activo y cariñoso');

INSERT INTO Mascota (id, nombre, edad, sexo, tipo, descripcion, img, adoptado, salud, comportamiento)
VALUES (null, 'TOBY', 3, 'MACHO', 'PERRO', 'Rescatado de la calle.','perro1.jpg', false,'Sano, con todas las vacunas', 'Muy activo y cariñoso');
*/