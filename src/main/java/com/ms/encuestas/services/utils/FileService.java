package com.ms.encuestas.services.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.exceptions.FileException;
import com.ms.encuestas.exceptions.FileNotFoundException;

@Service
public class FileService implements FileServiceI {
	private Logger logger = LoggerFactory.getLogger(FileService.class);
	private final Path fileMantenimientosLocation;
	private final Path fileReportesLocation;
	private final Path fileLogsLocation;

	@Autowired
	public FileService() {		
		String mantenimientosPath = String.join(File.separator, Arrays.asList("storage", "app", "mantenimientos"));
		String reportesPath = String.join(File.separator, Arrays.asList("storage", "app", "reportes"));
		String logsPath = String.join(File.separator, Arrays.asList("storage", "logs"));
		
		this.fileMantenimientosLocation = Paths.get(mantenimientosPath).toAbsolutePath().normalize();
		this.fileReportesLocation = Paths.get(reportesPath).toAbsolutePath().normalize();
		this.fileLogsLocation = Paths.get(logsPath).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileMantenimientosLocation);
			Files.createDirectories(this.fileReportesLocation);
			Files.createDirectories(this.fileLogsLocation);
			logger.info(String.format("Se creó la carpeta storage."));
		} catch (Exception ex) {
			throw new FileException("No se pudo crear la carpeta storage.", ex);
		}
	}

	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileReportesLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("Archivo no encontrado " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("Archivo no encontrado " + fileName, ex);
		}
	}
	
	@Override
	public Resource loadFileLogAsResource(Path fileName) {
		try {
			Resource resource = new UrlResource(fileName.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("Archivo no encontrado " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("Archivo no encontrado " + fileName, ex);
		}
	}
}
