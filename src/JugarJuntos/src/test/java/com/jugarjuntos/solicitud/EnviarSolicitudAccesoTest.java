package com.jugarjuntos.solicitud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAParticipacionTest;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class EnviarSolicitudAccesoTest {
    
    private static final TUsuario usuarioOK = new TUsuario("user", "mail@gmail.com", "A123", "user#2341");
	private static final TAnuncio anuncioOK = new TAnuncio("game", 1, 2, "pendiente", 1, null);
	private static final TAnuncio anuncioBAD = new TAnuncio(null, -1, -1, "badStatus", -1, null);

	@Autowired
	SAParticipacionTest sa;
	
	@Test
	void aceptarSolicitudOKTest() {
		assertEquals(sa.enviarSolicitud(new TParticipacion(), usuarioOK, anuncioOK), true);
	}

	@Test
	void invalidUserTest() {
        assertEquals(sa.enviarSolicitud(new TParticipacion(), null, anuncioOK), false);
	}

	@Test
	void invalidAdTest() {
        assertEquals(sa.enviarSolicitud(new TParticipacion(), usuarioOK, anuncioBAD), false);
	}

	@Test
	void invalidUserAndAdTest() {
        assertEquals(sa.enviarSolicitud(new TParticipacion(), null, anuncioBAD), false);
	}
}