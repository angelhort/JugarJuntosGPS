package com.jugarjuntos.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;

@Entity
@NamedQueries({
	@NamedQuery(name = "com.jugarjuntos.Entities.Anuncio.findByparticipacion", query = "select obj from Anuncio obj where :participacion MEMBER OF obj.participacion "),
	@NamedQuery(name = "AnuncioBuscarPorJuego", query = "select obj from Anuncio obj where obj.juego LIKE :juego")})
public class Anuncio implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String juego;
	
	private int personas_actuales;
	
	private int max_personas;

	//Pendiente o finalizado
	private String estado;
	
	@ManyToOne
	private Usuario anunciante;
	
	@OneToMany(mappedBy = "anuncio")
	private List<Participacion> participacion;

	public Anuncio() {
		super();
		this.participacion=new ArrayList<Participacion>();
		this.estado = "Pendiente";
	}

	public Anuncio(String juego, int personas_actuales, int max_personas, String estado) {
		super();
		this.juego = juego;
		this.personas_actuales = personas_actuales;
		this.max_personas = max_personas;
		if(estado.equals("Pendiente") || estado.equals("Finalizado")) this.estado = estado;
		else this.estado="Pendiente";
		this.participacion=new ArrayList<Participacion>();
	}

	public List<Participacion> getParticipacion() {
		return participacion;
	}

	public void setParticipacion(List<Participacion> participacion) {
		this.participacion = participacion;
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
	
	
	public Usuario getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Usuario anunciante) {
		this.anunciante = anunciante;
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
		if(estado.equals("Pendiente") || estado.equals("Finalizado")) this.estado = estado;
		else this.estado="Pendiente";
	}
	
	public TAnuncio entityToTransfer() {
		TAnuncio t = new TAnuncio();
		t.setId(this.id);
		t.setPersonas_actuales(this.personas_actuales);
		t.setMax_personas(this.max_personas);
		t.setEstado(this.estado);
		t.setId_Usuario(this.anunciante.getId());
		t.setJuego(this.juego);
		List<TParticipacion> parti  = new ArrayList<>();
		for(Participacion p: this.getParticipacion()) {
			parti.add(p.entityToTransfer());
		}
		t.setParticipacion(parti);
		
		return t;
		
		
		
	}

}