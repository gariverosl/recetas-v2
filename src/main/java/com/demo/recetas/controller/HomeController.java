package com.demo.recetas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.recetas.model.Receta;
import com.demo.recetas.repository.RecetaRepository;

@Controller
public class HomeController {

    private final RecetaRepository recetaRepository;

    @Autowired
    public HomeController(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        // Obtener las últimas 5 recetas (o más si lo deseas)
        List<Receta> recetasRecientes = recetaRepository.findTop5ByOrderByIdDesc();
        model.addAttribute("recetas", recetasRecientes);
        return "index"; // Renderizar la vista index.html
    }
}
