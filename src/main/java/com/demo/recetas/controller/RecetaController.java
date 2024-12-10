
// RecetaController.java
package com.demo.recetas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecetaController {
    @GetMapping("/receta/detalle")
    public String mostrarDetalleReceta(
            @RequestParam("id") Long id, 
            Model model,
            @RequestHeader("Authorization") String authHeader) {
        
        // Aquí buscarías la receta en la base de datos por su ID
        model.addAttribute("nombre", "Paella");
        model.addAttribute("tipoCocina", "Española");
        model.addAttribute("ingredientes", "Arroz, Azafrán, Mariscos");
        model.addAttribute("dificultad", "Media");
        model.addAttribute("tiempoCoccion", 45);
        model.addAttribute("instrucciones", List.of(
            "1. Calienta el aceite en una paellera y añade los mariscos.",
            "2. Agrega el arroz y el azafrán, y revuelve hasta que el arroz esté dorado.",
            "3. Añade el caldo y cocina a fuego lento hasta que el arroz esté cocido y haya absorbido todo el líquido."
        ));
        return "detalleReceta";
    }
}