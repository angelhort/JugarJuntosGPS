package com.jugarjuntos.Entities.UsuarioDetalles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.UsuarioRepository;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repo;

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Usuario usuario = repo.findUsuarioByCorreo(correo);

		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return new CustomUserDetails(usuario);
	}

}
