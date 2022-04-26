package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class Registro {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private TUsuario usuario = new TUsuario("TestIntegracion", "test@integracion.com", "pruebaTest", "testIntegracion");
	
	
	@Autowired
	SAUsuario saUsuario;
	
	@BeforeEach
	public void setup() throws Exception {
		
	}
	@Test
	public void checkRegistroUsuario() {
		assertNotNull(saUsuario.altaUsuario(usuario));
	}

}
