package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TerminarAnuncio {

	@Autowired
	SAAnuncio saAnuncio;

	@Autowired
	SAUsuario saUsuario;

	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	UsuarioRepository usuarioRepo;
	

	@Test
	public void testTerminarAnuncio() {
		TUsuario usuario = new TUsuario("PepeluGucci", "pepeluGucciDior@ucm.es", "contra", "pepeluLoko#4999");
		usuario.setId(saUsuario.altaUsuario(usuario));
		
		Long idAnuncio = saAnuncio.altaAnuncio(new TAnuncio("Test terminar anuncio", 1, 6, "Pendiente",(int) usuario.getId(), new ArrayList<TParticipacion>()));
		
		assertNotNull(idAnuncio);
		
		assertTrue(saAnuncio.terminarAnuncio(idAnuncio.intValue()));

		anuncioRepo.deleteById(idAnuncio);
		usuarioRepo.deleteById(usuario.getId());
	}
	
}
