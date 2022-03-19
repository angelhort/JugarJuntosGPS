package com.jugarjuntos.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "com.jugarjuntos.Entities.Usuario.findByparticipacion", query = "select obj from Usuario obj where :participacion MEMBER OF obj.participacion ")})
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	private String correo;
	

	private String password;
	

	private String discord;
	
	@OneToMany(mappedBy = "usuario")
	private List<Participacion> participacion;
	

	//Ocupado o libre
	private String estado;
	
	public Usuario() {
		super();
		this.participacion=new ArrayList<Participacion>();
	}
	

	public Usuario(String nombre, String correo, String password, String discord, String estado) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.discord = discord;
		this.estado = estado;
		this.participacion=new ArrayList<Participacion>();
	}


	public Integer getId() {
		return id;
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


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
