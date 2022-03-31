package com.jugarjuntos.ServiciosAplicacion;

public abstract class FactoriaSA {
	
	private static FactoriaSA instancia;

	public static FactoriaSA getInstance() {
		if (instancia == null)
			instancia = new FactoriaSAImp();
		return instancia;
	}
	
	public abstract SAAnuncio getSAAnuncio();
	
	public abstract SAUsuario getSAUsuario();
}
