package com.jorge.aneury.proyecto_final_QA.controladores;

import com.jorge.aneury.proyecto_final_QA.entidades.Producto;
import com.jorge.aneury.proyecto_final_QA.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.findAllByStatus(false);
        model.addAttribute("productos", productos);
        return "productos";
    }

    @PostMapping("/crear-prod")
    public String crearProducto(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam String categoria, @RequestParam float precio, @RequestParam int cantidad, @RequestParam int cantidadMinima) {
        Producto producto = new Producto(nombre, descripcion, categoria, precio, cantidad, cantidadMinima);
        productoService.insertar(producto);
        return "redirect:/";
    }

    @PostMapping("/modificar-prod/{id}")
    public String modificarProducto(@PathVariable int id, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam String categoria, @RequestParam float precio, @RequestParam int cantidad, @RequestParam int cantidadMinima) {
        productoService.actualizar(id, nombre, descripcion, categoria, precio, cantidad, cantidadMinima);
        return "redirect:/";
    }

    @RequestMapping("/eliminar-prod/{id}")
    public String eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
        return "redirect:/";
    }
}
