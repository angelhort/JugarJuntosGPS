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


//Demo que prueba el Servicio de Aplicación del prototipo

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)  // Se probarán las operaciones en orden alfabetico
class DemoTestApplicationTests {
	
	
	@Autowired
	SADemo sa;
	
	//Operacion que prueba si funciona la operacion de crear
	@Test
	void bcreateOneFineTest() {
		assertTrue(sa.addDemos("DemoData1", 20, "DemoDatatest"));
	}
	
	//Operacion que prueba si la operación de creación cuando hay un dato repetido en la clave primaria falla
	@Test
	void ccreateOneWrongTest() {
		assertFalse(sa.addDemos("DemoData1", 20, "DemoDatatest"));
	}
	
	//Operacion que prueba si se busca todos las filas de la tabla Demo
	@Test
	void dbuscarTodosTest() {
		assertNotNull(sa.getDemos());
	}
	
	
	//Operación que busca correctamente una fila de la tabla Demo 
	@Test
	void ebuscarUnoFineTest() {
		assertNotNull(sa.getOneDemo("DemoData1"));
	}
	
	//Operación de buscar una fila de la tabla Demo que falla dado que no existe esa fila
	@Test
	void fbuscarUnoWrongTest() {
		assertNull(sa.getOneDemo("TEsT??"));
	}
	
	//Operación de borrar correctamente una fila existente de la tabla Demo
	@Test
	void gdeleteOneFineTest() {
		assertTrue(sa.deleteDemo("DemoData1", 20, "DemoDatatest"));
	}
	
	//Operación de borrar con error dado que la fila de la tabla Demo no existe
	@Test
	void hdeleteOneWrongTest() {
		assertFalse(sa.deleteDemo("DemoData1", 20, "DemoDatatest"));
	}
	
	
	
	

}
