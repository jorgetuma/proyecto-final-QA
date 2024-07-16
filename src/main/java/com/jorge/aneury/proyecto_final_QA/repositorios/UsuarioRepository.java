package com.jorge.aneury.proyecto_final_QA.repositorios;

import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUserName(String userName);
}
