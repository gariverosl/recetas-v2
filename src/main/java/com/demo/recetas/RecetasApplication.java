package com.demo.recetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecetasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecetasApplication.class, args);

        /*// Crear el encriptador BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Generar hashes para las contraseñas
		 
        String password1 = "password1";
        String encodedPassword1 = encoder.encode(password1);
        System.out.println("Hash generado para usuario1: " + encodedPassword1);

        String password2 = "password2";
        String encodedPassword2 = encoder.encode(password2);
    
        System.out.println("Hash generado para usuario2: " + encodedPassword2);}*/
    }
}
