package com.demo.recetas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.recetas.model.Receta;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    // Método personalizado para buscar recetas que contengan el nombre especificado
    List<Receta> findByNombreContaining(String nombre);
    // Obtener las 5 recetas más recientes ...
    List<Receta> findTop5ByOrderByIdDesc();
}

