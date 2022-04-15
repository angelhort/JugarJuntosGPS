package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.ParticipacionRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BorrarAnuncio {
	@Autowired
	SAAnuncio sAAnuncio;

	@Autowired
	SAUsuario sAUsuario;

	@Autowired
	SAParticipacion sAParticipacion;

	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	UsuarioRepository userRepo;
	
	@Autowired
	ParticipacionRepository partRepo;

	private static TUsuario us1, us2;
	private static TAnuncio anuncio;
	private static TParticipacion participacion;

	@Test
	void aSetup() {
		us1 = new TUsuario("IntegBorrarAnuncio1", "unCorreo@test.com", "tester", "test#9876");
		us1.setEstado("libre");
		us2 = new TUsuario("IntegBorrarAnuncio2", "otroCorreo@test.com", "tester", "nerd#7766");
		us2.setEstado("libre");
		
		us1.setId(sAUsuario.altaUsuario(us1));
		us2.setId(sAUsuario.altaUsuario(us2));

		anuncio = new TAnuncio();
		anuncio.setEstado("Pendiente");
		anuncio.setId_Usuario(us1.getId());
		anuncio.setJuego("Juegostest1");
		anuncio.setMax_personas(2);
		anuncio.setPersonas_actuales(1);
		
		anuncio.setId(sAAnuncio.altaAnuncio(anuncio));
		
		participacion = new TParticipacion(us2.getId(), anuncio.getId());
		
		try {
			sAParticipacion.enviarSolicitud(participacion);
			sAParticipacion.aceptarSolicitud(participacion);
		} catch (BusinessException e) {
			e.printStackTrace();
			dClean();
		}
	}
	
	@Test
	void bBorrarAnuncioOK() {
		assertTrue(sAAnuncio.borrarAnuncio(anuncio.getId()));
	}
	
	@Test
	void cBorrarAnuncioInexistente() {
		assertFalse(sAAnuncio.borrarAnuncio(anuncio.getId()));
	}
	
	@Test
	void dClean() {
		for (Participacion p: anuncioRepo.findById(anuncio.getId()).getParticipacion()) {
			partRepo.delete(p);
		}
		anuncioRepo.deleteById(anuncio.getId());
		userRepo.deleteById(us1.getId());
		userRepo.deleteById(us2.getId());
	}

}
