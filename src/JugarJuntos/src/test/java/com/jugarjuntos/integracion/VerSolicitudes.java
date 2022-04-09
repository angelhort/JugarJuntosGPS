package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;


@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class VerSolicitudes {
	
	
	@Autowired
	SAAnuncio sAAnuncio;
	
	@Autowired
	SAUsuario sAUsuario;
	
	@Autowired
	SAParticipacion sAParticipacion;
	
	private static long id_usr1;
	
	private static long id_usr2;
	
	private static TUsuario usr1;
	
	private static TUsuario usr2;
	
	private static TAnuncio taux;
	
	private static long id_anuncio;
	
	private static TParticipacion tpart;
	
	
	@BeforeAll
	void ASetValues() {
		
		usr1 = new TUsuario();
		
		usr1.setCorreo("usrtest1@test.com");
		
		usr1.setNombre("usrtest1");
		
		usr1.setPassword("pswdusr1");
		
		usr1.setDiscord("discordusr1");
		
		usr1.setEstado("Libre");
		
		//--------------------------
		
		usr2 = new TUsuario();
		
		usr2.setCorreo("usrtest2@test.com");
		
		usr2.setNombre("usrtest2");
		
		usr2.setPassword("pswdusr2");
		
		usr2.setDiscord("discordusr2");
		
		usr2.setEstado("Libre");
		
		//--------------------------
		
		id_usr1 = sAUsuario.altaUsuario(usr1);
		
		usr1.setId(id_usr1);
		
		id_usr2 = sAUsuario.altaUsuario(usr2);
		
		usr2.setId(id_usr2);
		
		//--------------------------
		
		taux = new TAnuncio();
		
		taux.setEstado("Pendiente");
		
		taux.setId_Usuario(id_usr1);
		
		taux.setJuego("Juegostest1");
		
		taux.setMax_personas(2);
		
		taux.setPersonas_actuales(1);
		
		//--------------------------
		
		id_anuncio = sAAnuncio.altaAnuncio(taux);
		
		taux.setId(id_anuncio);
		
		tpart = new TParticipacion(id_usr1, id_usr2);
		
		tpart.setId_anuncio(id_anuncio);
		
		try {
			sAParticipacion.enviarSolicitud(tpart);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void BVerSolicitudesBien() {
		assertNotEquals(0, sAParticipacion.solicitudesPendientes(taux.getId()).size());
	}
	
	@Test
	public void CVerSolicitudesMal() {
		try {
			sAParticipacion.rechazarSolicitud(tpart);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		assertEquals(0, sAParticipacion.solicitudesPendientes(taux.getId()).size());
	}
}
