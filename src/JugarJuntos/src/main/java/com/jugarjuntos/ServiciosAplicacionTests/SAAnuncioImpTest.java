package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.ArrayList;
import java.util.HashMap;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;

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
		map.put((long) 1, new TAnuncio("Buscaminas RTX Test", 0, 5, "Pendiente", 1, new ArrayList<>()));
		map.put((long) 2, new TAnuncio("Buscaminas GOTY", 0, 5, "Pendiente", 2, new ArrayList<>()));
		map.put((long) 3, new TAnuncio("Buscaminas DLC Origins", 0, 5, "Pendiente", 3, new ArrayList<>()));

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

}
