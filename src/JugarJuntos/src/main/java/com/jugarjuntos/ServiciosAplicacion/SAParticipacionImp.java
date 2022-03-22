package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Repositories.ParticipacionRepository;
@Service
public class SAParticipacionImp implements SAParticipacion{
	@Autowired
	ParticipacionRepository participacionRepository;
	
	@Override
	public List<Participacion> solicitudesPendientes(long id) {
		return participacionRepository.findAllByIdAnuncio_id(id);
	}


}
