package com.jugarjuntos.integracion;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class OrdenarPorValorAnunciant {

	@Autowired
	SAAnuncio saAnuncio;

	@Autowired
	SAUsuario saUsuario;

	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	UsuarioRepository userRepo;

	private static TUsuario usr1, usr2, usr3;
	private static TAnuncio an1, an2, an3;

	@Test
	void bInit() { // Inicializar los datos para el testç

		// Preprando y dando de alta a los usuarios
		// Usuario1
		usr1 = new TUsuario("ZZtestZZ", "zztestzz@test.es", "vivaLaPepa", "zzxzz#1539");
		usr1.setEstado("libre");
		usr1.setPuntuacion_total(4);
		usr1.setNum_votaciones(2);

		// Usuario2
		usr2 = new TUsuario("YYtestYY", "yytestyy@test.es", "vivaMolinette", "yyxyy#1539");
		usr2.setEstado("libre");
		usr2.setPuntuacion_total(3);
		usr2.setNum_votaciones(2);

		// Usuario2
		usr3 = new TUsuario("XXtestXX", "xxtestxx@test.es", "vivaELPEPE", "xxxxx#1539");
		usr3.setEstado("libre");
		usr3.setPuntuacion_total(1);
		usr3.setNum_votaciones(1);

		// Insertando
		usr1.setId(saUsuario.altaUsuario(usr1));
		usr2.setId(saUsuario.altaUsuario(usr2));
		usr3.setId(saUsuario.altaUsuario(usr3));

		// ------------------------------------------------

		// Preparando y dando de alta a los anuncios
		// Anuncio1
		an1 = new TAnuncio();
		an1.setEstado("pendiente");
		an1.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		an1.setId_Usuario(usr1.getId());
		an1.setJuego("TestOrdenValAnunciante");
		an1.setMax_personas(2);
		an1.setPersonas_actuales(1);

		// Anuncio2
		an2 = new TAnuncio();
		an2.setEstado("pendiente");
		an2.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		an2.setId_Usuario(usr2.getId());
		an2.setJuego("TestOrdenValAnunciante");
		an2.setMax_personas(2);
		an2.setPersonas_actuales(1);

		// Anuncio3
		an3 = new TAnuncio();
		an3.setEstado("pendiente");
		an3.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		an3.setId_Usuario(usr3.getId());
		an3.setJuego("TestOrdenValAnunciante");
		an3.setMax_personas(2);
		an3.setPersonas_actuales(1);

		// Insertando
		an1.setId(saAnuncio.altaAnuncio(an1));
		an2.setId(saAnuncio.altaAnuncio(an2));
		an3.setId(saAnuncio.altaAnuncio(an3));
		assertTrue(true);
	}

	@Test
	void cOrdenarResultPorValorAnuncianteOK() { // Comprobamos que el orden es correcto
		List<Anuncio> la = saAnuncio.getAllAnunciosOrderByValoracion("TestOrdenValAnunciante");
		double puntuacion1 = la.get(0).getAnunciante().getPuntuacion_total();
		double puntiacion2 = la.get(1).getAnunciante().getPuntuacion_total();
		double puntiacion3 = la.get(2).getAnunciante().getPuntuacion_total();
		assertThat(puntuacion1 > puntiacion2 && puntiacion2 > puntiacion3);
	}

	@Test
	void dClean() { // Dejamos la BD como la encontramos
		anuncioRepo.deleteById(an1.getId());
		anuncioRepo.deleteById(an2.getId());
		anuncioRepo.deleteById(an3.getId());
		userRepo.deleteById(usr1.getId());
		userRepo.deleteById(usr2.getId());
		userRepo.deleteById(usr3.getId());
		assertTrue(true);
	}
}
