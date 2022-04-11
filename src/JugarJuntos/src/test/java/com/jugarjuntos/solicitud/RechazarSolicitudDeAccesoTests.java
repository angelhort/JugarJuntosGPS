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
public class RechazarSolicitudDeAccesoTests {

	@Autowired
	SAParticipacionTest sa;

	// Test que comprueba que llega información al SA
	@Test
	void aRechazarSolicitudTestBien() {
		assertTrue(sa.rechazarSolicitud(new TParticipacion()));
	}

	// Test que comprueba que no llega información al SA o no hay informacón de esa
	// participación

	@Test
	void bRechazarSolicitudTestMal() {
		assertFalse(sa.rechazarSolicitud(null));
	}

}
