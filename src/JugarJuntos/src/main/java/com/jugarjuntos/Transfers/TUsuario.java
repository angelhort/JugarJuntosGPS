package com.jugarjuntos.Transfers;

import java.util.List;

public class TUsuario {

private long id;
	
	private String nombre;
	
	private String correo;
	

	private String password;
	

	private String discord;
	
	private List<TAnuncio> anuncios;
	
	
	private List<TParticipacion> participacion;
	

	//Ocupado o libre
	private String estado;


	public TUsuario(long id, String nombre, String correo, String password, String discord, List<TAnuncio> anuncios,
			List<TParticipacion> participacion, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.discord = discord;
		this.anuncios = anuncios;
		this.participacion = participacion;
		this.estado = estado;
	}


	public TUsuario() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDiscord() {
		return discord;
	}


	public void setDiscord(String discord) {
		this.discord = discord;
	}


	public List<TAnuncio> getAnuncios() {
		return anuncios;
	}


	public void setAnuncios(List<TAnuncio> anuncios) {
		this.anuncios = anuncios;
	}


	public List<TParticipacion> getParticipacion() {
		return participacion;
	}


	public void setParticipacion(List<TParticipacion> participacion) {
		this.participacion = participacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
