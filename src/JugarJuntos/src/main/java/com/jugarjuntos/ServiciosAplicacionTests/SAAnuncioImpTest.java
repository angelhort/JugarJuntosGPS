package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@Service
public class SAAnuncioImpTest implements SAAnuncioTest {

	public long crearUsuario(TAnuncio tAnuncio) {
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
	public boolean empezarAnuncio(long idAnuncio, long idUsuario) {
		TAnuncio anun = getAnuncioByID(idAnuncio);
		
		if (anun != null && anun.getEstado().equals("pendiente")
				&& getAnuncianteById(idUsuario).getId() == idUsuario) {
			anun.setEstado("empezado");
			
			return true;
		}

		return false;
	}

	public TUsuario getAnuncianteById(long id) {
		if (id < 0)
			return null;
		

		HashMap<Long, TUsuario> map = new HashMap<>();
		map.put((long) 1, new TUsuario("Jorge","hola@gmail.com","1234","Djorge"));
		map.put((long) 2, new TUsuario("Ruben","ruben@gmail.com","1223","DRuben"));
		map.put((long) 3, new TUsuario("Jose","jose@gmail.com","12233","DJose"));

		return map.get(id);
	}



}
