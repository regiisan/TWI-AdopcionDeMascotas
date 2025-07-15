package com.tallerwebi.punta_a_punta;

import java.io.IOException;

public class ReiniciarDB {
    public static void limpiarBaseDeDatos() {
        try {
            String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
            String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
            String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "tallerwebi";
            String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "user";
            String dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "user";

            String sqlCommands = "DELETE FROM Mascota;\n" +
                    "ALTER TABLE Mascota AUTO_INCREMENT = 1;\n" +
                    "DELETE FROM Usuario;\n" +
                    "ALTER TABLE Usuario AUTO_INCREMENT = 1;\n" +
                    "INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);" +
                    "INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'regi@gmail.com', 'test', 'USUARIO', true);" +
                    "INSERT INTO Mascota (id, nombre, descripcion, adoptado, img, salud, comportamiento, edad, tipo, sexo, tamano, nivelEnergia, estado, usuario_id) " +
                    "VALUES (null, 'LUNA', 'Luna fue rescatada de las calles después de un accidente, es una perra muy cariñosa y tranquila que busca un hogar donde le den mucho amor.', false, 'images/mascotas/perro8.webp', 'sana, tiene todas las vacunas', 'social', 3, 'PERRO', 'HEMBRA', 'CHICO', 'BAJO', 'Aprobada',1), (null, 'MAX', 'Max fue encontrado en un parque mientras jugaba con niños. Es un perro protector y lleno de energía que necesita espacio para correr.', false, 'images/mascotas/perro7.jfif', 'buena salud, desparasitado', 'activo', 2, 'PERRO', 'MACHO', 'MEDIANO', 'ALTO', 'Aprobada',1);";

            String comando = String.format(
                    "docker exec tallerwebi-mysql mysql -h %s -P %s -u %s -p%s %s -e \"%s\"",
                    dbHost, dbPort, dbUser, dbPassword, dbName, sqlCommands
            );

            Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", comando});
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Base de datos limpiada exitosamente");
            } else {
                System.err.println("Error al limpiar la base de datos. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando script de limpieza: " + e.getMessage());
            e.printStackTrace();
        }
    }
}