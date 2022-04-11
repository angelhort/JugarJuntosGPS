package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.ParticipacionRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TParticipacion;

@Service
public class SAParticipacionImpTest implements SAParticipacionTest {
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
	public boolean aceptarSolicitud(TParticipacion participacion) {

		// Comprobamos que funciona la logica de negocio del método

		Participacion p = null;

		if (participacion != null)
			p = new Participacion(); // Se comprueba que existe información en el Transfer

		if (p != null) { // Si se encontro información se prueba si existe la participacion si existe
							// devuelve true si no devuelve false
			return true;
		}

		return false;
	}

	@Override
	public boolean rechazarSolicitud(TParticipacion participacion) {
		List<Participacion> p = participacionRepository.findParticipacionById(participacion.getId_anuncio(),
				participacion.getId_usuario());

		if (p != null) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean enviarSolicitud(TParticipacion participacion) {
		if (anuncioRepository.findById(participacion.getId_anuncio()) != null && // Se comprueba que tanto el anuncio
																					// como el usuario existan, que el
																					// anuncio no haya finalizado, que
																					// las
				usuarioRepository.findUsuarioById(participacion.getId_usuario()) != null && // personas actuales no
																							// superen al maximo de
																							// personas y que el usuario
																							// participe en mas anuncios
																							// al mismo tiempo
				anuncioRepository.findById(participacion.getId_anuncio()).getEstado().equalsIgnoreCase("pendiente") && // Comprobar
																														// que
																														// el
																														// anuncio
																														// todavía
																														// esta
																														// en
																														// estado
																														// pendiente
																														// y
																														// por
																														// tanto
																														// podemos
																														// unirnos
				anuncioRepository.findById(participacion.getId_anuncio()).getPersonas_actuales() < anuncioRepository
						.findById(participacion.getId_anuncio()).getMax_personas()
				&& participacionRepository.findAllByIdAnuncio_idPendientes(participacion.getId_usuario()) != null) {

			participacionRepository.aniadirSolicitud("esperando", "pendiente", participacion.getId_usuario(),
					participacion.getId_anuncio());
			return true;
		}

		else
			return false;
	}

}
