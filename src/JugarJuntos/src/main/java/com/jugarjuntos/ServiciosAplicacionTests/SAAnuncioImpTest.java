package com.jugarjuntos.ServiciosAplicacionTests;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Transfers.TAnuncio;

public class SAAnuncioImpTest implements SAAnuncioTest{

	public long crearUsuario(TAnuncio tAnuncio) {
		if(tAnuncio.getMax_personas() >0) {
			 Anuncio anuncio = new Anuncio();
			 anuncio.setJuego(tAnuncio.getJuego());
			 anuncio.setPersonas_actuales(tAnuncio.getPersonas_actuales());
			 anuncio.setMax_personas(tAnuncio.getMax_personas());
			 anuncio.setEstado(tAnuncio.getEstado());
		return 22;  // Devuelve un Dummy cualquiera
		}
		else return -1;
	}

	
	
	
	
}
