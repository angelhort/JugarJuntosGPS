package com.jugarjuntos.registro;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;

import com.jugarjuntos.ServiciosAplicacion.SAUsuario;

public class CleanTestData {
	@Autowired
	SAUsuario saUsuario;
	
    @AfterAll
    void cleanup() {
    	saUsuario.borrarPorCorreo("correo1@gmail.com");
    	saUsuario.borrarPorCorreo("correo2@gmail.com");
    	saUsuario.borrarPorCorreo("correo3@gmail.com");
    	saUsuario.borrarPorCorreo("correo4@gmail.com");
    }
}