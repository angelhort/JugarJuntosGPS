package com.jugarjuntos.ServiciosAplicacion;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetails;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TAnuncio;

@Service
public class SAAnuncioImp implements SAAnuncio {

	@Autowired
	AnuncioRepository anuncioRepo;

	@Autowired
	EntityManager em;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	@Transactional
	public long altaAnuncio(TAnuncio tAnuncio) {
		long id = -1;
		long id_usr = -1;

		if (tAnuncio.getMax_personas() > 0 && tAnuncio.getJuego().length() <= 150
				&& tAnuncio.getMax_personas() <= 255) {

			Anuncio anuncio = new Anuncio();

			try {
				id_usr = usuarioRepository.findUsuarioById(tAnuncio.getId_Usuario()).getId();

				if (anuncioRepo.findAllByAnunciante(id_usr).size() > 0) {
					throw new Exception();
				} else {
					anuncio.setAnunciante(usuarioRepository.findUsuarioById(tAnuncio.getId_Usuario()));
					anuncio.setFecha_creacion(
							Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
					anuncio.setJuego(tAnuncio.getJuego());
					anuncio.setPersonas_actuales(1); // Se incluye por defecto al anunciante
					anuncio.setMax_personas(tAnuncio.getMax_personas());
					anuncio.setEstado(tAnuncio.getEstado());
					anuncioRepo.save(anuncio);

					id = anuncio.getId();

				}

			} catch (Exception e) {
				id = -2;
			}
		}

		return id;

	}

	@Override
	public List<Anuncio> getAnunciosByNombreJuego(String juego) {

		// TypedQuery<Anuncio> query =
		// em.createNamedQuery("AnuncioBuscarPorJuego",Anuncio.class);
		// query.setParameter("juego", "%" + juego + "%");
		// List<Anuncio> a = query.getResultList();

		return anuncioRepo.findAllByJuego(juego);

	}

	public Anuncio findAnuncioByUser(long id_user) {
		Usuario usuario = em.find(Usuario.class, id_user);
		/*
		 * return usuario.getParticipacion().stream() .filter(p ->
		 * p.getEstado_partida().equals("en_lobby")) .map(p -> em.find(Anuncio.class,
		 * p.getId().getAnuncio_id())) .findFirst() .orElse(null);
		 */
		List<Participacion> participaciones = usuario.getParticipacion();

		for (Participacion p : participaciones) {
			if (p.getEstado_partida() == "en_lobby") {
				Anuncio a = em.find(Anuncio.class, p.getId().getAnuncio_id());
//				 em.close();
				return a;
			}

		}

//		em.close();
		return null;

	}

	@Override
	public List<Anuncio> getAllAnuncios() {
		return anuncioRepo.findAll();
	}

	@Override
	public Anuncio getAnuncioByID(long id) {
		return anuncioRepo.findById(id);
	}

	@Override
	public List<Anuncio> getAllAnunciosOrderByTime() {
		List<Anuncio> sol = anuncioRepo.findAll();
		Collections.sort(sol, new Comparator<Anuncio>() {
			@Override
			public int compare(Anuncio o1, Anuncio o2) {
				return o2.getFecha_creacion().compareTo(o1.getFecha_creacion());
			}

		});
		return sol;
	}

}
