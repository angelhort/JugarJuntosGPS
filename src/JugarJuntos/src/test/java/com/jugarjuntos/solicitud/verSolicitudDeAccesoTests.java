package com.jugarjuntos.solicitud;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class verSolicitudDeAccesoTests {
	
	@Autowired
	SAParticipacion saP;
	
	/* Comprobamos que al introducir un anuncio existente CON solicitudes pendientes
	   asociadas se devuelve una lista con estas*/
	@Test
	void bVerSolicitudesAnuncioConSolicitudes(){ 
		assertTrue(saP.solicitudesPendientes(2).size() > 0);
	}
	
	/* Comprobamos que al introducir un anuncio existente SIN solicitudes pendientes
	   asociadas se devuelve una lista vacía*/
	@Test
	void bVerSolicitudesAnuncioSinSolicitudes() {
		assertEquals(0, saP.solicitudesPendientes(75).size());
	}
	
	/* Comprobamos que al introducir un anuncio inexistente se devuelve una lista vacía*/
	@Test
	void bVerSolicitudesAnuncioInexistente() {
		assertEquals(0, saP.solicitudesPendientes(999999999).size());
	}
}
