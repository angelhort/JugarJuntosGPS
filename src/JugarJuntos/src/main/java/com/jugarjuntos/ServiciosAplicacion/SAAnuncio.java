package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Transfers.TAnuncio;

public interface SAAnuncio {
	long altaAnuncio(TAnuncio anuncio);
	
	List<Anuncio> getAnunciosByNombreJuego(String juego);
	
	public Anuncio findAnuncioByUser(long id_user);
	
	List<Anuncio> getAllAnuncios();
	
	public Anuncio getAnuncioByID(long id);
}
