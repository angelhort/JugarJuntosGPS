package com.jugarjuntos.anuncio;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioTest;
@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class ComenzarPartidaTest {
	@Autowired 
	SAAnuncioTest sa;
	
	@Test
	void bcomprobarComenzarPartida() {
		assertTrue(sa.empezarAnuncio(1, 1));
	}
	@Test
	void ccomprobarComenzarPartidaNullAnun() {
		assertFalse(sa.empezarAnuncio(4, 1));
		
	}
	@Test
	void dcomprobarComenzarPartidaNullUser() {
		assertFalse(sa.empezarAnuncio(1, 4));
	}
	@Test
	void ecomprobarComenzarPartidaEstado() {
		assertFalse(sa.empezarAnuncio(5, 1));
	}
	
}
