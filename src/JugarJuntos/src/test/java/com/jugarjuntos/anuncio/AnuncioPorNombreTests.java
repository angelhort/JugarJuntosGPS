package com.jugarjuntos.anuncio;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.ZoneId;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class AnuncioPorNombreTests {

	@Autowired
	SAAnuncio sA;
	
	@Autowired
	SAUsuario sU;
	
	@Autowired
	AnuncioRepository anunRepo;
	
	@Autowired
	UsuarioRepository userRepo;

	private static TUsuario user;
	private static TAnuncio anun;

	@Test
	void aInit() {
		
		//Inserto usuario
		user = new TUsuario("YOLO", "yolo@yolo.es", "vivaELYOLO", "yolo#2358");
		user.setEstado("libre");
		user.setPuntuacion_total(1);
		user.setNum_votaciones(1);
		user.setId(sU.altaUsuario(user));
		
		//Inserto anuncio
		anun = new TAnuncio();
		anun.setEstado("pendiente");
		anun.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		anun.setId_Usuario(user.getId());
		anun.setJuego("TestAnuncioPorNombreOK");
		anun.setMax_personas(2);
		anun.setPersonas_actuales(1);
		anun.setId(sA.altaAnuncio(anun));
		
		assertTrue(true);
	}

	// Comprobacion buscar anuncios por juego con juego existente
	@Test
	void bBuscarPorNombreJuegoOKTest() {
		assertTrue(sA.getAnunciosByNombreJuego("TestAnuncioPorNombre").size() > 0);
	}

	// Comprobacion buscar anuncios por juego con juego NO existente
	@Test
	void cBuscarPorNombreJuegoKOTest() {
		assertTrue(sA.getAnunciosByNombreJuego("En un lugar de la mancha de cuyo nombre no quiero acordarme").size() == 0);
	}
	
	@Test
	void zClean() {
		anunRepo.deleteById(anun.getId());
		userRepo.deleteById(user.getId());
		assertTrue(true);
	}

}
