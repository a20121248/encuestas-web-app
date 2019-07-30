package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.ProcesoServiceI;
import com.ms.encuestas.services.UsuarioServiceI;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	@Autowired
	private UsuarioServiceI usuarioService;
	@Autowired
	private ProcesoServiceI procesoService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Proceso proceso = procesoService.getCurrentProceso();
		Usuario usuario = usuarioService.findByCodigo(authentication.getName());
		Map<String, Object> additionalInformation = new HashMap<>();
		String nombreCompleto = usuario.getNombreCompleto();
		additionalInformation.put("nombre", nombreCompleto.substring(0, nombreCompleto.indexOf(' ')));
		additionalInformation.put("procesoId", proceso.getId());
		additionalInformation.put("procesoNombre", proceso.getNombre());
		additionalInformation.put("posicionCodigo", usuario.getPosicion().getCodigo());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);		
		return accessToken;
	}
}
