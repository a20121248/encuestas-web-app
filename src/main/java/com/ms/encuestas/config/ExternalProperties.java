package com.ms.encuestas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("file:/opt/pacifico/properties/EncPpto/external.properties")
public class ExternalProperties {
	
}
