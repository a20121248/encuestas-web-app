package com.ms.encuestas.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class LogService implements LogServiceI{
	@Autowired
	private FileServiceI fileService;
	
	@Value("${logging.file}")
	private String logFilePath;
	
	@Override
	public Resource obtenerLogFile() {
		Path currentRelativePath = Paths.get(logFilePath);

		return fileService.loadFileLogAsResource(currentRelativePath);
	}
}
