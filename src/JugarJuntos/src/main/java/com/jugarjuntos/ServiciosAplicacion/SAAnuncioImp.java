package com.jugarjuntos.ServiciosAplicacion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetails;
import com.jugarjuntos.Repositories.AnuncioRepository;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.entitymanager.EntityManagerSingleton;

@Service
public class SAAnuncioImp implements SAAnuncio{
	
	@Autowired
	AnuncioRepository anuncioRepo;
	
	@Autowired EntityManager em;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@Override
	@Transactional
	public long altaAnuncio(TAnuncio tAnuncio) {
		long id = -1;
//		EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
		if(tAnuncio.getMax_personas() >0) {
		 Anuncio anuncio = new Anuncio();
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 Long idUsuario = -1L;
			try {
				idUsuario = ((CustomUserDetails) principal).getId();
				anuncio.setAnunciante(usuarioRepository.findUsuarioById(idUsuario));
			}catch(Exception e) {
				
			}
			
		 anuncio.setJuego(tAnuncio.getJuego());
		 anuncio.setPersonas_actuales(tAnuncio.getPersonas_actuales());
		 anuncio.setMax_personas(tAnuncio.getMax_personas());
		 anuncio.setEstado(tAnuncio.getEstado());
		 anuncioRepo.save(anuncio);
		 id = anuncio.getId();
//		 em.close();
		 
		 
		}
		
		return id;
		
	}
	
	@Override
	public List<Anuncio> getAnunciosByNombreJuego(String juego) {
		
		//TypedQuery<Anuncio> query = em.createNamedQuery("AnuncioBuscarPorJuego",Anuncio.class);
		//query.setParameter("juego", "%" + juego + "%");
		//List<Anuncio> a = query.getResultList();
		
		return anuncioRepo.findAllByJuego(juego);
		
		
		
	}
	
	public Anuncio findAnuncioByUser(long id_user) {
		Usuario usuario  = em.find(Usuario.class, id_user);
		/*
		return usuario.getParticipacion().stream()
				.filter(p -> p.getEstado_partida().equals("en_lobby"))
				.map(p -> em.find(Anuncio.class, p.getId().getAnuncio_id()))
				.findFirst()
				.orElse(null);*/
		List<Participacion> participaciones = usuario.getParticipacion();
		
		for (Participacion p: participaciones) {
			if(p.getEstado_partida() == "en_lobby") {
				 Anuncio a = em.find(Anuncio.class, p.getId().getAnuncio_id());
//				 em.close();
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

	@Override
	public Anuncio getAnuncioByID(long id) {
		return anuncioRepo.findById(id);
	}


}
