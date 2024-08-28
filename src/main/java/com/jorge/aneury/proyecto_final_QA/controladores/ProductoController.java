package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.entidades.HistorialMovimiento;
import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.entidades.Usuario;
import com.jorge.aneury.proyecto_final_QA.servicios.HistorialMovimientoService;
import com.jorge.aneury.proyecto_final_QA.servicios.ProductoService;
import com.jorge.aneury.proyecto_final_QA.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductoController {
    private final ProductoService productoService;
    private final HistorialMovimientoService historialMovimientoService;
    private final UsuarioService usuarioService;

    @Autowired
    public ProductoController(ProductoService productoService,HistorialMovimientoService historialMovimientoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.historialMovimientoService = historialMovimientoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.findAllByStatus(false);
        model.addAttribute("productos", productos);
        return "productos";
    }

    @PostMapping("/crear-prod")
    public String crearProducto(@AuthenticationPrincipal UserDetails userDetails,@RequestParam String nombre, @RequestParam String descripcion, @RequestParam String categoria, @RequestParam float precio, @RequestParam int cantidad, @RequestParam int cantidadMinima) {
        Producto producto = new Producto(nombre, descripcion, categoria, precio, cantidad, cantidadMinima);
        Usuario usuario = usuarioService.findByUsuario(userDetails.getUsername());
        productoService.insertar(producto);
        historialMovimientoService.registrarIncremento(producto,usuario,producto.getCantidad());
        return "redirect:/";
    }

    @PostMapping("/modificar-prod/{id}")
    public String modificarProducto(@PathVariable int id, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam String categoria, @RequestParam float precio, @RequestParam int cantidad, @RequestParam int cantidadMinima) {
        productoService.actualizar(id, nombre, descripcion, categoria, precio, cantidad, cantidadMinima);
        return "redirect:/";
    }

    @RequestMapping("/eliminar-prod/{id}")
    public String eliminarProducto(@AuthenticationPrincipal UserDetails userDetails,@PathVariable int id) {
        Usuario usuario = usuarioService.findByUsuario(userDetails.getUsername());
        Producto p = productoService.findById(id);
        productoService.eliminar(id);
        historialMovimientoService.registrarDecremento(p,usuario,p.getCantidad());
        return "redirect:/";
    }

    @GetMapping("/historial-movimiento")
    public String historialMovimiento(Model model) {
        List<HistorialMovimiento> movimientos = historialMovimientoService.listar();
        model.addAttribute("movimientos", movimientos);
        return "historial-movimiento";
    }

    @PostMapping("/incrementar-stock/{id}")
    public String incrementarStock(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") int id, @RequestParam int cantidadIncrementar, Model model) {
        try {
            Usuario usuario = usuarioService.findByUsuario(userDetails.getUsername());
            Producto p = productoService.findById(id);
            productoService.incrementarStock(id, cantidadIncrementar);
            historialMovimientoService.registrarIncremento(p, usuario, cantidadIncrementar);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "productos";
        }
    }

    @PostMapping("/decrementar-stock/{id}")
    public String decrementarStock(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") int id, @RequestParam int cantidadDecrementar, Model model) {
        try {
            Usuario usuario = usuarioService.findByUsuario(userDetails.getUsername());
            Producto p = productoService.findById(id);
            productoService.decrementarStock(id, cantidadDecrementar);
            historialMovimientoService.registrarDecremento(p, usuario, cantidadDecrementar);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "productos";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<HistorialMovimiento> incrementos = historialMovimientoService.listarByTipo("Incremento de stock");
        List<HistorialMovimiento> decrementos = historialMovimientoService.listarByTipo("Decremento de stock");
        model.addAttribute("incrementos", incrementos.size());
        model.addAttribute("decrementos", decrementos.size());
        model.addAttribute("cantProductos",productoService.findAllByStatus(false).size());
        model.addAttribute("totalStock",obtenerTotalStock());
        return "dashboard";
    }

    /*Calcular el total de todas las unidades de productos en el inventario*/
    private int obtenerTotalStock() {
        int totalStock = 0;
        for (Producto p : productoService.findAllByStatus(false)) {
            totalStock += p.getCantidad();
        }
        return totalStock;
    }
}
