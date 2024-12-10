// BuscarController.java
package com.demo.recetas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuscarController {
    @GetMapping("/buscar")
    public String mostrarPaginaBusqueda() {
        return "buscar";
    }
}
