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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/productos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoRepository.save(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/productos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody ProductoDto productoDto) {
        Optional<Producto> optionalProducto = productoRepository.findById(productoDto.id());
        if (optionalProducto.isPresent()) {
            Producto productoExistente = optionalProducto.get();
            productoExistente.setNombre(productoDto.nombre());
            productoExistente.setDescripcion(productoDto.descripcion());
            productoExistente.setCategoria(productoDto.categoria());
            productoExistente.setPrecio(productoDto.precio());
            productoExistente.setCantidad(productoDto.cantidad());
            productoExistente.setCantidadMinima(productoDto.cantidadMinima());
            Producto actualizadoProducto = productoRepository.save(productoExistente);
            return new ResponseEntity<>(actualizadoProducto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/productos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> eliminarProducto(@PathVariable int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return new ResponseEntity<>("Producto eliminado", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
