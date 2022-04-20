package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.ArrayList;
import java.util.HashMap;

import com.jugarjuntos.Transfers.TAnuncio;

public class SAUsuarioImpTest implements SAUsuarioTest{

	@Override
	public boolean mostrarValoracion(long id) {
		HashMap<Long, TAnuncio> map = new HashMap<>();
		map.put((long) 1, new TAnuncio("Prueba", 1, 2, "empezado", 2, new ArrayList<>()));
		TAnuncio anuncio = map.get(id);
		if (anuncio != null) {
			anuncio.setEstado("finalizado");
			return true;
		}
		return false;
	}

}
