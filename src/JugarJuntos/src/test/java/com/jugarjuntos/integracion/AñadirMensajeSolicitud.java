package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Entities.Participacion;
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
public class AñadirMensajeSolicitud {


	@Autowired
	private SAParticipacion saParticipacion;

	@Autowired
	private SAUsuario saUsuario;

	@Autowired
	private SAAnuncio saAnuncio;
	
	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	UsuarioRepository userRepo;
	
	@Autowired
	ParticipacionRepository partRepo;

	private static Long idAnuncio = null;

	private static Long idUsuario = null;

	@Test
	public void asetup() {

		idUsuario = saUsuario.altaUsuario(new TUsuario("TestNombre", "test@gmail.com", "test12", "TestDiscord#1234"));
		idAnuncio = saAnuncio.altaAnuncio(new TAnuncio("JuegoTest", 0, 6, "Pendiente", idUsuario.intValue(), new ArrayList<TParticipacion>()));
		assertNotNull(idAnuncio);
		assertNotNull(idUsuario);
	}

	@Test
	public void bTestEnviarSolicitudBien() {
		assertEquals(saParticipacion.enviarSolicitud(new TParticipacion(idUsuario, idAnuncio,"pendiente", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")), 0);
	}
	
	@Test
	void cClean() {
		List<Participacion> p = partRepo.findParticipacionById(idAnuncio, idUsuario);
		for(Participacion pa: p) {
			partRepo.delete(pa);
		}
		anuncioRepo.deleteById(idAnuncio);
		userRepo.deleteById(idUsuario);
	}
	
}
