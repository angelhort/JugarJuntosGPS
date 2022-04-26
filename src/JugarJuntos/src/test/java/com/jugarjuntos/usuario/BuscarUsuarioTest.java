package com.jugarjuntos.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.ServiciosAplicacionTests.SAUsuarioImpTest;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BuscarUsuarioTest {
	
	@Autowired
	SAUsuarioImpTest sa;
	
	private static Usuario usr;
	
	private static long id_usr;
	
	@Test
	void aSetValues() {
		id_usr = 1;
		assertEquals(true, true);
	}
	
	@Test
	void bBuscarBien() {
		usr = sa.getUsuarioByID(id_usr);
		
		assertEquals(usr.getId(), id_usr);
	}

}
