package com.jorge.aneury.proyecto_final_QA.entidades;

import jakarta.persistence.*;

@Entity
public class HistorialMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovimiento;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Producto producto;
    private String tipo;
    private int cantidad;
    private String fecha;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    public HistorialMovimiento() {

   }

    public HistorialMovimiento(Producto producto, String tipo, int cantidad, String fecha, Usuario usuario) {
        this.producto = producto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Producto getIdProducto() {
        return producto;
    }

    public void setIdProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
