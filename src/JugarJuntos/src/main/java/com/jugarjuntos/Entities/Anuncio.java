package com.jugarjuntos.Entities;

import javax.persistence.*;

@Entity
public class Anuncio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String juego;
	
	private int personas_actuales;
	
	private int max_personas;

	//Pendiente o finalizado
	private String estado;
	


	public Anuncio() {
		super();
	}

	

	public Anuncio(String juego, int personas_actuales, int max_personas, String estado) {
		super();
		this.juego = juego;
		this.personas_actuales = personas_actuales;
		this.max_personas = max_personas;
		this.estado = estado;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJuego() {
		return juego;
	}

	public void setJuego(String juego) {
		this.juego = juego;
	}
	
	public int getPersonas_actuales() {
		return personas_actuales;
	}

	public void setPersonas_actuales(int personas_actuales) {
		this.personas_actuales = personas_actuales;
	}

	public int getMax_personas() {
		return max_personas;
	}

	public void setMax_personas(int max_personas) {
		this.max_personas = max_personas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}