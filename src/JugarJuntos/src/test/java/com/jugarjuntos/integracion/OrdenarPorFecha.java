package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

	private static TUsuario usr1;

	private static TAnuncio taux;

	private static long id_anuncio;
	
	private static int tam;

	@Test
	void aSetValues() {
		
		//Creo un nuevo usuario para los test
		usr1 = new TUsuario();

		usr1.setCorreo("usrtest200@test.com");

		usr1.setNombre("usrtest200");

		usr1.setPassword("pswdusr200");

		usr1.setDiscord("discordusr#200");

		usr1.setEstado("Libre");

		// --------------------------
		//Inserto al usuario

		id_usr1 = sAUsuario.altaUsuario(usr1);

		usr1.setId(id_usr1);

		// --------------------------
		
		//Creo e insrto el anuncio
		taux = new TAnuncio();

		taux.setEstado("Pendiente");

		taux.setId_Usuario(id_usr1);

		taux.setJuego("Juegostest200");

		taux.setMax_personas(3);

		taux.setPersonas_actuales(1);

		// --------------------------
		
		id_anuncio = sA.altaAnuncio(taux);
		
		System.out.println(id_anuncio);
		
		taux.setId(id_anuncio);
		
		//---------------------------
		//Extraigo la lista de anuncios ordenada
		
		lista = sA.getAllAnunciosOrderByTime("");
		
		tam=lista.size();
		
		assertEquals(true, true);
	}

	@Test
	void bbuscarOK() { //Comprobar que el juego es el primero
		assertEquals("Juegostest200", lista.get(0).getJuego());
	}
	
	@Test
	void cbuscarKO() {	//Comprobar que el juego no está al final de la lista
		assertNotEquals("Juegostest200", lista.get(tam-1).getJuego());
	}
	
	@Test
	void zendAll() {//Deja la base de datos como estaba
		anuncioRepo.deleteById(id_anuncio);
		userRepo.deleteById(id_usr1);
		assertEquals(true, true);
	}


}
