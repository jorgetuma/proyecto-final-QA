package com.jorge.aneury.proyecto_final_QA.repositorios;

import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Integer> {
    List<Producto> findAllByEliminado(boolean eliminado);
    Producto findProductoById(int id);
}
