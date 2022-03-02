package com.demoTest.AplicationServices.Anuncios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demoTest.Entities.Anuncio;
import com.demoTest.Repositories.AnunciosRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SAAnuncioImp implements SAAnuncios{
	
	private final AnunciosRepository anunciosRepo;
	@Override
	public List<Anuncio> getAnuncios() {
		return anunciosRepo.findAll();
	}

}
