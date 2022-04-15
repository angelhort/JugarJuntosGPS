package com.jugarjuntos.ServiciosAplicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.Repositories.UsuarioRepository;
import com.jugarjuntos.Transfers.TUsuario;

@Service
public class SAUsuarioImp implements SAUsuario {

	@Autowired
	private EntityManager em;

	@Autowired
	private UsuarioRepository repo;

	@Override
	@Transactional
	public long altaUsuario(TUsuario tUsuario) {
		long id = -1;
		Pattern patternCorreo = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		
		Pattern patternDiscord = Pattern.compile("[a-zA-Z0-9]+#[0-9]{4}+");
		
		Matcher matherDiscord = patternDiscord.matcher(tUsuario.getDiscord());
  
        Matcher matherCorreo = patternCorreo.matcher(tUsuario.getCorreo());
        
        //Comprobación de que el correo, el discord y la contraseña cumplan su formato y limitaciones
        
        Usuario usuarioExistente = repo.findUsuarioByCorreo(tUsuario.getCorreo());
        
       if(usuarioExistente == null) { 
        if (matherCorreo.find() == true && matherDiscord.find() == true && tUsuario.getPassword().length() <= 20 && tUsuario.getNombre().length() <= 20) {
			Usuario nuevoUsuario = new Usuario();                               //Se crea el usuario 
			if(tUsuario.getNombre() != null && tUsuario.getNombre().trim() != "" && tUsuario.getDiscord() != null && tUsuario.getDiscord().trim() != ""){
				
				//Se cambia los atributos del nuevo usuario creado
				nuevoUsuario.setNombre(tUsuario.getNombre());
				nuevoUsuario.setCorreo(tUsuario.getCorreo());
				nuevoUsuario.setPassword(encode_password(tUsuario.getPassword()));
				nuevoUsuario.setEstado("libre");
				nuevoUsuario.setDiscord(tUsuario.getDiscord());
				nuevoUsuario.setNum_votaciones(1);
				nuevoUsuario.setPuntuacion_total(1.0);
				
				em.persist(nuevoUsuario);			// Se persiste la entidad para que al hacer commit el EM mantega la entidad persistida 
				id = nuevoUsuario.getId();
			}
        } 
       }else id = -2; // Error por correo existente
		return id; 
       
	}

	public TUsuario loginUsuario(TUsuario tUsuario) {
		if (tUsuario.getCorreo() != null) {
			Pattern pattern = Pattern.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

			Matcher mather = pattern.matcher(tUsuario.getCorreo());

			if (mather.find() == false)
				return null;
		}

		Usuario bbdd_user = repo.findUsuarioByCorreo(tUsuario.getCorreo());
		if (bbdd_user != null) {
			TUsuario t = bbdd_user.entityToTransfer();
			if (check_password(tUsuario.getPassword(), bbdd_user.getPassword()))

				return t;
		}

		return null;

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

	@Override
	public List<Object> calcularMedia(long id) throws BusinessException {
		Usuario user = repo.findUsuarioById(id);
		List<Object> list = new ArrayList<Object>();
		if (user != null) {
			double media = user.getPuntuacion_total() / user.getNum_votaciones();
			list.add(String.format("%.2f", media));
			list.add( user.getNum_votaciones());
			return list;
		}
		else throw new BusinessException("No existe el usuario");
	}

}
