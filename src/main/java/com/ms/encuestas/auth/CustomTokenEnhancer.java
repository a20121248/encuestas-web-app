package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;

public class CustomTokenEnhancer implements TokenEnhancer {
	@Autowired
	private UsuarioServiceI usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String usuarioRed = authentication.getName();
		Map<String, Object> additionalInformation = new HashMap<>();
		if (authentication.getName().equals("admin.encuestas")) {
			additionalInformation.put("codigo", authentication.getName());
			additionalInformation.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		} else {
			Usuario usuario = usuarioService.findByUsuarioRed(usuarioRed);
			if (usuario != null) {
	    		additionalInformation.put("codigo", authentication.getName());
	    		additionalInformation.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
			}
		}
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}

}
