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
import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Usuario;
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
	private long id_anunciante;

	
	@Test
	public void checkRechazarSolicitud() throws BusinessException {
		anunciante = new TUsuario("prueba111", "prueba111@gmail.com", "1234", "prueba111#3341");
		id_anunciante = saUsuario.altaUsuario(anunciante);
		usuario = new TUsuario("prueba2111", "prueba2111@gmail.com", "1234", "prueba2111#3245");

		usuario.setId(saUsuario.altaUsuario(usuario));
		anuncio = new TAnuncio();
		anuncio.setJuego("anuncioPruebAS");
		anuncio.setPersonas_actuales(1);
		anuncio.setMax_personas(200);
		anuncio.setEstado("pendiente");
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		anuncio.setId_Usuario(id_anunciante);
		anuncio.setId(saAnuncio.altaAnuncio(anuncio));

		participacion = new TParticipacion(id_anunciante, anuncio.getId(),"pendiente",  "");
		saParticipacion.enviarSolicitud(participacion);

		assertTrue(saParticipacion.rechazarSolicitud(participacion));

		Anuncio anuncio1 = anuncioRepo.findById(anuncio.getId());
		Usuario usuario1 = userRepo.findUsuarioById(usuario.getId());
		Usuario usuario2 = userRepo.findUsuarioById(id_anunciante);

		anuncioRepo.delete(anuncio1);
		userRepo.delete(usuario1);
		userRepo.delete(usuario2);
	}
}
