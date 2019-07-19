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
		Usuario usuario = usuarioService.findByCodigo(authentication.getName());
		Map<String, Object> additionalInformation = new HashMap<>();
		additionalInformation.put("info adicional", "Hola que tal: !".concat(authentication.getName()));
		additionalInformation.put("nombre usuario ", usuario.getCodigo() + ": " + usuario.getNombre());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		
		return accessToken;
	}
}
