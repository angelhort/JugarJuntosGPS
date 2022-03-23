package com.jugarjuntos.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jugarjuntos.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value = "SELECT * FROM usuario WHERE id = ?1", nativeQuery = true)
	public Usuario findUsuarioById(long id);
	
	@Query(value = "UPDATE usuario SET estado = 'aceptado' WHERE id = ?1")
	public void cambiarEstadoAceptado(long id);
}