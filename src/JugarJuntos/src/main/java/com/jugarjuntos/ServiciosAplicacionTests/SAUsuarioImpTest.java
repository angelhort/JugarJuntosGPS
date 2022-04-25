package com.jugarjuntos.ServiciosAplicacionTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;
import com.jugarjuntos.Transfers.TUsuario;

@Service
public class SAUsuarioImpTest implements SAUsuarioTest {

	@Override
	public boolean mostrarValoracion(long id) { // Seria el eqivalente de calcular la media
		HashMap<Long, TUsuario> map = new HashMap<>();
		map.put((long) 1, new TUsuario(1, "Prueba", "joselito@gmail.com", "contraseyabuenisima12", "jose#2442",
				new ArrayList<>(), new ArrayList<>(), "empezado"));
		List<Object> list = new ArrayList<Object>();
		TUsuario user = map.get(id);
		if (user != null) {
			double media = (double) user.getPuntuacion_total() / user.getNum_votaciones();
			list.add(String.format("%.2f", media));
			list.add(user.getNum_votaciones());
			return true;
		}
		return false;
	}

	@Override
	public long altaUsuario(TUsuario usuario) {
		if (usuario.getNombre() != null 
				&& usuario.getNombre().trim() != "" 
				&& usuario.getNombre().length() <= 20
				&& usuario.getPassword() != null 
				&& usuario.getPassword().trim() != ""
				&& usuario.getPassword().length() <= 20 
				&& usuario.getDiscord() != null
				&& usuario.getDiscord().trim() != "")
			return 1;
		else
			return -1;
	}

}
