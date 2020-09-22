package com.ms.encuestas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EncuestasWebAppApplication extends SpringBootServletInitializer implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(EncuestasWebAppApplication.class);
	@Value("${app.usarAD}")
	private boolean usarAD;
	@Value("${app.segCen.url}")
	private String segCenUrl;
	//@Value("${spring.datasource.jndiName}")
	//private String jndiName;
	
	public static void main(String[] args) {
		SpringApplication.run(EncuestasWebAppApplication.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EncuestasWebAppApplication.class);
    }

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			logger.info("VALORES LEÍDOS PROPERTIES:");
			logger.info("- Usar Segcen: " + usarAD);
			logger.info("- URL Segcen: " + segCenUrl);
			//logger.info("- URL Datasource: " + jndiName);
		};
	}

    @Override
    public void run(String... strings) throws Exception {

    }

}
