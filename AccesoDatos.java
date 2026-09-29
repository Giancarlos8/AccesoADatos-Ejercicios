package com.mycompany.accesodatos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AccesoDatos {

    public static void main(String[] args) {
        Path baseDir = Paths.get(".");

        String[] carpetas = {
            "src",
            "bin",
            "img",
            "src/junit"
        };

        String[] ficheros = {
            "readme.md",
            "src/run.java",
            "img/imagen.png"
        };

        System.out.println("CREACIÓN DE DIRECTORIOS");
        for (String carpeta : carpetas) {
            Path rutaCarpeta = baseDir.resolve(carpeta);
            try {
                if (!Files.exists(rutaCarpeta)) {
                    Files.createDirectories(rutaCarpeta);
                    System.out.println("Carpeta creada: " + rutaCarpeta);
                } else {
                    System.out.println("La carpeta ya existe: " + rutaCarpeta);
                }
            } catch (IOException e) {
                System.err.println("Error al crear carpeta " + rutaCarpeta + ": " + e.getMessage());
            }
        }

        System.out.println("\nCREACIÓN DE FICHEROS");
        for (String fichero : ficheros) {
            Path rutaFichero = baseDir.resolve(fichero);
            try {
                if (!Files.exists(rutaFichero)) {
                    if (rutaFichero.getParent() != null && !Files.exists(rutaFichero.getParent())) {
                        Files.createDirectories(rutaFichero.getParent());
                    }
                    Files.createFile(rutaFichero);
                    System.out.println("Fichero creado: " + rutaFichero);
                } else {
                    System.out.println("El fichero ya existe: " + rutaFichero);
                }
            } catch (IOException e) {
                System.err.println("Error al crear fichero " + rutaFichero + ": " + e.getMessage());
            }
        }

        System.out.println("\nESTRUCTURA DEL PROYECTO (DIRECTORIOS Y SUBDIRECTORIOS)");
        try (Stream<Path> stream = Files.walk(baseDir)) {
            stream.forEach(path -> {
                String tipo = Files.isDirectory(path) ? "[DIR] " : "[FILE]";
                System.out.println(tipo + " " + path.normalize());
            });
        } catch (IOException e) {
            System.err.println("Error al listar directorios: " + e.getMessage());
        }
    }
}
