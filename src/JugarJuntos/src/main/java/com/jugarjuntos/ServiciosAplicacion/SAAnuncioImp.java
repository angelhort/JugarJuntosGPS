package com.jugarjuntos.ServiciosAplicacion;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.ParticipacionRepository;
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

	@Autowired
	ParticipacionRepository participacionRepo;

	@Override
	@Transactional
	public long altaAnuncio(TAnuncio tAnuncio) {
		long id = -1;
		long id_usr = -1;
		
		if (tAnuncio.getMax_personas() > 225 || tAnuncio.getMax_personas() <= 1)
			return -1; // Invalid player number

		if (tAnuncio.getJuego().length() > 150)
			return -3; // Invalid game name

		Anuncio anuncio = new Anuncio();

		try {
			id_usr = usuarioRepository.findUsuarioById(tAnuncio.getId_Usuario()).getId();

			if (anuncioRepo.findAllByAnunciante(id_usr).size() > 0) {
				System.out.println("Hii");
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

		return id;

	}

	@Override
	public List<Anuncio> getAnunciosByNombreJuego(String juego) {

		// TypedQuery<Anuncio> query =
		// em.createNamedQuery("AnuncioBuscarPorJuego",Anuncio.class);
		// query.setParameter("juego", "%" + juego + "%");
		// List<Anuncio> a = query.getResultList();

		return (juego.equals("")) ? anuncioRepo.findAll() : anuncioRepo.findAllByJuego(juego);

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
			if (p.getAnuncio().getEstado() == "empezado") {
				Anuncio a = em.find(Anuncio.class, p.getId().getAnuncio_id());
				// em.close();
				return a;
			}

		}

		// em.close();
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
	public List<Anuncio> getAllAnunciosOrderByTime(String juego) {
		List<Anuncio> sol = (juego.equals("")) ? anuncioRepo.findAll() : anuncioRepo.findAllByJuego(juego);
		Collections.sort(sol, new Comparator<Anuncio>() {
			@Override
			public int compare(Anuncio o1, Anuncio o2) {
				return o2.getFecha_creacion().compareTo(o1.getFecha_creacion());
			}

		});
		return sol;
	}
	
	@Override
	public List<Anuncio> getAllAnunciosOrderByValoracion(String juego) {
		List<Anuncio> sol = (juego.equals("")) ? anuncioRepo.findAll() : anuncioRepo.findAllByJuego(juego);
		Collections.sort(sol, new Comparator<Anuncio>() {
			@Override
			public int compare(Anuncio o1, Anuncio o2) {
				Double mediaA1 = (double) o1.getAnunciante().getPuntuacion_total() / o1.getAnunciante().getNum_votaciones(), 
						mediaA2 = (double) o2.getAnunciante().getPuntuacion_total() / o2.getAnunciante().getNum_votaciones();
				return mediaA2.compareTo(mediaA1);
			}

		});
		return sol;
	}

	@Override
	public boolean terminarAnuncio(long id) {
		Anuncio anuncio = anuncioRepo.findById(id);

		if (anuncio != null) {
			anuncio.setEstado("finalizado");
			anuncioRepo.save(anuncio);
			return true;
		}

		return false;
	}

	@Override
	public boolean borrarAnuncio(long id) {
		Anuncio anuncio = anuncioRepo.findById(id);
		if (anuncio != null) {
			for (Participacion p : anuncio.getParticipacion()) {
				p.getUsuario().setEstado("libre");
				usuarioRepository.save(p.getUsuario());
				participacionRepo.delete(p);
			}
			anuncio.getAnunciante().setEstado("libre");
			usuarioRepository.save(anuncio.getAnunciante());
			anuncioRepo.delete(anuncio);
			return true;
		}

		return false;
	}

	@Override
	public boolean empezarAnuncio(long idAnuncio, long idUsuario) {
		Anuncio anuncio = anuncioRepo.findById(idAnuncio);
		if (anuncio != null && anuncio.getEstado().equals("pendiente")
				&& anuncio.getAnunciante().getId() == idUsuario) {
			anuncio.setEstado("empezado");
			anuncioRepo.save(anuncio);
			return true;
		}

		return false;
	}

	@Override
	public boolean checkEmpezado(long idAnuncio) {
		Anuncio a = anuncioRepo.findById(idAnuncio);
		if (a != null && a.getEstado().equals("empezado"))
			return true;
		return false;
	}

	@Override
	public boolean UsuarioEnAnuncio(long idAnuncio, long idUsuario) {
		Anuncio a = anuncioRepo.findById(idAnuncio);
		if (a != null) {
			if (a.getAnunciante().getId() == idUsuario)
				return true;
			else {
				List<Participacion> elems = a.getParticipacion();

				for (Participacion p : elems) {
					if (p.getUsuario().getId() == idUsuario && p.getEstado_solicitud().equals("aceptado"))
						return true;
				}
			}
		}
		return false;
	}

	public boolean valorarJugadores(List<Integer> listaNumEstrellas, List<Long> listaNumEstrellasId) {

		if (listaNumEstrellas != null && listaNumEstrellasId != null) {
			for (int i = 0; i < listaNumEstrellasId.size(); i++) {

				Long idUsuario = listaNumEstrellasId.get(i);
				Integer puntuacionNueva = listaNumEstrellas.get(i);

				Usuario u = usuarioRepository.findUsuarioById(idUsuario);
				u.setNum_votaciones(u.getNum_votaciones() + 1);
				u.setPuntuacion_total(u.getPuntuacion_total() + puntuacionNueva);

				usuarioRepository.save(u);

			}
			return true;
		}

		return false;
	}

}
