package com.jugarjuntos.Entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ParticipacionId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long usuario_id;
	
	private long anuncio_id;

	
	
	public ParticipacionId() {
		super();
	}

	public ParticipacionId(long usuario_id, long anuncio_id) {
		super();
		this.usuario_id = usuario_id;
		this.anuncio_id = anuncio_id;
	}

	public long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public long getAnuncio_id() {
		return anuncio_id;
	}

	public void setAnuncio_id(long anuncio_id) {
		this.anuncio_id = anuncio_id;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || getClass() != obj.getClass())
			return false;
		
		ParticipacionId other = (ParticipacionId) obj;
		
		if(anuncio_id != other.getAnuncio_id() || usuario_id != getUsuario_id())
			return false;
		
		return true;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		long res = 1;
		res=prime*res+anuncio_id;
		res=prime*res+usuario_id;
		
		return (int) res;
		
	}
	

}
