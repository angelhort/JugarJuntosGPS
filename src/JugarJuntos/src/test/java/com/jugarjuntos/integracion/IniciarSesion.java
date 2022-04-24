package com.jugarjuntos.integracion;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class IniciarSesion {
	
	@Autowired
	SAUsuario saUsuario;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	private TUsuario usuario = new TUsuario("TestIntegracion", "test@integracion.com", "pruebaTest", "testIntegracion");
	private long idUsuario;
	
	@Test
	public void checkLoginUsuario() {
		idUsuario = saUsuario.altaUsuario(usuario);
		assertNotNull(saUsuario.loginUsuario(usuario));
		usuarioRepository.delete(usuarioRepository.findUsuarioById(idUsuario));
	}
}
