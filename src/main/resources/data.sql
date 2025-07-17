INSERT INTO Usuario(id, email, password, rol, activo, nombre, edadPreferida, tipoPreferido,tamanoPreferido,nivelEnergiaPreferido,sexoPreferido)
VALUES
    (null, 'test@unlam.edu.ar', '$2a$12$TgbkpeMDAIBCaJSjvrGY6uYNg61xiWO3lLmCiTLkq3m.7SYwl8ZdK', 'ADMIN', true, 'Administrador', null, null, null,null,null),
    (null, 'regi@gmail.com', '$2a$12$TgbkpeMDAIBCaJSjvrGY6uYNg61xiWO3lLmCiTLkq3m.7SYwl8ZdK', 'USUARIO', true, 'Regina', null, null, null,null,null);

INSERT INTO Mascota (id, nombre, descripcion, adoptado, img, salud, comportamiento, edad, tipo, sexo, tamano, nivelEnergia, estado, usuario_id)
VALUES
    (null, 'LUNA', 'Luna fue rescatada de las calles después de un accidente, es una perra muy cariñosa y tranquila que busca un hogar donde le den mucho amor.', false, 'images/mascotas/perro8.webp', 'sana, tiene todas las vacunas', 'social', 3, 'PERRO', 'HEMBRA', 'MEDIANO', 'BAJO', 'Aprobada',1),
    (null, 'MAX', 'Max fue encontrado en un parque mientras jugaba con niños. Es un perro protector y lleno de energía que necesita espacio para correr.', false, 'images/mascotas/perro7.jfif', 'buena salud, desparasitado', 'activo', 2, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1),
    (null, 'MILA', 'Mila fue adoptada y devuelta por una familia que no pudo cuidarla. Es una gata dulce y calmada que se adapta a hogares con niños.', false, 'images/mascotas/gato4.jpg', 'sana, vacunada', 'calma', 2, 'GATO', 'HEMBRA', 'CHICO', 'MEDIO', 'Aprobada',1),
    (null, 'VITO', 'Vito vivía en una granja y ahora busca una familia que le brinde mucho ejercicio y cariño, ya que es muy activo y juguetón.', false, 'images/mascotas/perro5.jpg', 'sano, con chip', 'activo', 5, 'PERRO', 'MACHO', 'CHICO', 'ALTO', 'Aprobada',1),
    (null, 'BLANCA', 'Blanca fue encontrada en un refugio abandonada, pero es una gata curiosa y sociable que ama la compañía de otros animales.', false, 'images/mascotas/gato2.jpg', 'salud óptima', 'social', 1, 'GATO', 'HEMBRA', 'CHICO', 'ALTO', 'Aprobada',1),
    (null, 'SIMON', 'Simon fue rescatado de una casa donde lo maltrataban. Ahora es un perro calmado y fiel que solo quiere un hogar seguro.', false, 'images/mascotas/perro6.jpg', 'buena salud, sin alergias', 'calma', 6, 'PERRO', 'MACHO', 'CHICO', 'BAJO', 'Aprobada',1),
    (null, 'KITTY', 'Kitty es una gata que fue encontrada en la calle, pequeña y cariñosa, ideal para vivir en departamentos o espacios pequeños.', false, 'images/mascotas/gato3.jpg', 'sana, todas las vacunas', 'social', 3, 'GATO', 'HEMBRA', 'MEDIANO', 'MEDIO', 'Aprobada',1),
    (null, 'ZEUS', 'Zeus es un perro inteligente que fue abandonado por su familia anterior. Busca un hogar donde pueda aprender y ser parte de la familia.', false, 'images/mascotas/perro4.jpg', 'salud estable', 'activo', 4, 'PERRO', 'MACHO', 'MEDIANO', 'MEDIO', 'Aprobada',1),
    (null, 'TINA', 'Tina fue encontrada en un refugio y es una gata tranquila que ama descansar y recibir caricias.', false, 'images/mascotas/gato1.jpg', 'buena salud', 'calma', 5, 'GATO', 'HEMBRA', 'MEDIANO', 'BAJO', 'Aprobada',1),
    (null, 'BRUNO', 'Bruno es un perro que estuvo en un hogar temporal donde demostró ser muy juguetón y amigable con los niños.', false, 'images/mascotas/perro3.jpg', 'sano y vacunado', 'social', 2, 'PERRO', 'MACHO', 'CHICO', 'ALTO', 'Aprobada',1),
    (null, 'SIRA', 'Sira es una perra independiente que vivía en la calle, pero se adaptó rápido a la vida en hogares y busca un espacio tranquilo.', false, 'images/mascotas/perro2.jpg', 'salud perfecta', 'calma', 4, 'PERRO', 'HEMBRA', 'GRANDE', 'BAJO', 'Aprobada',1),
    (null, 'LORENZO', 'Lorenzo es un perro enérgico que disfruta correr y jugar al aire libre.', false, 'images/mascotas/perro9.jpg', 'salud buena, vacunado', 'activo', 3, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1),
    (null, 'LINA', 'Lina es una gata tranquila que disfruta de espacios soleados y mucha atención.', false, 'images/mascotas/gato5.jfif', 'sana, esterilizada', 'calma', 4, 'GATO', 'HEMBRA', 'CHICO', 'BAJO', 'Aprobada',1),
    (null, 'ROCO', 'Roco fue víctima de maltrato en su juventud, lo que lo volvió reservado pero muy fiel a quien le brinda cariño. Necesita paciencia y amor.', false, 'images/mascotas/perro10.jfif', 'salud estable', 'activo', 2, 'PERRO', 'MACHO', 'MEDIANO', 'MEDIO', 'Aprobada',1),
    (null, 'LINCE', 'Lince es un gato juguetón y curioso que se adapta fácilmente a nuevos entornos.', false, 'images/mascotas/gato6.webp', 'buena salud', 'social', 2, 'GATO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1),
    (null, 'ALFONSO', 'Alfonso es un perro mayor, tranquilo y cariñoso que disfruta de los paseos lentos y la compañía familiar.', false, 'images/mascotas/perro14.jpg', 'salud estable para su edad', 'calma', 10, 'PERRO', 'MACHO', 'GRANDE', 'BAJO', 'Aprobada',1),
    (null, 'NALA', 'Nala es una gata elegante y cariñosa, perfecta para vivir en departamentos.', false, 'images/mascotas/gato7.jpg', 'sana, vacunada', 'calma', 3, 'GATO', 'HEMBRA', 'CHICO', 'MEDIO', 'Aprobada',1),
    (null, 'CHARLIE', 'Después de ser abandonado por su familia, Charlie sobrevivió gracias a la ayuda de vecinos. Ahora busca un hogar definitivo donde se sienta seguro.', false, 'images/mascotas/perro13.jpg', 'salud estable', 'activo', 6, 'PERRO', 'MACHO', 'GRANDE', 'BAJO', 'Aprobada',1),
    (null, 'PEPE', 'Pepe es un gato tranquilo y dulce, le encanta estar cerca de las personas.', false, 'images/mascotas/gato8.jpg', 'buena salud', 'calma', 5, 'GATO', 'MACHO', 'MEDIANO', 'BAJO', 'Aprobada',1),
    (null, 'TOMMY', 'Tommy es un perro leal y protector, ideal para familias activas.', false, 'images/mascotas/perro12.jpeg', 'sano y vacunado', 'activo', 3, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1),
    (null, 'ZOE', 'Zoe fue abandonada cuando estaba embarazada. Hoy es una gata tranquila que busca un hogar seguro y lleno de cariño.', false, 'images/mascotas/gato9.jpg', 'sana, esterilizada', 'social', 2, 'GATO', 'HEMBRA', 'CHICO', 'BAJO', 'Aprobada',1),
    (null, 'LUCAS', 'Lucas es un perro cariñoso y tranquilo que busca una familia que le dé mucho amor y paseos tranquilos.', false, 'images/mascotas/perro11.png', 'salud buena, vacunado', 'calma', 7, 'PERRO', 'MACHO', 'CHICO', 'BAJO', 'Aprobada',1),
    (null, 'MIMI', 'Mimi es una perra juguetona y sociable, ideal para hogares con niños o con otras mascotas.', false, 'images/mascotas/perro15.jpg', 'sana, todas las vacunas', 'social', 3, 'PERRO', 'HEMBRA', 'GRANDE', 'MEDIO', 'Aprobada',1),
    (null, 'COCO', 'Coco fue rescatado de una situación de abandono y necesita una familia activa que pueda brindarle mucho tiempo y cariño.', false, 'images/mascotas/perro1.jpg', 'sano, desparasitado', 'activo', 6, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1);

INSERT INTO SolicitudAdopcion
(id, nombre, email, tipoVivienda, espacioDisponible, otrosAnimales, experiencia,fk_mascota, mascotaId, estado)
VALUES
    (null, 'Juan Pérez', 'juan@hotmail.com', 'Casa', 'Patio grande', 1, '5 años con perros grandes', 1, 1,'Pendiente');
