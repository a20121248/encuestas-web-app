package com.ms.encuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReporteService implements ReporteServiceI {
	@Autowired
	private FileServiceI fileService;
	
	@Override
	public Resource generarReporteControl(Long procesoId, String fileName) {
		return fileService.loadFileAsResource(fileName);
	}

}
