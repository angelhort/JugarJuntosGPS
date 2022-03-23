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
		return participacionRepository.findAllByIdAnuncio_id(id);
	}

	@Override
	public boolean aceptarSolicitud(TParticipacion participacion) throws BusinessException {
		//Comprobamos que existe esta solicitud en la sala
		if(participacionRepository.findParticipacionById(participacion.getId_anuncio(), participacion.getId_usuario()) != null) {
			
			// Incrementamos las personas en la sala en 1
			anuncioRepository.incrementPersonasActuales(participacion.getId_anuncio());
			
			//Cambiamos el estado del usuario en la sala a aceptado
			participacionRepository.cambiarEstadoAceptado(participacion.getId_anuncio(), participacion.getId_usuario());
			
			return true;
		}
			
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	@Override
	public boolean rechazarSolicitud(TParticipacion participacion) throws BusinessException {
		if(participacionRepository.findParticipacionById(participacion.getId_anuncio(), participacion.getId_usuario()) != null) {
			participacionRepository.eliminarUsuarioParticipacion(participacion.getId_usuario(),participacion.getId_anuncio());

					return true;
					
				}
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	@Override
	public boolean enviarSolicitud(TParticipacion participacion) throws BusinessException {
		if(anuncioRepository.findById(participacion.getId_anuncio()) != null &&       // Se comprueba que tanto el anuncio como el usuario existan, que el anuncio no haya 
		usuarioRepository.findUsuarioById(participacion.getId_usuario()) != null &&   // finalizado y que las personas actuales no superen al maximo de personas
		anuncioRepository.findById(participacion.getId_anuncio()).getEstado() == "pendiente" &&
		anuncioRepository.findById(participacion.getId_anuncio()).getPersonas_actuales() < anuncioRepository.findById(participacion.getId_anuncio()).getMax_personas()) {
			
			participacionRepository.aniadirSolicitud(participacion.getId_usuario(), participacion.getId_anuncio());
			return true;
		}
	
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	
}
