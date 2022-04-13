package com.jugarjuntos.ServiciosAplicacionTests;

import com.jugarjuntos.Transfers.TAnuncio;

public interface SAAnuncioTest {

	public long crearUsuario(TAnuncio tAnuncio);

	public TAnuncio getAnuncioByID(long id); // Devuelve un anuncio solo con los ids 1,2 y 3.
	
	public boolean borrarAnuncio(long id);

}
