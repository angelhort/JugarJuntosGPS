package com.demoTest;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demoTest.AplicationServices.Anuncios.SADemo;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class DemoTestApplicationTests {
	
	@Autowired
	SADemo sa;
	

	@Test
	void acontextLoads() {
		
	}
	
	@Test
	void bcreateOneFineTest() {
		assertTrue(sa.addDemos("DemoData1", 20, "DemoDatatest"));
	}
	@Test
	void ccreateOneWrongTest() {
		assertFalse(sa.addDemos("DemoData1", 20, "DemoDatatest"));
	}
	@Test
	void dbuscarTodosTest() {
		assertNotNull(sa.getDemos());
	}
	
	@Test
	void ebuscarUnoFineTest() {
		assertNotNull(sa.getOneDemo("DemoData1"));
	}
	@Test
	void fbuscarUnoWrongTest() {
		assertNull(sa.getOneDemo("TEsT??"));
	}
	
	@Test
	void gdeleteOneFineTest() {
		assertTrue(sa.deleteDemo("DemoData1", 20, "DemoDatatest"));
	}
	@Test
	void hdeleteOneWrongTest() {
		assertFalse(sa.deleteDemo("DemoData1", 20, "DemoDatatest"));
	}
	
	
	
	

}
