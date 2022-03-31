package com.jugarjuntos.ServiciosAplicacion;

public class FactoriaSAImp extends FactoriaSA {
	
	public SAAnuncio getSAAnuncio() {
		return new SAAnuncioImp();
	}

	
	public SAUsuario getSAUsuario() {
		return new SAUsuarioImp();
	}

}
