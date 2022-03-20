package com.jugarjuntos.Transfers;

import java.util.List;


public class TAnuncio {

	private long id;
	
	private String juego;
	
	private int personas_actuales;
	
	private int max_personas;

	//Pendiente o finalizado
	private String estado;
	

	private int id_usuario;
	
	private List<TParticipacion> participacion;

	public TAnuncio(String juego, int personas_actuales, int max_personas, String estado, int id_usuario,
			List<TParticipacion> participacion) {
		super();
		this.juego = juego;
		this.personas_actuales = personas_actuales;
		this.max_personas = max_personas;
		this.estado = estado;
		this.id_usuario = id_usuario;
		this.participacion = participacion;
	}

	public TAnuncio() {
		super();
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getId_Usuario() {
		return id_usuario;
	}

	public void setId_Usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public List<TParticipacion> getParticipacion() {
		return participacion;
	}

	public void setParticipacion(List<TParticipacion> participacion) {
		this.participacion = participacion;
	}
	
	
	
	

	
	
	
}
