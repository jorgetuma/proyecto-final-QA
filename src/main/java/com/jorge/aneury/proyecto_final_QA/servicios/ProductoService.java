package com.jorge.aneury.proyecto_final_QA.servicios;

import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.repositorios.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAllByStatus(boolean status) {
        return productoRepository.findAllByEliminado(status);
    }

    public Producto findById(int id) {
        return productoRepository.findProductoById(id);
    }

    public void insertar(Producto producto) {
         productoRepository.save(producto);
    }

    @Transactional
    public void actualizar(int id,String nombre, String descripcion, String categoria ,float precio,int cantidad,int cantidadMinima) {
    Producto producto = productoRepository.findProductoById(id);
    producto.setNombre(nombre);
    producto.setDescripcion(descripcion);
    producto.setCategoria(categoria);
    producto.setPrecio(precio);
    producto.setCantidad(cantidad);
    producto.setCantidadMinima(cantidadMinima);
    }

    @Transactional
    public void eliminar(int id) {
        Producto p = productoRepository.findProductoById(id);
        p.setEliminado(true);
    }

    @Transactional
    public void incrementarStock(int id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser positiva");
        }
        Producto p = productoRepository.findProductoById(id);
        p.setCantidad(p.getCantidad() + cantidad);
    }

    @Transactional
    public void decrementarStock(int id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a decrementar debe ser positiva");
        }
        Producto p = productoRepository.findProductoById(id);
        if (p.getCantidad() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock para decrementar");
        }
        p.setCantidad(p.getCantidad() - cantidad);
    }
}
