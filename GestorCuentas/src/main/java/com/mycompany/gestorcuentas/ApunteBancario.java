package com.mycompany.gestorcuentas;

import java.io.Serializable;

public class ApunteBancario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String fecha;
    private String concepto;
    private String valor;
    private double importe;
    private double saldo;

    // Constructor
    public ApunteBancario(int id, String fecha, String concepto, String valor, double importe, double saldo) {
        this.id = id;
        this.fecha = fecha;
        this.concepto = concepto;
        this.valor = valor;
        this.importe = importe;
        this.saldo = saldo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    // Mostrar datos
    @Override
    public String toString() {
        return "ID: " + id + " | Fecha: " + fecha + " | Concepto: " + concepto + 
               " | Valor: " + valor + " | Importe: " + importe + " € | Saldo: " + saldo + " €";
    }
}