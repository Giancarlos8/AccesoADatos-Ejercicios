package com.mycompany.gestorproductosra1;

public class ProductoInvalidoException extends Exception {
    public ProductoInvalidoException(String mensaje) {
        super(mensaje);
    }
}