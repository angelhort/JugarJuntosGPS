package com.jugarjuntos.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAUsuarioTest;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class MostrarValoracionJugadorTests {

	@Autowired
	SAUsuarioTest sa;

	// Test que comprueba que se devolveria la información correcta
	@Test
	void aMostrarValoracionJugadorBien() {
		assertTrue(sa.mostrarValoracion(1));
	}

	// Test que comprueba que llega al SA un usuario inexistente por lo que no funcionaria
	// error

	@Test
	void bMostrarValoracionJugadorMal() {
		assertFalse(sa.mostrarValoracion(99));
	}

}
