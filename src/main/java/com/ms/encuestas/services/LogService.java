package com.ms.encuestas.services;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class LogService implements LogServiceI{
	@Autowired
	private FileServiceI fileService;
	
	@Override
	public Resource obtenerLogFile() {
		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		List<String> alphabets = Arrays.asList(currentPath, "storage", "logs", "app.log");
		String result = String.join(File.separator, alphabets);
		return fileService.loadFileLogAsResource("app.log");
//		try {
//			Path file = currentRelativePath.resolve(result);
//			Resource resource =  new UrlResource(file.toUri());
//			if (resource.exists() || resource.isReadable()) {
//				return resource;
//			} else {
//				throw new RuntimeException("Fallo la lectura del archivo!");
//			}
//		} catch (MalformedURLException e) {
//			throw new RuntimeException("Fallo la lectura de la url del archivo!");
//		}
	}
}
