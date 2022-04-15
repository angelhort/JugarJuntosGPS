package com.jugarjuntos.ServiciosAplicacion;


import java.util.List;

import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Transfers.TUsuario;


public interface SAUsuario {

	public long altaUsuario(TUsuario tUsuario);
	
	public TUsuario loginUsuario(TUsuario tUsuario);
	
	public List<Object> calcularMedia(long id)throws BusinessException;
}
