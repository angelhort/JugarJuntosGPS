package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
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
		return participacionRepository.findAllByIdAnuncio_idPendientes(id);
	}

	@Override
	public boolean aceptarSolicitud(TParticipacion participacion) throws BusinessException {
		//Comprobamos que existe esta solicitud en la sala
		List<Participacion> p = participacionRepository.findParticipacionById(participacion.getId_anuncio(), participacion.getId_usuario());
		
		if(p != null) {
			//Comprobar si el anuncio ya está lleno
			Anuncio a = anuncioRepository.findById(participacion.getId_anuncio());
			if(a.getMax_personas()==a.getPersonas_actuales())
				return false;
			
			// Incrementamos las personas en la sala en 1
			//anuncioRepository.incrementPersonasActuales(participacion.getId_anuncio());
			p.get(0).getAnuncio().setPersonas_actuales(p.get(0).getAnuncio().getPersonas_actuales() + 1);
			//Cambiamos el estado del usuario en la sala a aceptado
			//participacionRepository.cambiarEstadoAceptado(participacion.getId_anuncio(), participacion.getId_usuario());
			p.get(0).setEstado_solicitud("aceptado");
			anuncioRepository.save(p.get(0).getAnuncio());
			participacionRepository.save(p.get(0));
			return true;
		}
			
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	@Override
	public boolean rechazarSolicitud(TParticipacion participacion) throws BusinessException {
		List<Participacion> p = participacionRepository.findParticipacionById(participacion.getId_anuncio(), participacion.getId_usuario());
		
		if(p != null) {
			//participacionRepository.eliminarUsuarioParticipacion(participacion.getId_usuario(),participacion.getId_anuncio());
			participacionRepository.delete(p.get(0));
			return true;	
		}
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	@Override
	public int enviarSolicitud(TParticipacion participacion) {
		Anuncio anuncio = anuncioRepository.findById(participacion.getId_anuncio());
		Usuario usuario = usuarioRepository.findUsuarioById(participacion.getId_usuario());
		List<Participacion> part = participacionRepository.findAllByIdAnuncio_idPendientes(participacion.getId_usuario());

		if (anuncio == null || usuario == null)
			return -1; // Invalid user or ad
		
		if (!anuncio.getEstado().equalsIgnoreCase("pendiente"))
			return -2; // Ad has finished or the game is being played
		
		if (anuncio.getPersonas_actuales() >= anuncio.getMax_personas())
			return -3; // Ad is full
		
		if (part != null && part.size() == 0) {
			participacionRepository.aniadirSolicitud("pendiente",participacion.getId_usuario(), participacion.getId_anuncio(), participacion.getText());
			return 0;
		} else
			return -4;
	}

	@Override
	public boolean isUserInPartida(long idAnuncio, long idUsuario) {
		if(participacionRepository.findParticipacionById(idAnuncio, idUsuario).isEmpty())
			return false;
		else return true;
	}

	
}
