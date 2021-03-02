package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;

public class CustomTokenEnhancer implements TokenEnhancer {
	private final Logger logger = LoggerFactory.getLogger(TokenEnhancer.class);
	@Autowired
	private UsuarioServiceI usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String usuarioRed = authentication.getName();
		Map<String, Object> additionalInformation = new HashMap<>();
		if (authentication.getName().equals("admin.encuestas")) {
			additionalInformation.put("codigo", usuarioRed);
			additionalInformation.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			logger.info(String.format("Se encontró el usuario de red '%s'.", usuarioRed));
		} else {
			Usuario usuario = usuarioService.findByUsuarioRed(usuarioRed);
			if (usuario != null) {
	    		additionalInformation.put("codigo", usuario.getCodigo());
	    		additionalInformation.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
	    		logger.info(String.format("Se encontró la matrícula '%s' para el usuario de red '%s'.", usuario.getCodigo(), usuarioRed));
			}
		}
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}

}
