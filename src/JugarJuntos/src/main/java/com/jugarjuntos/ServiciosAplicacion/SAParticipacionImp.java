package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.ParticipacionRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TParticipacion;
@Service
public class SAParticipacionImp implements SAParticipacion{
	@Autowired
	ParticipacionRepository participacionRepository;
	
	@Autowired
	AnuncioRepository anuncioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public List<Participacion> solicitudesPendientes(long id) {
		System.out.println("ENTRA");
		return participacionRepository.findAllByIdAnuncio_id(id);
	}

	@Override
	public boolean aceptarSolicitud(TParticipacion participacion) throws BusinessException {
		//Comprobamos que existe esta solicitud en la sala
		if(anuncioRepository.findById(participacion.getId_anuncio()) != null && 
				usuarioRepository.findUsuarioById(participacion.getId_usuario()) != null) {
			
			// Incrementamos las personas en la sala en 1
			anuncioRepository.incrementPersonasActuales(participacion.getId_anuncio());
			
			//Cambiamos el estado del usuario en la sala a aceptado
			usuarioRepository.cambiarEstadoAceptado(participacion.getId_usuario());
			
			//Cambiamos el estado del usuario en la sala a aceptado
			participacionRepository.insertarUsuarioEnSala(
					participacion.getId_anuncio(), participacion.getId_usuario());
			
			return true;
		}
			
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	@Override
	public boolean rechazarSolicitud(TParticipacion participacion) throws BusinessException {
		if(participacionRepository.findParticipacionById(
				participacion.getId_anuncio(),
				participacion.getId_usuario()) != null ){
					//participacionRepository.changeEstadoSolicitudDenegada(
							//participacion.getId_anuncio(), participacion.getId_usuario());
					
					return true;
					
				}
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	
}
