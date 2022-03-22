package com.jugarjuntos.anuncio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;

import java.util.ArrayList;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class AnuncioTests {

	@Autowired
	SAAnuncio sAAnuncio;
	TAnuncio tAnuncio = new TAnuncio("Elden Ring", 1, 3, "en_lobby", 1, new ArrayList<TParticipacion>());

	// Operacion que prueba si funciona la operacion de crear anuncio
	@Test
	void bcreateOneFineTest() {
		assertNotEquals(-1, sAAnuncio.altaAnuncio(tAnuncio));
	}

	// Operacion que prueba si cuando hay un dato repetido
	// en la clave primaria falla en la operación de creación anuncio
	@Test
	void ccreateOneWrongTest() {
		assertEquals(-1, sAAnuncio.altaAnuncio(tAnuncio));
	}

	// Operacion que prueba si se buscan todos los anuncios
	@Test
	void ebuscarTodosTest() {
		assertNotNull(sAAnuncio.getAllAnuncios());
	}

	// Operación que busca un anuncio por el id del usuario
	@Test
	void gbuscarUnoFineTest() {
		// findAnuncioByUser(long id_user)
		assertNotNull(sAAnuncio.findAnuncioByUser(1));
	}
/*  No implementado todavía borrar
	// Operación de borrar correctamente una fila existente de la tabla Anuncio
	@Test
	void hdeleteOneFineTest() {
		assertNotEquals(-1, sAAnuncio.bajaAnuncio(tAnuncio));
	}
	// Operación de borrar con error dado que la fila de la tabla Anuncio no existe
	@Test
	void ideleteOneWrongTest() {
		assertEquals(-1, sAAnuncio.bajaAnuncio(tAnuncio));
	}
*/
}