package com.mycompany.ejercicio7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio7 {

    public static void main(String[] args) {
        // ruta de fichero
        Path origen = Paths.get("file.txt");
        // ruta donde se creara el nuevo fichero
        Path destino = Paths.get("copia_file.txt");

        System.out.println("Copiar fichero de texto");

        // usamos try-with-resources para lectura y escritura
        try (BufferedReader br = Files.newBufferedReader(origen, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(destino, StandardCharsets.UTF_8)) {

            String linea;
            long lineasCopiadas = 0;

            // leemos linea a linea
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
                lineasCopiadas++;
            }

            System.out.println("La copia fue realizada");
            System.out.println("Se han copiado " + lineasCopiadas + " líneas de \"" + origen.getFileName() + "\" a \"" + destino.getFileName() + "\".");

        } catch (IOException e) {
            System.err.println("Error durante la copia del fichero: " + e.getMessage());
        }
    }
}