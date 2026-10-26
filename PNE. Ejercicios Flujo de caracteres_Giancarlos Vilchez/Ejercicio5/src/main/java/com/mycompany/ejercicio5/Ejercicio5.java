package com.mycompany.ejercicio5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ejercicio5 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        //ruta del fichero.
        System.out.println("Escribe el nombre de un fichero: file.txt o students.txt");
        String nombreFichero = scanner.nextLine();
        
        System.out.println("Introduce la cadena de texto que deseas buscar: ");
        String busqueda = scanner.nextLine();
        
        Path rutaFichero = Paths.get(nombreFichero);
        int numeroLinea = 0;
        int totalCoincidencias = 0;
        
        //Usamos try-with-resources, Files.newBufferedReader y UTF-8. Para garantizar la correcta lectura del fichero.
        try (BufferedReader br = Files.newBufferedReader(rutaFichero, StandardCharsets.UTF_8)) 
        {
            String linea;
            
            System.out.println("Resultado de la busqueda: ");
            
            //Recorrido del fichero linea a linea.
            while ((linea = br.readLine()) != null){
                numeroLinea++;
                
                //Verificamos si la linea actual contiene la cadena de texto.
                if (linea.contains(busqueda)){
                    System.out.println("Linea "+ numeroLinea + ":" + linea);
                    totalCoincidencias++;
                }
            }
            System.out.println("Se encontraron "+ totalCoincidencias + " coincidencias.");
            
            } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
