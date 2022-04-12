package com.jugarjuntos.integracion;

import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
public class AceptarSolicitud {

	@Autowired
	SAAnuncio saAnuncio;
	@Autowired
	SAUsuario saUsuario;
	@Autowired
	SAParticipacion saParticipacion;

	private TAnuncio anuncio;
	private TUsuario usuario;
	private TUsuario anunciante;
	private TParticipacion participacion;
	private long id_anunciante;

	@BeforeEach
	public void stat() {
		anunciante = new TUsuario("anunciantePrueba", "Anunpro@gmail.com", "1234", "anun#3341");
		id_anunciante = saUsuario.altaUsuario(anunciante);
		usuario = new TUsuario("kyliansito", "kiliapro@gmail.com", "1234", "kili#3245");
		usuario.setId(saUsuario.altaUsuario(usuario));
		
	}
	
	@Test
	public void bAceptarSolicitudOK() throws BusinessException {
		// Anuncio normal que permitirá aceptar solicitudes	
		anuncio = new TAnuncio();
		anuncio.setJuego("anuncioPruebaAS");
		anuncio.setPersonas_actuales(1);
		anuncio.setMax_personas(200);
		anuncio.setEstado("pendiente");
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		anuncio.setId_Usuario(id_anunciante);
		anuncio.setId(saAnuncio.altaAnuncio(anuncio));
		
		participacion = new TParticipacion(usuario.getId(), anuncio.getId());
		saParticipacion.enviarSolicitud(participacion);

		assertTrue(saParticipacion.aceptarSolicitud(participacion));
	}
}
