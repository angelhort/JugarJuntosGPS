package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import com.jugarjuntos.Entities.Anuncio;

public interface SAAnuncio {
	List<Anuncio> getAnunciosByNombreJuego(String juego);
	
	public Anuncio findAnuncioByUser(long id_user);
}
