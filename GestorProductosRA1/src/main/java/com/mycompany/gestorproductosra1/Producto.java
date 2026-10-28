package com.mycompany.gestorproductosra1;

public class Producto {
    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) throws ProductoInvalidoException {
        if (id <= 0) {
            throw new ProductoInvalidoException("El ID debe ser mayor que 0.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ProductoInvalidoException("El nombre del producto no puede estar vacío.");
        }
        if (precio < 0) {
            throw new ProductoInvalidoException("El precio no puede ser negativo.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return id + ";" + nombre + ";" + precio;
    }
}