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
public class OrdenarPorNombreTests {
	@Autowired 
	SAAnuncioTest sa;
	
	
	//Comprueba que se ordena bien por juego
	@Test
	void bOrdenarPorNombreBienConJuegoBien() {
		assertNotNull(sa.getAllAnunciosOrderByTime("juego"));
	}
	
	//Comprueba que se ordena bien sin juego
	@Test
	void cOrdenarPorNombreSinJuegoBien() {
		assertNotNull(sa.getAllAnunciosOrderByTime(""));
		
	
	}
	
}
