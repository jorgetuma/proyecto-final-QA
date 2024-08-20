package com.jorge.aneury.proyecto_final_QA.servicios;

import com.jorge.aneury.proyecto_final_QA.entidades.HistorialMovimiento;
import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.HistorialMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class HistorialMovimientoService {

    private final HistorialMovimientoRepository historialMovimientoRepository;

    @Autowired
    public HistorialMovimientoService(HistorialMovimientoRepository historialMovimientoRepository) {
        this.historialMovimientoRepository = historialMovimientoRepository;
    }

    public List<HistorialMovimiento> listar() {
        return historialMovimientoRepository.findAll();
    }

    public HistorialMovimiento buscar(int id) {return historialMovimientoRepository.findById(id);}

    public void registrarIncremento(Producto producto, Usuario usuario) {
        HistorialMovimiento historialMovimiento = new HistorialMovimiento(producto,"Incremento de stock",producto.getCantidad(),new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()),usuario);
        historialMovimientoRepository.save(historialMovimiento);
    }

    public void registrarDecremento(Producto producto,Usuario usuario) {
        HistorialMovimiento historialMovimiento = new HistorialMovimiento(producto,"Decremento de stock",producto.getCantidad(),new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()),usuario);
        historialMovimientoRepository.save(historialMovimiento);
    }
}
