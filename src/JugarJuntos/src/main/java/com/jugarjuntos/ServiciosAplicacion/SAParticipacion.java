package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import com.jugarjuntos.Entities.Participacion;

public interface SAParticipacion {
	List<Participacion> solicitudesPendientes(long id);
}
