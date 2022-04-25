package com.jugarjuntos.integracion;

import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
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
public class RechazarSolicitud {
	
	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired
	SAUsuario saUsuario;
	
	@Autowired
	SAParticipacion saParticipacion;
	
	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	UsuarioRepository userRepo;
	
	@Autowired
	ParticipacionRepository partRepo;
	
	private TAnuncio anuncio;
	private TUsuario usuario, anunciante;
	private TParticipacion participacion;
	
	@Test
	public void aSetup() throws BusinessException {
		anuncio = new TAnuncio();
		anuncio.setJuego("juegoDePrueba");
		anuncio.setMax_personas(4);
		anuncio.setEstado("pendiente");
		anuncio.setPersonas_actuales(1);
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		
		anunciante = new TUsuario("Manu", "ManuManitas@ucm.es", "contrasenia", "manuBobo#4536");
		anunciante.setId(saUsuario.altaUsuario(anunciante));
		
		anuncio.setId_Usuario(anunciante.getId());

		anuncio.setId(saAnuncio.altaAnuncio(anuncio));
		
		usuario = new TUsuario("Pepelu", "pepeluGucci@ucm.es", "contrasenia", "pepeluLoko#4536");
		usuario.setId(saUsuario.altaUsuario(usuario));
		
		
		participacion = new TParticipacion(usuario.getId(), anuncio.getId(), null, "");
		saParticipacion.enviarSolicitud(participacion);
	}
	
	
	@Test
	public void checkRechazarSolicitud() throws BusinessException {
		assertTrue(saParticipacion.rechazarSolicitud(participacion));
	}
	
	@Test
	public void zEnd() {
		anuncioRepo.deleteById(anuncio.getId());
		userRepo.deleteById(usuario.getId());
	}
}
