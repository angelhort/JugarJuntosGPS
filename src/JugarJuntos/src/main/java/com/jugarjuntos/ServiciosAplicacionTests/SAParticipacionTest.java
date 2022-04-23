package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.List;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

public interface SAParticipacionTest {
	List<Participacion> solicitudesPendientes(long id);

	boolean aceptarSolicitud(TParticipacion participacion);

	boolean rechazarSolicitud(TParticipacion participacion);

	boolean enviarSolicitud(TParticipacion participacion, TUsuario usuario, TAnuncio anuncio);
}
