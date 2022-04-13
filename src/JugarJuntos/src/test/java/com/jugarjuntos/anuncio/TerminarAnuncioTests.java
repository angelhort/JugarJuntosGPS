package com.jugarjuntos.anuncio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioTest;
@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class TerminarAnuncioTests {

	@Autowired
	SAAnuncioTest sa;

	// Test que comprueba que se cambia que se termina el anuncio correctamente
	@Test
	void aTerminarAnuncioTestBien() {
		assertTrue(sa.terminarAnuncio(1));
	}

	// Test que comprueba que llega al SA anuncio no existente por lo que daria error

	@Test
	void bTerminarAnuncioTestMal() {
		assertFalse(sa.terminarAnuncio(99));
	}

}
