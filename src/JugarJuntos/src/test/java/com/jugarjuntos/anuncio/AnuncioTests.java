package com.jugarjuntos.anuncio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class AnuncioTests {

	@Autowired
	SAAnuncio sAAnuncio;
	TAnuncio tAnuncio = new TAnuncio("Elden Ring", 1, 3, "en_lobby", 1, new List<TParticipacion>());

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

	// Operacion que prueba si se busca los anuncios por juego
	@Test
	void dbuscarTodosNombreJuegoTest() {
		assertNotNull(sAAnuncio.getAnunciosByNombreJuego("Elden Ring"));
	}

	// Operacion que prueba si se buscan todos los anuncios
	@Test
	void ebuscarTodosTest() {
		assertNotNull(sAAnuncio.getAllAnuncios());
	}

	// Operación de busca un anuncio que no existe
	@Test
	void fbuscarUnoWrongTest() {
		assertNull(sAAnuncio.getAnunciosByNombreJuego("Paco juega a la petanca 2: ultimate version"));
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
