package com.ms.encuestas.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:file.properties")
@ConfigurationProperties
public class FileProperties {
	private String reportesPath;

	public String getReportesPath() {
		return reportesPath;
	}

	public void setReportesPath(String reportesPath) {
		this.reportesPath = reportesPath;
	}
}
