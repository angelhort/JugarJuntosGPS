package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

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
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ComenzarPartida {

	@Autowired
	SAAnuncio saAnuncio;

	@Autowired
	SAUsuario saUsuario;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AnuncioRepository anuncioRepository;
	
	private static Long idAnuncio = null;

	private static Long idUsuario = null;
	

	@Test
	public void testComenzarPartida() {
		idUsuario = saUsuario.altaUsuario(new TUsuario("pruebaCom", "pruebaCom@gmail.com", "pruebaCom", "pruebaCom#1112"));
		idAnuncio = saAnuncio.altaAnuncio(
				new TAnuncio( "TestpruebaCom", 1, 2, "pendiente", idUsuario.intValue(), new ArrayList<TParticipacion>()));
		assertNotNull(idAnuncio);
		assertNotNull(idUsuario);

		assertTrue(saAnuncio.empezarAnuncio(idAnuncio, idUsuario));

		Optional<Anuncio> anuncio = anuncioRepository.findById(idAnuncio);
		anuncioRepository.delete(anuncio.get());
		usuarioRepository.delete(usuarioRepository.findUsuarioById(idUsuario));

	}
}