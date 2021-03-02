package com.ms.encuestas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/oauth")
public class LogoutController {
    @Autowired
    private TokenStore tokenStore;
    
	@PostMapping("/logout")
	public ResponseEntity<String> revoke(OAuth2Authentication auth) {
	    try {
	    	final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
	    	String tokenValue = details.getTokenValue();
	        if (tokenValue != null) {
	            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
	            tokenStore.removeAccessToken(accessToken);

	            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
	            tokenStore.removeRefreshToken(refreshToken);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Token de acceso invalido.");
	    }
	    return ResponseEntity.ok().body("Token de acceso revocado correctamente.");
	}
	
}