package com.jorge.aneury.proyecto_final_QA.servicios;

import com.jorge.aneury.proyecto_final_QA.dto.UsuarioDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    private Logger logger =  LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findByUsuario(String username){
        return usuarioRepository.findByUserName(username);
    }

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Consultado el usuario: {}", username);

        Usuario user = usuarioRepository.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("Usuario no existe");
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (String role : user.getRoles()) {
            logger.info("Role: "+role);
            roles.add(new SimpleGrantedAuthority(role));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public Usuario save(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setUserName(usuarioDto.getUsername());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setRoles(Collections.singletonList(usuarioDto.getRole()));
        return usuarioRepository.save(usuario);
    }
}