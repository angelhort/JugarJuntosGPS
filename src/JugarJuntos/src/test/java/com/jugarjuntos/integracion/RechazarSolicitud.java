package com.jugarjuntos.integracion;

import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class RechazarSolicitud {
	
	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired
	SAUsuario saUsuario;
	
	@Autowired
	SAParticipacion saParticipacion;
	
	private TAnuncio anuncio;
	private TUsuario usuario;
	private TParticipacion participacion;
	
	@BeforeAll
	public void setup() throws BusinessException {
		anuncio = new TAnuncio();
		anuncio.setJuego("juegoDePrueba");
		anuncio.setMax_personas(4);
		anuncio.setEstado("pendiente");
		anuncio.setPersonas_actuales(1);
		anuncio.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		
		anuncio.setId(saAnuncio.altaAnuncio(anuncio));
		
		usuario = new TUsuario("Pepelu", "pepeluGucci@ucm.es", "contrasenia", "pepeluLoko#4536");
		usuario.setId(saUsuario.altaUsuario(usuario));
		
		
		participacion = new TParticipacion(usuario.getId(), anuncio.getId(), null, "");
		saParticipacion.enviarSolicitud(participacion);
	}
	
	
	@Test
	public void checkRechazarSolicitud() throws BusinessException {
		assertEquals(true, saParticipacion.rechazarSolicitud(participacion));
	}
}
