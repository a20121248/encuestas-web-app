package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.ProcesoServiceI;
import com.ms.encuestas.services.UsuarioServiceI;
import com.ms.encuestas.services.segcen.ISegCenServicios;
import com.ms.encuestas.services.segcen.SegCenServicio;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	@Autowired
	private UsuarioServiceI usuarioService;
	@Autowired
	private ProcesoServiceI procesoService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String usuarioRed = authentication.getName();
		Map<String, Object> additionalInformation = new HashMap<>();
		if (authentication.getName().equals("admin.encuestas")) {
			additionalInformation.put("nombre", "Administrador");
		} else {
			Usuario usuario;
    		if (usuarioRed.length()>5 && usuarioRed.substring(0, 5).equals("epps\\")) {
    			usuario = usuarioService.findByUsuarioGenerales(usuarioRed);
    		} else {
    			usuario = usuarioService.findByUsuarioVida(usuarioRed);
    		}
    		additionalInformation.put("codigo", usuario.getCodigo());
    		additionalInformation.put("nombre", usuario.getNombreCompleto());
		}
		
		/*Map<String, Object> procesoResponse = new HashMap<>();
		procesoResponse.put("id", proceso.getId());
		procesoResponse.put("nombre", proceso.getNombre());
		additionalInformation.put("proceso", procesoResponse);		
		
		Map<String, Object> posicionResponse = new HashMap<>();
		posicionResponse.put("codigo", usuario.getPosicion().getCodigo());
		additionalInformation.put("posicion", posicionResponse);*/
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}
}
