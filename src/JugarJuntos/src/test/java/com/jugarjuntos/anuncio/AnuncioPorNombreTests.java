package com.jugarjuntos.anuncio;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;

@SpringBootTest(classes = JugarJuntosApplication.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class AnuncioPorNombreTests {

	@Autowired
	SAAnuncio sA;
	
	//Comprobacion buscar anuncios por juego con juego existente 
	@Test
	void bbuscarPorNombreJuegoOKTest() {
		assertThat(sA.getAnunciosByNombreJuego("Elden Ring").size() > 0);
	}
	
	//Comprobacion buscar anuncios por juego con juego NO existente 
	@Test
	void cbuscarPorNombreJuegoKOTest() {
		assertThat(sA.getAnunciosByNombreJuego("Petanca 2023 GOTY Edition").size() == 0);
	}
	
	//Comprobacion buscar anuncios por juego con campo nombre vacío (puede ser innecesario)
	@Test
	void dbuscarPorNombreJuegoVacioTest() {
		assertThat(sA.getAnunciosByNombreJuego(null) == null);
	}
	
}
