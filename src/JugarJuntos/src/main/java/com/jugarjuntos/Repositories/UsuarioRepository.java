package com.jugarjuntos.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jugarjuntos.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
