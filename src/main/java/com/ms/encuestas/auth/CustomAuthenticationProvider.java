package com.ms.encuestas.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.SegCenServicioInjI;
import com.ms.encuestas.services.UsuarioServiceI;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	private SegCenServicioInjI segCenService;
	@Autowired
	private UsuarioServiceI usuarioService;
	@Value("${app.usarAD}")
	private boolean usarAD;
	@Value("${app.segCen.url}")
	private String segCenUrl;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	String strPUsuario = authentication.getName();
    	String strPContrasenia = authentication.getCredentials().toString();
    	
		boolean error = false;
		Usuario usuario = null;
    	if (strPUsuario.equals("admin.encuestas") || !usarAD) {
    		usuario = usuarioService.findByUsuarioRed(strPUsuario);
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
        	if (segCenService.getSegCenServicios() != null) {
        		String response = segCenService.validarUsuarioApp(strPUsuario, strPContrasenia, strPCodigoAplicacion, intPMayor, intPMinor, intPVersion, strPIP, strPHostName);
            	if (response.equals("")) {
            		logger.info(String.format("Respuesta del SegCen: '%s'.", response));
            	} else {
            		logger.error(String.format("Respuesta del SegCen: '%s'.", response));
            		error = true;
            	}
            	
            	usuario = usuarioService.findByUsuarioRed(strPUsuario);        		
        		if (usuario == null) {
        			error = true;
        		}
        	} else {
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
