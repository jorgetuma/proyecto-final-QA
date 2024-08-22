package com.jorge.aneury.proyecto_final_QA.servicios;

import com.jorge.aneury.proyecto_final_QA.dto.UpdateUserDto;
import com.jorge.aneury.proyecto_final_QA.dto.UsuarioDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Role;
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

    public Optional<Usuario> findById(int id){
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAllByActiveTrue();
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
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
//        for (String role : user.getRoles()) {
//            logger.info("Role: "+role);
//            roles.add(new SimpleGrantedAuthority(role));
//        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public Usuario save(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setUserName(usuarioDto.getUsername());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setRole(Role.valueOf(usuarioDto.getRole()));
        return usuarioRepository.save(usuario);
    }

    public Usuario update(int id, UpdateUserDto usuarioDto) {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("Usuario no existe");
        }
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setUserName(usuarioDto.getUsername());
        usuario.setRole(Role.valueOf(usuarioDto.getRole()));
        return usuarioRepository.save(usuario);
    }

    public void delete(int id) {
        Optional<Usuario> usuarioOptional = findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setActive(false);
            usuarioRepository.save(usuario);
        } else {
            throw new UsernameNotFoundException("Usuario no existe");
        }
    }
}