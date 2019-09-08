package com.ms.encuestas.auth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;
import com.ms.encuestas.services.segcen.ISegCenServicios;
import com.ms.encuestas.services.segcen.SegCenServicio;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	private UsuarioServiceI usuarioService;
	@Value("${app.usarAD}")
	private boolean usarAD;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info(String.format("Probando la autenticacion con usuario: %s", authentication.getName()));
		logger.info(String.format("Probando la autenticacion con contrasenia: %s", authentication.getCredentials().toString()));
		
    	String strPUsuario = authentication.getName();
    	String strPContrasenia = authentication.getCredentials().toString();
    	
		boolean error = false;
		Usuario usuario = null;
    	if (strPUsuario.equals("admin.encuestas") || !usarAD) {
    		if (strPUsuario.length()>5 && strPUsuario.substring(0, 5).toUpperCase().equals("EPPS\\")) {    		
    			usuario = usuarioService.findByUsuarioGenerales(strPUsuario);
    		} else {
    			usuario = usuarioService.findByUsuarioVida(strPUsuario);
    		}
    		if (usuario == null) {
    			error = true;
    		} else if (!passwordEncoder().matches(strPContrasenia,usuario.getContrasenha())) {
				error = true;  			
    		}
    	} else {
    		String strPCodigoAplicacion = "ENCPTO";
        	int intPMayor = 1;
        	int intPMinor = 0;
        	int intPVersion = 0;
        	String strPIP = "";
        	String strPHostName = "";
        	try {
        		SegCenServicio segCenServicio = new SegCenServicio();
            	ISegCenServicios segCenServicios = segCenServicio.getBasicHttpBindingISegCenServicios();
            	String response = segCenServicios.validarUsuarioApp(strPUsuario, strPContrasenia, strPCodigoAplicacion, intPMayor, intPMinor, intPVersion, strPIP, strPHostName);
            	logger.info(String.format("Respuesta del SegCen: '%s'.", response));
            	if (!response.equals("")) {
            		error = true;
            	}
            	
        		if (strPUsuario.length()>5 && strPUsuario.substring(0, 5).toUpperCase().equals("EPPS\\")) {
        			usuario = usuarioService.findByUsuarioGenerales(strPUsuario);
        		} else {
        			usuario = usuarioService.findByUsuarioVida(strPUsuario);
        		}
        		
        		if (usuario == null) {
        			error = true;
        		}
        	} catch(javax.xml.ws.WebServiceException e) {
        		logger.error("No se pudo conectar al servicio de directorio activo.");
        		throw new AuthenticationServiceException("No se pudo conectar al servicio de directorio activo.");
        	}        	
    	}
    	
    	if (error) {
    		String msj = String.format("El usuario '%s' no existe, está bloqueado o la contraseña es incorrecta.", strPUsuario);
    		logger.error(msj);
            throw new BadCredentialsException(msj + " Por favor contactar con Helpdesk.");
    	}
    	
		List<GrantedAuthority> authorities = usuarioService.getRolesByCodigo(usuario.getCodigo());
		if (authorities != null && !authorities.isEmpty()) {
			logger.info(String.format("El usuario '%s' se autenticó correctamente.", strPUsuario));
			return new UsernamePasswordAuthenticationToken(strPUsuario, strPContrasenia, authorities);			
		} else {
			String msj = String.format("No se pudieron cargar los roles del usuario '%s'.", strPUsuario);
			logger.info(msj);
    		throw new AuthenticationServiceException(msj + " Por favor contactar con Helpdesk.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
