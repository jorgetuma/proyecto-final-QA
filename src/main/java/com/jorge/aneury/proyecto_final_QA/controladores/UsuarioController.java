package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.dto.UpdateUserDto;
import com.jorge.aneury.proyecto_final_QA.dto.UsuarioDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Role;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private UsuarioService usuarioService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute UsuarioDto usuarioDto, Model model) {
        Usuario usuario = usuarioService.findByUsuario(usuarioDto.getUsername());
        if (usuario != null){
            model.addAttribute("error", "El nombre de usuario ya existe");
            model.addAttribute("usuarios", usuarioService.findAll());
            return "usuarios";
        }
        usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuarioService.save(usuarioDto);
        return "redirect:/usuarios";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable int id, @ModelAttribute UpdateUserDto updateUserDto, Model model) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isEmpty()) {
            model.addAttribute("error", "Usuario no encontrado");
            model.addAttribute("usuarios", usuarioService.findAll());
            return "usuarios";
        }
        
        // Check if the new username is already taken by another user
        Usuario existingUser = usuarioService.findByUsuario(updateUserDto.getUsername());
        if (existingUser != null && existingUser.getId() != id) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            model.addAttribute("usuarios", usuarioService.findAll());
            return "usuarios";
        }

        usuarioService.update(id, updateUserDto);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id, Model model) {
        try {
            usuarioService.delete(id);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

}
