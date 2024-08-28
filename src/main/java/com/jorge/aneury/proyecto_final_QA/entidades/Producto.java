package com.jorge.aneury.proyecto_final_QA.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;
    private String categoria;
    @Min(value = 0, message = "El precio no pueder ser negativo")
    private float precio;
    @Min(value = 0, message="La cantidad no puede ser negativa")
    private int cantidad;
    @Min(value = 0, message="La cantidad mínima no puede ser negativa")
    private int CantidadMinima;
    private boolean eliminado;

    public Producto() {}

    public Producto(String nombre, String descripcion, String categoria, float precio, int cantidad,int CantidadMinima) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (CantidadMinima < 0) {
            throw new IllegalArgumentException("La cantidad mínima no puede ser negativa");
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.CantidadMinima = CantidadMinima;
        this.eliminado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }

    public int getCantidadMinima() {
        return CantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        if (cantidadMinima < 0) {
            throw new IllegalArgumentException("La cantidad mínima no puede ser negativa");
        }
        this.CantidadMinima = cantidadMinima;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
