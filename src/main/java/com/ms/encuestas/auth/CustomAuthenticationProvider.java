package com.ms.encuestas.auth;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info(String.format("probando la autenticacion con usuario: %s", authentication.getName()));
		logger.info(String.format("probando la autenticacion con contrasenia: %s", authentication.getCredentials().toString()));
		
    	String strPUsuario = authentication.getName();
    	String strPContrasenia = authentication.getCredentials().toString();
    	
		boolean error = false;
    	if (strPUsuario.equals("admin.encuestas")) {
    		Usuario usuario = usuarioService.findByCodigo(strPUsuario);
    		logger.info(String.format("probando la autenticacion con usuario: %s", authentication.getName()));
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
        		System.out.println("aa");
        		SegCenServicio segCenServicio = new SegCenServicio();
        		System.out.println("bb");
            	ISegCenServicios segCenServicios = segCenServicio.getBasicHttpBindingISegCenServicios();
            	System.out.println("cc");
            	String response = segCenServicios.validarUsuarioApp(strPUsuario, strPContrasenia, strPCodigoAplicacion, intPMayor, intPMinor, intPVersion, strPIP, strPHostName);
            	logger.info(String.format("Respuesta del SegCen: %s", response));
            	if (!response.equals("")) {
            		error = true;
            	}
        	} catch(javax.xml.ws.WebServiceException e) {
        		logger.error("No se pudo conectar al servicio de SegCen.");
        		throw new AuthenticationServiceException("SEGCEN");
        	}        	
    	}
    	
    	if (error) {
    		logger.error("El usuario no existe o está bloqueado o la contraseña es incorrecta.");
            throw new BadCredentialsException("El usuario no existe o está bloqueado o la contraseña es incorrecta. Por favor contactar con Helpdesk.");
    	}
    	
		logger.info(String.format("El usuario '%s' se autenticó correctamente.", strPUsuario));
		return new UsernamePasswordAuthenticationToken(strPUsuario, strPContrasenia, new ArrayList<GrantedAuthority>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
