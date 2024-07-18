package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.dto.AuthRequest;
import com.jorge.aneury.proyecto_final_QA.dto.AuthResponse;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.UsuarioRepository;
import com.jorge.aneury.proyecto_final_QA.servicios.JwtService;
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
@RequestMapping("/auth")
public class AuthController {

    private UsuarioRepository usuarioRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsuarioRepository usuarioRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest){

        Usuario usuario = usuarioRepository.findByUserName(authRequest.username());
        if(usuario==null && !usuario.getPassword().equals(passwordEncoder.encode(authRequest.password()))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthResponse token = jwtService.generateToken(authRequest.username());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(authRequest.username()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

//    @PostMapping("/generateToken")
//    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.username());
//        } else {
//            throw new UsernameNotFoundException("Usuario invalido...");
//        }
//    }
}
