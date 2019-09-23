package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	@Autowired
	private UsuarioServiceI usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String usuarioRed = authentication.getName();
		Map<String, Object> additionalInformation = new HashMap<>();
		if (authentication.getName().equals("admin.encuestas")) {
			additionalInformation.put("codigo", "admin.encuestas");
		} else {
			Usuario usuario = usuarioService.findByUsuarioRed(usuarioRed);
			if (usuario != null) {
	    		additionalInformation.put("codigo", usuario.getCodigo());
			}
		}
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}
}
