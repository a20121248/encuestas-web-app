package com.ms.encuestas.services.utils;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.exceptions.FileException;
import com.ms.encuestas.exceptions.FileNotFoundException;
import com.ms.encuestas.properties.FileProperties;

@Service
public class FileService implements FileServiceI {
	private final Path fileReportesLocation;

	@Autowired
	public FileService(FileProperties fileStorageProperties) {
		fileReportesLocation = null;
		/*this.fileReportesLocation = Paths.get(fileStorageProperties.getReportesPath()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileReportesLocation);
		} catch (Exception ex) {
			throw new FileException("Could not create the directory where the uploaded files will be stored.", ex);
		}*/
	}

	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileReportesLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}
}
