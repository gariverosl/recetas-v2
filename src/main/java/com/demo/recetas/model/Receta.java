package com.demo.recetas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recetas")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipoCocina;

    @Lob
    private String ingredientes;

    @Lob
    private String instrucciones;

    private Integer tiempoCoccion;
    private String dificultad;

    //Contructor

    public Receta() {
    }

    public Receta(String nombre, String tipoCocina, String ingredientes, String instrucciones, Integer tiempoCoccion, String dificultad) {
        this.nombre = nombre;
        this.tipoCocina = tipoCocina;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.tiempoCoccion = tiempoCoccion;
        this.dificultad = dificultad;
    }


    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Integer getTiempoCoccion() {
        return tiempoCoccion;
    }

    public void setTiempoCoccion(Integer tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
