package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Repositories.AnuncioRepository;

@Service
public class SAAnuncioImp implements SAAnuncio{
	
	@Autowired
	AnuncioRepository anuncioRepo;
	
	@Override
	public List<Anuncio> getAnunciosByNombreJuego(String juego) {
		return anuncioRepo.findAllByJuego(juego);
	}

}
