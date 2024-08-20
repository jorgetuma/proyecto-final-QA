package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.dto.AuthRequest;
import com.jorge.aneury.proyecto_final_QA.dto.AuthResponse;
import com.jorge.aneury.proyecto_final_QA.dto.UsuarioDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.UsuarioRepository;
import com.jorge.aneury.proyecto_final_QA.servicios.JwtService;
import com.jorge.aneury.proyecto_final_QA.servicios.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private UsuarioService usuarioService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsuarioService usuarioService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {

        Usuario usuario = usuarioService.findByUsuario(authRequest.username());
        if (usuario == null || !passwordEncoder.matches(authRequest.password(), usuario.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthResponse token = jwtService.generateToken(authRequest.username());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = usuarioService.findByUsuario(usuarioDto.getUsername());
        if (usuario != null){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuarioService.save(usuarioDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
}
