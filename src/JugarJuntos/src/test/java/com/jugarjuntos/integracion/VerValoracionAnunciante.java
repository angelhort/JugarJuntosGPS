package com.jugarjuntos.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jugarjuntos.JugarJuntosApplication;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TUsuario;

@SpringBootTest(classes = JugarJuntosApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class VerValoracionAnunciante {
		
		@Autowired
		SAAnuncio sAAnuncio;
		
		@Autowired
		SAUsuario sAUsuario;
		
		private static long id_usr1;
		
		private static TUsuario usr1;
		
		private static double media;
		
		private static int num_val;
		
		@Autowired
		UsuarioRepository userRepo;
		
		@Test
		void aSetValues() {
			
			usr1 = new TUsuario();
			
			usr1.setCorreo("usrtestValor@test.com");
			
			usr1.setNombre("testValor");
			
			usr1.setPassword("pswdusr1");
			
			usr1.setDiscord("discordusr1#6457");
			
			usr1.setEstado("Libre");
			
			//--------------------------
			
			id_usr1 = sAUsuario.altaUsuario(usr1);
			
			usr1.setId(id_usr1);
			
			//--------------------------
			
			media=2.5;
			num_val=1;
			
			assertEquals(true, true);
		}
		
		@Test
		public void bVerValoracion1Bien() {
			try {
				assertEquals(String.format("%.2f", media), sAUsuario.calcularMedia(id_usr1).get(0));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void cVerValoracion2Bien() {
			try {
				assertEquals(num_val, (int) sAUsuario.calcularMedia(id_usr1).get(1));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void dVerValoracionMal1() {
			List<Integer> num_estrellas = new ArrayList<Integer>();
			num_estrellas.add(3);
			List<Long> lista_ids = new ArrayList<Long>();
			lista_ids.add(id_usr1);
			sAAnuncio.valorarJugadores(num_estrellas, lista_ids);
			try {
				assertNotEquals(String.format("%.2f", media), sAUsuario.calcularMedia(id_usr1).get(0));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void eVerValoracionMal2() {
			try {
				assertNotEquals(num_val, (int) sAUsuario.calcularMedia(id_usr1).get(1));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void fVerValoracionBien1() {
			media+=3;
			num_val++;
			media=media/num_val;
			
			try {
				assertEquals(String.format("%.2f", media), sAUsuario.calcularMedia(id_usr1).get(0));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void gVerValoracionBien2() {
			try {
				assertEquals(num_val, (int) sAUsuario.calcularMedia(id_usr1).get(1));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void hendAll() {//Deja la base de datos como estaba
			userRepo.deleteById(id_usr1);
			
		}
	}
