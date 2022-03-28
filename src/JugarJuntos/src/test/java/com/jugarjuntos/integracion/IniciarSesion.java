package com.jugarjuntos.integracion;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class IniciarSesion {
	
	@Autowired
	SAUsuario saUsuario;
	
	private TUsuario usuario = new TUsuario("TestIntegracion", "test@integracion.com", "pruebaTest", "testIntegracion");
	
	@BeforeAll
	public void setup() throws Exception {		
		saUsuario.altaUsuario(usuario);
	}
	
	@Test
	public void checkLoginUsuario() {
		assertNotNull(saUsuario.loginUsuario(usuario));
	}
}
