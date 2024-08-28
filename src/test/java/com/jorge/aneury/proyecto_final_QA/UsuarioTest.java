package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.Role;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UsuarioTest {

    @Test
    public void creandoUsuarioTest() {
        Usuario usuario = new Usuario("Prueba","Prueba");
        assertEquals("Prueba", usuario.getUserName());
        assertEquals("Prueba", usuario.getPassword());
    }

    @Test
    public void creandoUsuario2Test() {
        Usuario usuario = new Usuario();

        usuario.setId(1);
        usuario.setUserName("Prueba");
        usuario.setPassword("Prueba");
        usuario.setRole(Role.ROLE_ADMIN);

        assertEquals(1, usuario.getId());
        assertEquals("Prueba", usuario.getUserName());
        assertEquals("Prueba", usuario.getPassword());
        assertEquals(Role.ROLE_ADMIN, usuario.getRole());
    }

    @Test
    public void testSetAndGetRole() {
        Usuario usuario = new Usuario();
        usuario.setRole(Role.ROLE_ADMIN);
        assertEquals(Role.ROLE_ADMIN, usuario.getRole());
    }

    @Test
    public void testSetAndGetActive() {
        Usuario usuario = new Usuario();
        usuario.setActive(true);
        assertTrue(usuario.isActive());
        usuario.setActive(false);
        assertFalse(usuario.isActive());
    }

    @Test
    public void testConstructorWithNullValues() {
        Usuario usuario = new Usuario(null, null);
        assertNull(usuario.getUserName());
        assertNull(usuario.getPassword());
    }

}
