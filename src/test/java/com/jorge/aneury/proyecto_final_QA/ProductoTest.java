package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
