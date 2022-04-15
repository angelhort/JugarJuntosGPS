package com.jugarjuntos.solicitud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAParticipacionTest;
import com.jugarjuntos.Transfers.TParticipacion;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico

public class EnviarSolicitudDeAcceso {
	@Autowired
	SAParticipacionTest sa;
	
	//Test que comprueba que se manda la solicitud correctamente
	void aEnviarSolicitudTestBien() {
		assertTrue(sa.enviarSolicitud(new TParticipacion()));
		
	}

	
	
	//Test que comprueba que se manda la solicitud y se manda mal

	void bEnviarSolicitudTestMal() {
		assertFalse(sa.enviarSolicitud(null));
	
	}
}
