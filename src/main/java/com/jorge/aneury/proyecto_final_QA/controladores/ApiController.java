package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.dto.ProductoDto;
import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.repositorios.ProductoRepository;
import com.jorge.aneury.proyecto_final_QA.servicios.HistorialMovimientoService;
import com.jorge.aneury.proyecto_final_QA.servicios.ProductoService;
import com.jorge.aneury.proyecto_final_QA.servicios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private final ProductoRepository productoRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final HistorialMovimientoService historialMovimientoService;

    @Autowired
    public ApiController(ProductoRepository productoRepository, ProductoService productoService,
                         UsuarioService usuarioService, HistorialMovimientoService historialMovimientoService) {
        this.productoRepository = productoRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.historialMovimientoService = historialMovimientoService;
    }

    @GetMapping("/productos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/productos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearProducto(@RequestBody @Valid Producto producto) {
        try {
            Producto nuevoProducto = productoRepository.save(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/productos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> actualizarProducto(@RequestBody ProductoDto productoDto) {
        Optional<Producto> optionalProducto = productoRepository.findById(productoDto.id());
        if (optionalProducto.isPresent()) {
            Producto productoExistente = optionalProducto.get();
            try {
                productoExistente.setNombre(productoDto.nombre());
                productoExistente.setDescripcion(productoDto.descripcion());
                productoExistente.setCategoria(productoDto.categoria());
                productoExistente.setPrecio(productoDto.precio());
                productoExistente.setCantidad(productoDto.cantidad());
                productoExistente.setCantidadMinima(productoDto.cantidadMinima());
                Producto actualizadoProducto = productoRepository.save(productoExistente);
                return new ResponseEntity<>(actualizadoProducto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
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

    @PostMapping("/productos/{id}/incrementar-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> incrementarStock(@PathVariable int id, @RequestParam int cantidad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsuario(username);
        Producto producto = productoService.findById(id);
        if (producto != null) {
            try {
                productoService.incrementarStock(id, cantidad);
                historialMovimientoService.registrarIncremento(producto, usuario, cantidad);
                return new ResponseEntity<>(producto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/productos/{id}/decrementar-stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> decrementarStock(@PathVariable int id, @RequestParam int cantidad) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsuario(username);
        Producto producto = productoService.findById(id);
        if (producto != null) {
            try {
                if (producto.getCantidad() >= cantidad) {
                    productoService.decrementarStock(id, cantidad);
                    historialMovimientoService.registrarDecremento(producto, usuario, cantidad);
                    return new ResponseEntity<>(producto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No hay suficiente stock para decrementar", HttpStatus.BAD_REQUEST);
                }
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
