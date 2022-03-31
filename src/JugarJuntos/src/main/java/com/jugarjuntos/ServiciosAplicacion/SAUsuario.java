package com.jugarjuntos.ServiciosAplicacion;


import com.jugarjuntos.Transfers.TUsuario;


public interface SAUsuario {

	public long altaUsuario(TUsuario tUsuario);
	
	public TUsuario loginUsuario(TUsuario tUsuario);
	
}
