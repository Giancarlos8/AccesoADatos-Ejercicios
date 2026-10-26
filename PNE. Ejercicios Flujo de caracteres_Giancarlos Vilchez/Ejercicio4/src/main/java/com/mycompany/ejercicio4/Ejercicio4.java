package com.mycompany.ejercicio4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio4 {

    public static void main(String[] args) {
        //Definimos la ruta del fichero
        Path rutaFichero = Paths.get("file.txt");
        long totalPalabras = 0;
        
        //Usamos try-with-resources, Files.newBufferedReader y UTF-8. Para garantizar la correcta lectura del fichero.
        try (BufferedReader br = Files.newBufferedReader(rutaFichero, StandardCharsets.UTF_8)) 
        {
            String linea;
                    
            //Leemos el fichero linea a linea.
            while((linea = br.readLine()) != null){
            if(!linea.trim().isEmpty()){
                //Dividimos por espacios en blanco.
                String[] palabras = linea.trim().split("\\s+");
                totalPalabras += palabras.length;
            }
        }
            System.out.print("El Fichero \"" + rutaFichero.getFileName() + "\" tiene un totoal de " + totalPalabras + " palabras." );        
        }catch (IOException e){
            System.err.print("Error al leer el fichero: " + e.getMessage());
        }
        
    }
}
