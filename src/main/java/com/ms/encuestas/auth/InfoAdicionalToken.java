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
		Usuario usuario = usuarioService.findByCodigoWithPosicionFull(authentication.getName());
		Map<String, Object> additionalInformation = new HashMap<>();
		additionalInformation.put("nombre", usuario.getNombre());
		additionalInformation.put("nombreCompleto", usuario.getNombreCompleto());
		additionalInformation.put("posicionCodigo", usuario.getPosicion().getCodigo());
		additionalInformation.put("posicionNombre", usuario.getPosicion().getNombre());
		additionalInformation.put("areaNombre", usuario.getPosicion().getArea().getNombre());
		additionalInformation.put("centroId", usuario.getPosicion().getCentro().getId());
		additionalInformation.put("centroCodigo", usuario.getPosicion().getCentro().getCodigo());
		additionalInformation.put("centroNombre", usuario.getPosicion().getCentro().getNombre());
		additionalInformation.put("centroNivel", usuario.getPosicion().getCentro().getNivel());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		
		return accessToken;
	}
}
