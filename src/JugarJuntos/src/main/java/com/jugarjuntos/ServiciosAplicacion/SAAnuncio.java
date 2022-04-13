package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Transfers.TAnuncio;

public interface SAAnuncio {
	public long altaAnuncio(TAnuncio anuncio);
	
	public List<Anuncio> getAnunciosByNombreJuego(String juego);
	
	public Anuncio findAnuncioByUser(long id_user);
	
	public List<Anuncio> getAllAnuncios();
	
	public List<Anuncio> getAllAnunciosOrderByTime();
	
	public Anuncio getAnuncioByID(long id);
	
	public boolean terminarAnuncio(long id);
	
	public boolean borrarAnuncio(long id);
}
