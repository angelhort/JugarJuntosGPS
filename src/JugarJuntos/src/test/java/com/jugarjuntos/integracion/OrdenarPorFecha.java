package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import com.jugarjuntos.ServiciosAplicacion.SAAnuncioImp;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.ServiciosAplicacion.SAUsuarioImp;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Ejecutar pruebas por orden alfabético
public class OrdenarPorFecha {

	@Autowired
	SAAnuncioImp sA;

	@Autowired
	SAUsuarioImp sAUsuario;
	
	@Autowired
	AnuncioRepository anuncioRepo;
	
	@Autowired
	UsuarioRepository userRepo;

	private static List<Anuncio> lista;

	private static long id_usr1;
	private static long id_usr2;

	private static TUsuario usr1;
	private static TUsuario usr2;

	private static TAnuncio taux;
	private static TAnuncio taux1;

	private static long id_anuncio;
	private static long id_anuncio1;

	
	private static int tam;

	@Test
	void aSetValues() {
		
		//Creo un nuevo usuario para los test
		usr1 = new TUsuario();
		usr1.setCorreo("usrtest200@test.com");
		usr1.setNombre("usrtest200");
		usr1.setPassword("pswdusr200");
		usr1.setDiscord("discordusr#2000");
		usr1.setEstado("libre");

		usr2 = new TUsuario();
		usr2.setCorreo("usrtest300@test.com");
		usr2.setNombre("usrtest300");
		usr2.setPassword("pswdusr300");
		usr2.setDiscord("discordusr#3000");
		usr2.setEstado("libre");

		// --------------------------
		//Inserto al usuario

		id_usr1 = sAUsuario.altaUsuario(usr1);
		usr1.setId(id_usr1);
		id_usr2 = sAUsuario.altaUsuario(usr2);
		usr2.setId(id_usr2);

		// --------------------------
		
		//Creo e inserto el anuncio
		taux = new TAnuncio();
		taux.setFecha_creacion(
						Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		taux.setEstado("pendiente");
		taux.setId_Usuario(id_usr1);
		taux.setJuego("Juegostest200");
		taux.setMax_personas(3);
		taux.setPersonas_actuales(1);

		taux1 = new TAnuncio();
		taux1.setFecha_creacion(
						Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		taux1.setEstado("pendiente");
		taux1.setId_Usuario(id_usr2);
		taux1.setJuego("Juegostest300");
		taux1.setMax_personas(3);
		taux1.setPersonas_actuales(1);

		// --------------------------

		id_anuncio1 = sA.altaAnuncio(taux1);
		id_anuncio = sA.altaAnuncio(taux);
		taux.setId(id_anuncio);
		taux1.setId(id_anuncio1);
		
		//---------------------------
		//Extraigo la lista de anuncios ordenada
		
		lista = sA.getAllAnunciosOrderByTime("");
		tam=lista.size();
		assertEquals(true, true);
	}

	@Test
	void bbuscarOK() { //Comprobar que el juego es el primero
		assertEquals("Juegostest300", lista.get(0).getJuego());
	}
	
	@Test
	void cbuscarKO() {	//Comprobar que el juego no está al final de la lista
		assertNotEquals("Juegostest300", lista.get(tam-1).getJuego());
	}
	
	@Test
	void zendAll() {//Deja la base de datos como estaba
		anuncioRepo.deleteById(id_anuncio);
		anuncioRepo.deleteById(id_anuncio1);
		userRepo.deleteById(id_usr1);
		userRepo.deleteById(id_usr2);
		assertEquals(true, true);
	}


}
