package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.entitymanager.EntityManagerSingleton;

@Service
public class SAAnuncioImp implements SAAnuncio{
	
	@Autowired
	AnuncioRepository anuncioRepo;
	
	@Override
	public List<Anuncio> getAnunciosByNombreJuego(String juego) {
		return anuncioRepo.findAllByJuego(juego);
	}
	
	public Anuncio findAnuncioByUser(long id_user) {
		EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
		Usuario u_aux  = em.find(Usuario.class, id_user);
		
		List<Participacion> participaciones = u_aux.getParticipacion();
		
		for (Participacion p: participaciones) {
			if(p.getEstado_partida() == "en_lobby") {
				 Anuncio a = em.find(Anuncio.class, p.getId().getAnuncio_id());
				 em.close();
				 return a;
			}
				
		}
		
		em.close();
		return null;
		
	}

	@Override
	public List<Anuncio> getAllAnuncios() {
		return anuncioRepo.findAll();
	}

}
