package com.jugarjuntos.anuncio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioImpTest;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioTest;
import com.jugarjuntos.Transfers.TAnuncio;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class VerAtributosBasicosTests {

	SAAnuncioTest saTest = new SAAnuncioImpTest();

	// Comprueba que los atributos se reciben bien al buscar un anuncio por id.
	@Test
	void bVerAtributosOK() {
		TAnuncio anuncio = saTest.getAnuncioByID(1);
		assertThat(anuncio.getJuego() != null && anuncio.getMax_personas() > 0 && anuncio.getParticipacion() != null
				&& anuncio.getEstado() != null && anuncio.getId_Usuario() == 1);
	}

	// cComprueba que no se recibe nada al introducir un id inexistente
	@Test
	void cVerAtributosKOInexistente() {
		TAnuncio anuncio = saTest.getAnuncioByID(4); // El id 4 no existe.
		assertThat(anuncio == null);
	}
}
