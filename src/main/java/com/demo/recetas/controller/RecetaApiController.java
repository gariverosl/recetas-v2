// RecetaApiController.java
package com.demo.recetas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.recetas.model.Receta;
import com.demo.recetas.repository.RecetaRepository;
// RecetaApiController.java
@RestController
@RequestMapping("/api/recetas")
public class RecetaApiController {
    private final RecetaRepository recetaRepository;

    @Autowired
    public RecetaApiController(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receta>> buscarRecetas(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestHeader("Authorization") String authHeader) {
        List<Receta> resultados = nombre != null ? 
            recetaRepository.findByNombreContaining(nombre) : 
            List.of();
        return ResponseEntity.ok(resultados);
    }
}