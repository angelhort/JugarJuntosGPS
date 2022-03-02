package com.demoTest.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ANUNCIOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anuncio implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PROPIETARIO")
	@NonNull
	private String propietario;
	
	@Column(name = "DESCRIPCION")
	@NonNull
	private String descripcion;
	
	@Column(name = "JUEGO")
	@NonNull
	private String juego;
}
