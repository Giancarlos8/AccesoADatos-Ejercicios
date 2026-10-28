package com.mycompany.gestorproductosra1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorProductosRA1 {

    private static final String DIR_DATOS = "datos";
    private static final String FICHERO_CSV = "datos/productos.csv";
    private static final String FICHERO_TSV = "datos/productos.tsv";
    private static final String DIR_BACKUPS = "backups";

    public static void main(String[] args) {
        // iniciamos ficheros al arrancar
        inicializarEntorno();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n GESTOR DE PRODUCTOS RA1 ");
            System.out.println("1. Listar productos");
            System.out.println("2. Añadir producto");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Convertir entre formatos (CSV <-> TSV)");
            System.out.println("7. Crear copia de seguridad (Backup)");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        añadirProducto(scanner);
                        break;
                    case 3:
                        buscarProductoUI(scanner);
                        break;
                    case 4:
                        modificarProducto(scanner);
                        break;
                    case 5:
                        eliminarProducto(scanner);
                        break;
                    case 6:
                        convertirFormatos(scanner);
                        break;
                    case 7:
                        crearCopiaSeguridad();
                        break;
                    case 8:
                        System.out.println("Fin del programa");
                        break;
                    default:
                        System.out.println("Opcion incorrecta, introdusca un número entre 1 y 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
            }

        } while (opcion != 8);

        scanner.close();
    }

    public static void inicializarEntorno() {
        try {
            Path dirDatosPath = Paths.get(DIR_DATOS);
            Path ficheroCsvPath = Paths.get(FICHERO_CSV);
            Path dirBackupsPath = Paths.get(DIR_BACKUPS);

            if (!Files.exists(dirDatosPath)) {
                Files.createDirectories(dirDatosPath);
            }
            if (!Files.exists(dirBackupsPath)) {
                Files.createDirectories(dirBackupsPath);
            }
            if (!Files.exists(ficheroCsvPath)) {
                try (BufferedWriter bw = Files.newBufferedWriter(ficheroCsvPath, StandardCharsets.UTF_8)) {
                    bw.write("ID;NOMBRE;PRECIO");
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error crítico al inicializar el entorno: " + e.getMessage());
        }
    }

    public static void listarProductos() {
        Path ruta = Paths.get(FICHERO_CSV);
        System.out.println("\n <LISTADO DE PRODUCTOS> ");

        try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            boolean esCabecera = true;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                if (esCabecera) {
                    esCabecera = false;
                    continue;
                }
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";", -1);
                if (partes.length >= 3) {
                    System.out.println("ID: " + partes[0] + " | Nombre: " + partes[1] + " | Precio: " + partes[2] + " €");
                    contador++;
                }
            }

            if (contador == 0) {
                System.out.println("En este momento no hay productos registrados.");
            } else {
                System.out.println("Total de productos: " + contador);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public static void añadirProducto(Scanner scanner) {
        System.out.println(" <AÑADIR NUEVO PRODUCTO> ");
        try {
            System.out.print("Introduce el ID (entero positivo): ");
            int id = Integer.parseInt(scanner.nextLine());

            if (existeId(id)) {
                System.out.println("Error: Ya existe un producto con el ID " + id + ".");
                return;
            }

            System.out.print("Introduce el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Introduce el precio: ");
            double precio = Double.parseDouble(scanner.nextLine());

            Producto nuevoProducto = new Producto(id, nombre, precio);

            Path ruta = Paths.get(FICHERO_CSV);
            try (BufferedWriter bw = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8, 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                bw.write(nuevoProducto.toString());
                bw.newLine();
                System.out.println(" Producto fue añadido correctamente");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Introduce números válidos.");
        } catch (ProductoInvalidoException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }

    public static Producto buscarProductoPorId(int idBuscado) {
        Path ruta = Paths.get(FICHERO_CSV);
        try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            boolean esCabecera = true;

            while ((linea = br.readLine()) != null) {
                if (esCabecera) {
                    esCabecera = false;
                    continue;
                }
                String[] partes = linea.split(";", -1);
                if (partes.length >= 3 && !partes[0].isEmpty()) {
                    int id = Integer.parseInt(partes[0]);
                    if (id == idBuscado) {
                        double precio = Double.parseDouble(partes[2]);
                        return new Producto(id, partes[1], precio);
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void buscarProductoUI(Scanner scanner) {
        System.out.println("\n <BUSCAR PRODUCTO POR ID>");
        try {
            System.out.print("Introduce el ID que quiere buscar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Producto p = buscarProductoPorId(id);
            if (p != null) {
                System.out.println("El producto fue encontrado");
                System.out.println(" -> ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() + " €");
            } else {
                System.out.println("El producto con el ID: " + id + ", no fue encontrado");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Introduce un ID numérico válido.");
        }
    }

    public static void modificarProducto(Scanner scanner) {
        System.out.println("\n <MODIFICAR PRODUCTO> ");
        try {
            System.out.print("Introduce el ID del producto que deseas modificar: ");
            int idBuscado = Integer.parseInt(scanner.nextLine());

            Path ruta = Paths.get(FICHERO_CSV);
            java.util.List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            boolean encontrado = false;

            java.util.List<String> nuevasLineas = new java.util.ArrayList<>();
            if (!lineas.isEmpty()) {
                nuevasLineas.add(lineas.get(0)); // Mantener cabecera
            }

            for (int i = 1; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";", -1);
                int idActual = Integer.parseInt(partes[0]);

                if (idActual == idBuscado) {
                    encontrado = true;
                    System.out.println("Producto actual encontrado: " + partes[1] + " (" + partes[2] + " €)");
                    System.out.print("Introduce el nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Introduce el nuevo precio: ");
                    double nuevoPrecio = Double.parseDouble(scanner.nextLine());

                    Producto pMod = new Producto(idBuscado, nuevoNombre, nuevoPrecio);
                    nuevasLineas.add(pMod.toString());
                    System.out.println("El producto fue modificado.");
                } else {
                    nuevasLineas.add(linea);
                }
            }

            if (encontrado) {
                Files.write(ruta, nuevasLineas, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Fichero actualizado con éxito.");
            } else {
                System.out.println("No existe ningún producto con el ID " + idBuscado + ".");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato en los datos introducidos.");
        } catch (ProductoInvalidoException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al modificar el fichero: " + e.getMessage());
        }
    }

    public static void eliminarProducto(Scanner scanner) {
        System.out.println("\n <ELIMINAR PRODUCTO> ");
        try {
            System.out.print("Introduce el ID del producto que deseas eliminar: ");
            int idBuscado = Integer.parseInt(scanner.nextLine());

            Path ruta = Paths.get(FICHERO_CSV);
            java.util.List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            boolean encontrado = false;

            java.util.List<String> nuevasLineas = new java.util.ArrayList<>();
            if (!lineas.isEmpty()) {
                nuevasLineas.add(lineas.get(0)); // Mantener cabecera
            }

            for (int i = 1; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";", -1);
                int idActual = Integer.parseInt(partes[0]);

                if (idActual == idBuscado) {
                    encontrado = true;
                } else {
                    nuevasLineas.add(linea);
                }
            }

            if (encontrado) {
                Files.write(ruta, nuevasLineas, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("¡Producto con ID " + idBuscado + " eliminado con éxito!");
            } else {
                System.out.println("No se encontró ningún producto con el ID " + idBuscado + ".");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Introduce un ID numérico válido.");
        } catch (IOException e) {
            System.err.println("Error de E/S al procesar el fichero: " + e.getMessage());
        }
    }

    public static void convertirFormatos(Scanner scanner) {
        System.out.println("\n <CONVERSIÓN DE FORMATOS> ");
        System.out.println("1. De CSV (;) a TSV (\\t)");
        System.out.println("2. De TSV (\\t) a CSV (;)");
        System.out.print("Elige una opción: ");
        try {
            int op = Integer.parseInt(scanner.nextLine());
            Path origen, destino;

            if (op == 1) {
                origen = Paths.get(FICHERO_CSV);
                destino = Paths.get(FICHERO_TSV);
            } else if (op == 2) {
                origen = Paths.get(FICHERO_TSV);
                destino = Paths.get(FICHERO_CSV);
            } else {
                System.out.println("Opción invalida.");
                return;
            }

            if (!Files.exists(origen)) {
                System.out.println("El fichero no existe (" + origen.getFileName() + ").");
                return;
            }

            java.util.List<String> lineas = Files.readAllLines(origen, StandardCharsets.UTF_8);
            java.util.List<String> lineasConvertidas = new java.util.ArrayList<>();

            for (String linea : lineas) {
                if (op == 1) {
                    lineasConvertidas.add(linea.replace(";", "\t"));
                } else {
                    lineasConvertidas.add(linea.replace("\t", ";"));
                }
            }

            Files.write(destino, lineasConvertidas, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("¡Conversión realizada con éxito! Fichero generado: " + destino);

        } catch (Exception e) {
            System.out.println("Error durante la conversión: " + e.getMessage());
        }
    }

    public static void crearCopiaSeguridad() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path origen = Paths.get(FICHERO_CSV);
            Path destino = Paths.get(DIR_BACKUPS + "/productos_" + timestamp + ".csv");

            if (!Files.exists(origen)) {
                System.out.println("No existe el fichero de productos para copiar.");
                return;
            }

            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("¡Copia de seguridad creada con éxito en: " + destino + "!");

        } catch (IOException e) {
            System.err.println("Error al crear la copia de seguridad: " + e.getMessage());
        }
    }

    public static boolean existeId(int idBuscado) {
        return buscarProductoPorId(idBuscado) != null;
    }
}