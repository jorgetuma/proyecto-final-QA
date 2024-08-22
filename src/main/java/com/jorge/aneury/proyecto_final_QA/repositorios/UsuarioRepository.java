package com.jorge.aneury.proyecto_final_QA.repositorios;

import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUserName(String userName);
    List<Usuario> findAllByActiveTrue();
}
