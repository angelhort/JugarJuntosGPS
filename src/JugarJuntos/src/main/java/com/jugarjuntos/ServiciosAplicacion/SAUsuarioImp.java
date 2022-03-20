package com.jugarjuntos.ServiciosAplicacion;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Transfers.TUsuario;

@Service
public class SAUsuarioImp implements SAUsuario{
	
	@Autowired
	private EntityManager em;

	@Override
	@Transactional
	public long altaUsuario(TUsuario tUsuario) {
		long id = -1;
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(tUsuario.getNombre());
		nuevoUsuario.setCorreo(tUsuario.getCorreo());
		nuevoUsuario.setPassword(tUsuario.getPassword());
		nuevoUsuario.setEstado("Libre");
		nuevoUsuario.setDiscord(tUsuario.getDiscord());
		em.persist(nuevoUsuario);
		id = nuevoUsuario.getId();
		return id; 
	}

}
