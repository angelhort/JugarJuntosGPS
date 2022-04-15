package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

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
public class EnviarSolicitud {

	@Autowired
	private SAParticipacion saParticipacion;

	@Autowired
	private SAUsuario saUsuario;

	@Autowired
	private SAAnuncio saAnuncio;

	private static Long idAnuncio = null;

	private static Long idUsuario = null;

	@Test
	public void asetup() {

		idUsuario = saUsuario.altaUsuario(new TUsuario("TestNombre", "test@gmail.com", "test12", "TestDiscord"));
		idAnuncio = saAnuncio.altaAnuncio(
				new TAnuncio("JuegoTest", 0, 6, "Pendiente", idUsuario.intValue(), new ArrayList<TParticipacion>()));
		assertNotNull(idAnuncio);
		assertNotNull(idUsuario);
	}

	@Test
	public void bTestEnviarSolicitudBien() {
		assertNotEquals(saParticipacion.enviarSolicitud(new TParticipacion(idUsuario.intValue(), idAnuncio.intValue())), 0);
	}
	


}
