package com.jugarjuntos.ServiciosAplicacionTests;

import com.jugarjuntos.Transfers.TAnuncio;
import java.util.List;

public interface SAAnuncioTest {

	public long crearUsuario(TAnuncio tAnuncio);

	public TAnuncio getAnuncioByID(long id); // Devuelve un anuncio solo con los ids 1,2 y 3.

	public boolean borrarAnuncio(long id);

	public boolean terminarAnuncio(long id);

	public boolean valorarJugadores(List<Integer> listaNumEstrellas, List<Long> listaNumEstrellasId);
	
	public boolean empezarAnuncio(long idAnuncio, long idUsuario);
	
	public List<TAnuncio> getAllAnunciosOrderByTime(String juego);
}
