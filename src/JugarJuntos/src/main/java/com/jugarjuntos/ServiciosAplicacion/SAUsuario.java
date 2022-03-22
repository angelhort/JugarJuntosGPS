package com.jugarjuntos.ServiciosAplicacion;


import com.jugarjuntos.Transfers.TUsuario;


public interface SAUsuario {

	public long altaUsuario(TUsuario tUsuario);
	
	public Boolean loginUsuario(TUsuario tUsuario);
	
}
