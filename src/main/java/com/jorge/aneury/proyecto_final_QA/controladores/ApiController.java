package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.dto.ProductoDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.repositorios.ProductoRepository;
import com.jorge.aneury.proyecto_final_QA.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private ProductoRepository productoRepository;

    @Autowired
    public ApiController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @PostMapping("/productos/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Producto listarProductos(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/productos/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Producto> updateProducto(@RequestBody ProductoDto productoDto) {
        Optional<Producto> producto1 = productoRepository.findById(productoDto.id());
        if (producto1.isPresent()) {
            producto1.get().setNombre(productoDto.nombre());
            producto1.get().setDescripcion(productoDto.descripcion());
            producto1.get().setCategoria(productoDto.categoria());
            producto1.get().setPrecio(productoDto.precio());
            producto1.get().setCantidad(productoDto.cantidad());
            producto1.get().setCantidadMinima(productoDto.cantidadMinima());
            return new ResponseEntity<>(productoRepository.save(producto1.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/productos/remove/{id}")
    public String listarProductos(@PathVariable int id) {
        productoRepository.deleteById(id);
        return "Producto eliminado";
    }
}
