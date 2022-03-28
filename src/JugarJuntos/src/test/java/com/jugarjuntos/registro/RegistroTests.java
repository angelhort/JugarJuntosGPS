package com.jugarjuntos.registro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

import java.util.ArrayList;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Se probarán las operaciones en orden alfabetico
public class RegistroTests extends CleanTestData {

	@Autowired
	SAUsuario saUsuario;
	
	TUsuario tUsuarioCorrecto = new TUsuario("nombre1", "correo1@gmail.com", "password1", "discord1", new ArrayList<TAnuncio>(), new ArrayList<TParticipacion>(), "libre");
    TUsuario tUsuarioNombreIncorrecto = new TUsuario("", "correo2@gmail.com", "password2", "discord2", new ArrayList<TAnuncio>(), new ArrayList<TParticipacion>(), "libre");
    TUsuario tUsuarioGmailIncorrecto = new TUsuario("nombre3", "correo3", "password3", "discord3", new ArrayList<TAnuncio>(), new ArrayList<TParticipacion>(), "libre");
    TUsuario tUsuarioDiscordIncorrecto = new TUsuario("nombre4", "correo4@gmail.com", "password4", "", new ArrayList<TAnuncio>(), new ArrayList<TParticipacion>(), "libre");

	// Operacion que prueba si funciona la operacion de crear usuario con todos los campos rellenos y correctos
	@Test
	@Order(1)
	void createOneFineTest() {
		assertNotEquals(-1, saUsuario.altaUsuario(tUsuarioCorrecto));
	}

	// Operacion que prueba si cuando hay un dato repetido
	// en la clave primaria falla en la operación de creación usuario
	@Test
	@Order(2)
	void createOneWrongTest() {
		assertEquals(-1, saUsuario.altaUsuario(tUsuarioCorrecto));
	}

    //Operacion que prueba si da error la creación de usuario con un nombre incorrecto(en este caso nombre vacio)
    @Test
	@Order(3)
	void crearNombreIncorrectoTest() {
		assertEquals(-1, saUsuario.altaUsuario(tUsuarioNombreIncorrecto));
	}

    //Operacion que prueba si da error la creación de usuario con un gmail incorrecto(no cumple el fromato correcto "@")
    @Test
	@Order(4)
	void crearGmailIncorrectoTest() {
		assertEquals(-1, saUsuario.altaUsuario(tUsuarioGmailIncorrecto));
	}

    //Operacion que prueba si da error la creación de usuario con un discord incorrecto(en este caso discord vacio)
    @Test
	@Order(5)
	void crearDiscordVacioTest() {
		assertEquals(-1, saUsuario.altaUsuario(tUsuarioDiscordIncorrecto));
	}
    
    @Test
    @Order(6)
    void cleanup() {
    	saUsuario.borrarPorCorreo("correo1@gmail.com");
    }
}
