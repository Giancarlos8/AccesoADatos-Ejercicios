package com.mycompany.ejercicio6;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Ejercicio6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // ruta del fichero
        Path ruta = Paths.get("students.txt");

        System.out.println("Añadir alumno:");
        System.out.print("Introduce los datos del nuevo alumno: ");
        String nuevaLinea = scanner.nextLine();

        // abrimos el BufferedWriter
        try (BufferedWriter bw = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            
            bw.newLine();
           
            //escribimos la nueva línea con los datos del alumno
            bw.write(nuevaLinea);
            
            System.out.println("El alumno ha sido añadido" + ruta.getFileName());

        } catch (IOException e) {
            System.err.println("Error al escribir en el fichero: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}