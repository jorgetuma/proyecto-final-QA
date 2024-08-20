package com.jorge.aneury.proyecto_final_QA.repositorios;

import com.jorge.aneury.proyecto_final_QA.entidades.HistorialMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMovimientoRepository extends JpaRepository<HistorialMovimiento,Integer> {
    HistorialMovimiento findByIdMovimiento(int id);
}
