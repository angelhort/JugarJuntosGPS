package com.jugarjuntos.ServiciosAplicacionTests;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.TabableView;

import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@Service
public class SAAnuncioImpTest implements SAAnuncioTest {

	public long altaAnuncio(TAnuncio tAnuncio) {
		if (tAnuncio.getMax_personas() > 0) {
			Anuncio anuncio = new Anuncio();
			anuncio.setJuego(tAnuncio.getJuego());
			anuncio.setPersonas_actuales(tAnuncio.getPersonas_actuales());
			anuncio.setMax_personas(tAnuncio.getMax_personas());
			anuncio.setEstado(tAnuncio.getEstado());
			return 22; // Devuelve un Dummy cualquiera
		} else
			return -1;
	}

	public TAnuncio getAnuncioByID(long id) {
		if (id < 0)
			return null;

		HashMap<Long, TAnuncio> map = new HashMap<>();
		map.put((long) 1, new TAnuncio("Buscaminas RTX Test", 0, 5, "pendiente", 1, new ArrayList<>()));
		map.put((long) 2, new TAnuncio("Buscaminas GOTY", 0, 5, "pendiente", 2, new ArrayList<>()));
		map.put((long) 3, new TAnuncio("Buscaminas DLC Origins", 0, 5, "pendiente", 3, new ArrayList<>()));
		map.put((long) 5, new TAnuncio("Buscaminas GTX ", 0, 5, "finalizado", 5, new ArrayList<>()));

		return map.get(id);
	}

	@Override
	public boolean borrarAnuncio(long id) {
		HashMap<Long, TAnuncio> map = new HashMap<>();
		map.put((long) 1, new TAnuncio("Prueba", 1, 2, "Pendiente", 2, new ArrayList<>()));
		TAnuncio anuncio = map.get(id);
		if (anuncio != null) {
			for (TParticipacion p : anuncio.getParticipacion()) {
				p.setEstado("libre");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean terminarAnuncio(long id) {
		HashMap<Long, TAnuncio> map = new HashMap<>();
		map.put((long) 1, new TAnuncio("Prueba", 1, 2, "empezado", 2, new ArrayList<>()));
		TAnuncio anuncio = map.get(id);
		if (anuncio != null) {
			anuncio.setEstado("finalizado");
			return true;
		}
		return false;

	}

	public boolean valorarJugadores(List<Integer> listaNumEstrellas, List<Long> listaNumEstrellasId) {
		Integer numVotaciones = 0;
		Double puntuacionTotal = 0.0;

		if (listaNumEstrellas != null && listaNumEstrellasId != null) {
			for (int i = 0; i < listaNumEstrellasId.size(); i++) {
				numVotaciones += i;
				puntuacionTotal += i;
			}
			
			return true;
		}
		return false;
	}
	public List<TAnuncio> getAllAnuncios(){
		List<TAnuncio> anuncios = new ArrayList<>();
		return anuncios;
	}
	
	public List<TAnuncio> getAllAnunciosOrderByTime(String juego) {
		TAnuncio juego1 = new TAnuncio();
		TAnuncio juego2 = new TAnuncio();
		juego1.setJuego(juego);
		juego2.setJuego(juego);
		juego1.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		juego2.setFecha_creacion(Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		List<TAnuncio> array = new ArrayList<TAnuncio>();
		array.add(juego1);
		array.add(juego2);
		List<TAnuncio> sol = (juego.equals("")) ? array : array;
		Collections.sort(sol, new Comparator<TAnuncio>() {
			@Override
			public int compare(TAnuncio o1, TAnuncio o2) {
				return o2.getFecha_creacion().compareTo(o1.getFecha_creacion());
			}

		});
		return sol;
	}

	@Override
	public boolean empezarAnuncio(long idAnuncio, long idUsuario) {
		TAnuncio anun = getAnuncioByID(idAnuncio);
		TUsuario usu = getAnuncianteById(idUsuario);
		
		if (anun != null && anun.getEstado().equals("pendiente")
				&&usu != null && usu.getId() == idUsuario) {
			anun.setEstado("empezado");
			
			return true;
		}

		return false;
	}

	public TUsuario getAnuncianteById(long id) {
		if (id < 0)
			return null;
		

		HashMap<Long, TUsuario> map = new HashMap<>();
		map.put((long) 1, new TUsuario(1,"Jorge","hola@gmail.com","1234","Djorge","libre"));
		map.put((long) 2, new TUsuario(2,"Ruben","ruben@gmail.com","1223","DRuben","libre"));
		map.put((long) 3, new TUsuario(3,"Jose","jose@gmail.com","12233","DJose","libre"));

		return map.get(id);
	}



}
