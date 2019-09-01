package com.ms.encuestas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		//Proceso proceso = procesoService.getCurrentProceso();
		//Usuario usuario = usuarioService.findByCodigoAndProceso(authentication.getName(), proceso.getId());
		Map<String, Object> additionalInformation = new HashMap<>();
		if (authentication.getName().equals("admin.encuestas")) {
			additionalInformation.put("nombre", "Administrador");
		} else {
        	ISegCenServicios segCenServicios = new SegCenServicio().getBasicHttpBindingISegCenServicios();
        	String response = segCenServicios.obtenerNombreUsuario(authentication.getName());
			additionalInformation.put("nombre", response);
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
