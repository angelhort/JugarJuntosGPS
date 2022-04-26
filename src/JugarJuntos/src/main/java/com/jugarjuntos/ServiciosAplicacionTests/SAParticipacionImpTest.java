package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.ParticipacionRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

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
		if (id > 0 && id < 75) {
			List<Participacion> la = new ArrayList<>();
			la.add(new Participacion());
			la.add(new Participacion());
			return la;
		}
		else {
			return new ArrayList<>();
		}
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
	public boolean enviarSolicitud(TParticipacion participacion, TUsuario usuario, TAnuncio anuncio) {
		List<Participacion> part = new ArrayList<>();

		if (anuncio == null || usuario == null)
			return false; // Invalid user or ad

		if (!anuncio.getEstado().equalsIgnoreCase("pendiente"))
			return false; // Ad has finished or the game is being played

		if (anuncio.getPersonas_actuales() >= anuncio.getMax_personas())
			return false; // Ad is full

		if (part != null && part.size() == 0)
			return true;

		return false;
	}
}
