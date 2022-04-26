package com.jugarjuntos.integracion;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class ValorarJugadores {
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
	ParticipacionRepository participacionRepo;
	
	private TAnuncio anuncio;
	private TUsuario usuario;
	private TUsuario anunciante;
	private TParticipacion participacion;
	
	@Test
	public void astart() throws BusinessException {
		anunciante = new TUsuario("aane2errqwue", "aAnqrwrrw@gmail.com", "1234", "aarnqqrwii#3341");
		anunciante.setId(saUsuario.altaUsuario(anunciante));
		usuario = new TUsuario("akrrq2t", "akis2qro1rqw@gmail.com", "1234", "akqrwwsr#3245");
		usuario.setId(saUsuario.altaUsuario(usuario));
		
		anuncio = new TAnuncio();
		
		anuncio = new TAnuncio();
		anuncio.setJuego("anuncioPrrruebAS");
		anuncio.setPersonas_actuales(1);
		anuncio.setMax_personas(2);
		anuncio.setEstado("pendiente");
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		anuncio.setId_Usuario(anunciante.getId());
		anuncio.setId(saAnuncio.altaAnuncio(anuncio));
		
		participacion = new TParticipacion(usuario.getId(), anuncio.getId(),"pendiente",  "");
		saParticipacion.enviarSolicitud(participacion);
		saParticipacion.aceptarSolicitud(participacion);
		
		saAnuncio.empezarAnuncio(anuncio.getId(), anunciante.getId());
		saAnuncio.terminarAnuncio(anuncio.getId());
	}
	
	@Test
	public void bcheckValorar() {
		List<Integer> listaNumEstrellas = new ArrayList<Integer>(); 
		List<Long> listaNumEstrellasId = new ArrayList<Long>();
		
		listaNumEstrellas.add(3);
		listaNumEstrellas.add(5);
		
		listaNumEstrellasId.add(anunciante.getId());
		listaNumEstrellasId.add(usuario.getId());
		
		assertTrue(saAnuncio.valorarJugadores(listaNumEstrellas, listaNumEstrellasId));
	}
	
	@Test
	public void zDeleteEntities() {
		Anuncio anuncio1 = anuncioRepo.findById(anuncio.getId());
		Usuario usuario1 = userRepo.findUsuarioById(usuario.getId());
		Usuario usuario2 = userRepo.findUsuarioById(anunciante.getId());

		participacionRepo.delete(participacionRepo.findParticipacionById(anuncio.getId(), usuario.getId()).get(0));
		anuncioRepo.delete(anuncio1);
		userRepo.delete(usuario1);
		userRepo.delete(usuario2);
	}
}
