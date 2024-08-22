package com.jorge.aneury.proyecto_final_QA;

import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<String> roles = new ArrayList<>();
        roles.add("admin");

        usuario.setId(1);
        usuario.setUserName("Prueba");
        usuario.setPassword("Prueba");
        usuario.setRoles(roles);

        assertEquals(1, usuario.getId());
        assertEquals("Prueba", usuario.getUserName());
        assertEquals("Prueba", usuario.getPassword());
        assertEquals("admin", usuario.getRoles().get(0));
    }
}
