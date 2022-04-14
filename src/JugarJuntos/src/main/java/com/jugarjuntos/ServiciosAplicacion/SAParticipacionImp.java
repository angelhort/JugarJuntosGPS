package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
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
	public boolean enviarSolicitud(TParticipacion participacion) throws BusinessException {
		
		if(anuncioRepository.findById(participacion.getId_anuncio()) != null &&       // Se comprueba que tanto el anuncio como el usuario existan, que el anuncio no haya finalizado, que las
		usuarioRepository.findUsuarioById(participacion.getId_usuario()) != null &&   // personas actuales no superen al maximo de personas y que el usuario participe en mas anuncios al mismo tiempo
		anuncioRepository.findById(participacion.getId_anuncio()).getEstado().equalsIgnoreCase("pendiente") && // Comprobar que el anuncio todavía esta en estado pendiente y por tanto podemos unirnos
		anuncioRepository.findById(participacion.getId_anuncio()).getPersonas_actuales() < anuncioRepository.findById(participacion.getId_anuncio()).getMax_personas() &&
		participacionRepository.findAllByIdAnuncio_idPendientes(participacion.getId_usuario()) != null && participacionRepository.findAllByIdUsuarioAnuncioAceptado(participacion.getId_usuario()).size()==0) {
			
			participacionRepository.aniadirSolicitud("esperando","pendiente",participacion.getId_usuario(), participacion.getId_anuncio());
			return true;
		}
	
		else throw new BusinessException("No existe esta solicitud de acceso a la sala.");
	}

	
}
