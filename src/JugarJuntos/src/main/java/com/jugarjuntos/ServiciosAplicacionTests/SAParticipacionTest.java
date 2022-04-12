package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.List;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Transfers.TParticipacion;

public interface SAParticipacionTest {
	List<Participacion> solicitudesPendientes(long id);

	boolean aceptarSolicitud(TParticipacion participacion);

	boolean rechazarSolicitud(TParticipacion participacion);

	boolean enviarSolicitud(TParticipacion participacion);
}
