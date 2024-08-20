package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.HistorialMovimiento;
import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistorialMovimientoTest {

    @Test
    public void HistorialMovimientoTest1() {
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        HistorialMovimiento hm = new HistorialMovimiento(new Producto(),"Incremento de Stock",10,fecha,new Usuario());
        hm.setIdMovimiento(1);

        assertEquals(1, hm.getIdMovimiento());
        assertEquals("Incremento de Stock", hm.getTipo());
        assertEquals(10, hm.getCantidad());
        assertEquals(fecha, hm.getFecha());
        assertNotEquals(null,hm.getUsuario());
        assertNotEquals(null, hm.getProducto());
    }

    @Test
    public void HistorialMovimientoTest2() {
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        HistorialMovimiento hm = new HistorialMovimiento();

        hm.setIdMovimiento(1);
        hm.setTipo("Incremento de Stock");
        hm.setCantidad(10);
        hm.setFecha(fecha);
        hm.setUsuario(new Usuario());
        hm.setProducto(new Producto());

        assertEquals(1, hm.getIdMovimiento());
        assertEquals("Incremento de Stock", hm.getTipo());
        assertEquals(10, hm.getCantidad());
        assertEquals(fecha, hm.getFecha());
        assertNotEquals(null,hm.getUsuario());
        assertNotEquals(null, hm.getProducto());
    }
}
