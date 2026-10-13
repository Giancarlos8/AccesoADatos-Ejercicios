package com.mycompany.ejercicio2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Ejercicio2 {

    public static void main(String[] args) {
        String encabezado = "id,nombre,nota";
        List<String> filas = Arrays.asList(
            "1,Ana Lopez,8.5",
            "2,Carlos Gomez,6.0",
            "3,Maria Perez,9.2"
        );

        escribirConJavaIO("alumnos_io.csv", encabezado, filas);

        escribirConJavaNIO("alumnos_nio.csv", encabezado, filas);
    }

    public static void escribirConJavaIO(String nombreFichero, String encabezado, List<String> filas) {
        File archivo = new File(nombreFichero);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(encabezado);
            bw.newLine();

            for (String fila : filas) {
                bw.write(fila);
                bw.newLine();
            }

            System.out.println("[java.io] Archivo creado correctamente: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[java.io] Error al escribir el fichero: " + e.getMessage());
        }
    }

    public static void escribirConJavaNIO(String nombreFichero, String encabezado, List<String> filas) {
        Path ruta = Paths.get(nombreFichero);

        try {
            List<String> contenido = new java.util.ArrayList<>();
            contenido.add(encabezado);
            contenido.addAll(filas);

            Files.write(ruta, contenido, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("[java.nio] Archivo creado correctamente: " + ruta.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[java.nio] Error al escribir el fichero: " + e.getMessage());
        }
    }
}