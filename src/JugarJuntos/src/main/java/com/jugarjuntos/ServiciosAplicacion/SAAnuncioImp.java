package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.entitymanager.EntityManagerSingleton;

@Service
public class SAAnuncioImp implements SAAnuncio{
	
	@Autowired
	AnuncioRepository anuncioRepo;
	
	@Autowired EntityManager em;
	
	
	@Override
	@Transactional
	public long altaAnuncio(TAnuncio tAnuncio) {
		long id = -1;
//		EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
		 Anuncio anuncio = new Anuncio();
		 anuncio.setJuego(tAnuncio.getJuego());
		 anuncio.setPersonas_actuales(tAnuncio.getPersonas_actuales());
		 anuncio.setMax_personas(tAnuncio.getMax_personas());
		 anuncio.setEstado(tAnuncio.getEstado());
		 em.persist(anuncio);
		 id = anuncio.getId();
//		 em.close();
		return id;
		
	}
	
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
		
//		em.close();
		return null;
		
	}


	@Override
	public List<Anuncio> getAllAnuncios() {
		return anuncioRepo.findAll();
	}


}
