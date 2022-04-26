package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Transfers.TParticipacion;

public interface SAParticipacion {
	List<Participacion> solicitudesPendientes(long id);
	
	boolean aceptarSolicitud(TParticipacion participacion) throws BusinessException;
	boolean rechazarSolicitud(TParticipacion participacion) throws BusinessException;
	int enviarSolicitud(TParticipacion participacion);
	boolean isUserInPartida(long idAnuncio, long idUsuario);
}
