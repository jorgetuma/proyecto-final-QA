package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    /*Probando creación de un nuevo producto*/
    @Test
    public void testNuevoProducto() {
        Producto producto = new Producto("Manzana","Prueba","Frutas", (float)10.0,1,1);
        assertEquals("Manzana", producto.getNombre());
        assertEquals("Prueba", producto.getDescripcion());
        assertEquals("Frutas", producto.getCategoria());
        assertEquals((float)10.0, producto.getPrecio(),0.1);
        assertEquals(1, producto.getCantidad());
        assertEquals(1, producto.getCantidadMinima());
        assertEquals(false, producto.isEliminado());
    }

    /*Probando los setters de cada atributo*/
    @Test
    public void  testNuevoProducto2() {
        Producto producto = new Producto();

        producto.setId(1);
        producto.setNombre("Manzana");
        producto.setDescripcion("Prueba");
        producto.setCategoria("Frutas");
        producto.setPrecio((float)10.0);
        producto.setCantidad(1);
        producto.setCantidadMinima(1);
        producto.setEliminado(false);

        assertEquals(1, producto.getId());
        assertEquals("Manzana", producto.getNombre());
        assertEquals("Prueba", producto.getDescripcion());
        assertEquals("Frutas", producto.getCategoria());
        assertEquals((float)10.0, producto.getPrecio(),0.1);
        assertEquals(1, producto.getCantidad());
        assertEquals(1, producto.getCantidadMinima());
        assertEquals(false, producto.isEliminado());
    }

    /*Probando actualización de precio*/
    @Test
    public void testActualizarPrecio() {
        Producto producto = new Producto("Banana", "Fruta tropical", "Frutas", (float)8.0, 10, 5);
        producto.setPrecio((float)9.5);
        assertEquals((float)9.5, producto.getPrecio(), 0.1);
    }

    /*Probando eliminación lógica*/
    @Test
    public void testEliminacionLogica() {
        Producto producto = new Producto("Uva", "Fruta pequeña", "Frutas", (float)15.0, 20, 10);
        assertFalse(producto.isEliminado());
        producto.setEliminado(true);
        assertTrue(producto.isEliminado());
    }

    /*Probando precio negativo*/
    @Test
    public void testNegativePrecio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("Test", "Description", "Category", -10.0f, 10, 1);
        });

        Producto producto = new Producto();
        assertThrows(IllegalArgumentException.class, () -> {
            producto.setPrecio(-5.0f);
        });
    }

    /*Probando valores negativos en cantidad y cantidadMinima*/
    @Test
    public void testNegativeCantidad() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("Test", "Description", "Category", 10.0f, -5, 1);
        });

        Producto producto = new Producto();
        assertThrows(IllegalArgumentException.class, () -> {
            producto.setCantidad(-10);
        });
    }

    /*Probando valores negativos en cantidad y cantidadMinima*/
    @Test
    public void testNegativeCantidadMinima() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("Test", "Description", "Category", 10.0f, 10, -3);
        });

        Producto producto = new Producto();
        assertThrows(IllegalArgumentException.class, () -> {
            producto.setCantidadMinima(-5);
        });
    }
}
