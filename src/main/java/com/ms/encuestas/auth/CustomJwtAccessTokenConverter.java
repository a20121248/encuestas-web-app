package com.ms.encuestas.auth;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        Authentication userAuthentication = authentication.getUserAuthentication();

        if (userAuthentication != null) {
            String codigo = (String) map.get("user_name");    		
            userAuthentication = new UsernamePasswordAuthenticationToken(
            		new User(codigo, "", true, true, true, true, userAuthentication.getAuthorities()),
                    userAuthentication.getCredentials(),
                    userAuthentication.getAuthorities());
            //if (userDetails != null) {
            	//System.out.println("wooo: " + userDetails.get("localUserTableField"));
            	/*
                // build your principal here
                String localUserTableField = (String) userDetails.get("localUserTableField");
                CustomUserDetails extendedPrincipal = new CustomUserDetails(localUserTableField);

                Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();

                userAuthentication = new UsernamePasswordAuthenticationToken(extendedPrincipal,
                        userAuthentication.getCredentials(), authorities);*/
            //}
        }
        return new OAuth2Authentication(authentication.getOAuth2Request(), userAuthentication);
    }
}
