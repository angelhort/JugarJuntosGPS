package com.jugarjuntos.anuncio;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.ServiciosAplicacionTests.SAAnuncioTest;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class) // Se probarán las operaciones en orden alfabetico
public class ValoracionUsuarioTest {

    public static final List<Integer> listaNumEstrellas = new ArrayList<>(Arrays.asList(1, 2, 3));
    public static final List<Long> listaNumEstrellasId = new ArrayList<>(Arrays.asList(1L, 2L, 3L));

    @Autowired
	SAAnuncioTest sAAnuncioTest;

	@Test
	void valorarJugadoresOKTest() {
        assertEquals(sAAnuncioTest.valorarJugadores(listaNumEstrellas, listaNumEstrellasId), true);
    }

    @Test
	void listaNumEstrellasNullTest() {
        assertEquals(sAAnuncioTest.valorarJugadores(null, listaNumEstrellasId), false);
	}

    @Test
	void listaNumEstrellasIdNullTest() {
        assertEquals(sAAnuncioTest.valorarJugadores(listaNumEstrellas, null), false);
	}

    @Test
    void allListNullTest() {
        assertEquals(sAAnuncioTest.valorarJugadores(null, null), false);
    }
}