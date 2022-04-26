package com.jugarjuntos.solicitud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAParticipacionTest;
import com.jugarjuntos.Transfers.TParticipacion;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class AceptarSolicitudDeAccesoTests {

	@Autowired
	SAParticipacionTest sa;

	// Test que comprueba que llega información al SA
	@Test
	void aAceptarSolicitudTestBien() {
		assertTrue(sa.aceptarSolicitud(new TParticipacion()));
	}

	// Test que comprueba que no llega información al SA o no hay informacón de esa
	// participación

	@Test
	void bAceptarSolicitudTestMal() {
		assertFalse(sa.aceptarSolicitud(null));
	}

}
