package com.mycompany.ejercicio2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Ejercicio2 {

    public static void main(String[] args) {
        String datos = "id,nombre,nota\n"
                     + "1,Ana Lopez,8.5\n"
                     + "2,Carlos Gomez,6.0\n"
                     + "3,Maria Perez,9.2\n";

        try (FileWriter escritor = new FileWriter("alumnos_io.csv")) {
            escritor.write(datos);
            System.out.println("Archivo con java.io creado.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.writeString(Paths.get("alumnos_nio.csv"), datos);
            System.out.println("Archivo con java.nio creado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}