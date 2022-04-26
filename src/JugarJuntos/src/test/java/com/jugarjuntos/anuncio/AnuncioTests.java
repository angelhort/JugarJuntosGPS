package com.jugarjuntos.anuncio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioTest;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TUsuario;

import java.sql.Date;
import java.time.ZoneId;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class AnuncioTests {

	@Autowired
	SAAnuncioTest sAAnuncio;

	@Autowired
	SAAnuncioTest sAAnuncioTest;

	private static TAnuncio anuncio;

	// Operacion que prueba si funciona la operacion de crear anuncio
	@Test
	void bcreateOneFineTest() {
		anuncio = new TAnuncio();
		anuncio.setEstado("pendiente");
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		anuncio.setId_Usuario(1);
		anuncio.setJuego("TestAltaAnuncio");
		anuncio.setMax_personas(2);
		anuncio.setPersonas_actuales(1);
		anuncio.setId(sAAnuncio.altaAnuncio(anuncio));
		assertNotEquals(-1, anuncio.getId());
	}

	// Operacion que prueba si se buscan todos los anuncios
	@Test
	void cbuscarTodosTest() {
		assertNotNull(sAAnuncio.getAllAnuncios());
	}

	// Operacion que busca un anuncio por id y lo devuelve
	@Test
	void dgetDetallesAnuncioTest() {
		assertNotNull(sAAnuncio.getAnuncioByID(2));
	}

	@Test
	void eborrarAnuncioBien() {
		assertTrue(sAAnuncioTest.borrarAnuncio(1));
	}

	@Test
	void fborrarAnuncioMal() {
		assertFalse(sAAnuncioTest.borrarAnuncio(10));
	}

}