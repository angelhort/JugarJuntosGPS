package com.jugarjuntos.ServiciosAplicacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Transfers.TUsuario;


@Service
public class SAUsuarioImp implements SAUsuario{
	
	@Autowired
	private EntityManager em;

	@Override
	@Transactional
	public long altaUsuario(TUsuario tUsuario) {
		long id = -1;
		Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
      
 
        Matcher mather = pattern.matcher(tUsuario.getCorreo());
 
        if (mather.find() == true) {
        
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(tUsuario.getNombre());
		nuevoUsuario.setCorreo(tUsuario.getCorreo());
		nuevoUsuario.setPassword(encode_password(tUsuario.getPassword()));
		nuevoUsuario.setEstado("Libre");
		nuevoUsuario.setDiscord(tUsuario.getDiscord());
		em.persist(nuevoUsuario);
		id = nuevoUsuario.getId();
        }
		return id; 
       
	}
	
	public Boolean loginUsuario(TUsuario tUsuario) {
		
		if(tUsuario.getCorreo() != null) {
			Pattern pattern = Pattern
	                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	 
	      
	 
	        Matcher mather = pattern.matcher(tUsuario.getCorreo());
	        
	        if (mather.find() == false) return false;
		}
		
		TUsuario bbdd_user = em.find(Usuario.class, tUsuario).entityToTransfer();
		
		return (check_password(tUsuario.getPassword(), bbdd_user.getPassword()));
		
	}
	
	private String encode_password(String plain_text_password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(plain_text_password);
	    
	    return encodedPassword;
	}
	
	private Boolean check_password(String plain_text_password, String encoded_password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    return passwordEncoder.matches(plain_text_password, encoded_password);
	}

}
