package com.ms.encuestas.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Filtro;
import com.ms.encuestas.services.ReporteServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
	@Autowired
	private ReporteServiceI reporteService;
	
	private final Logger log = LoggerFactory.getLogger(ReporteController.class);

	@PostMapping("/control")
	@Transactional(readOnly = true)
	public ResponseEntity<?> getControl(@RequestBody Filtro filtro) {		
		Resource resource = reporteService.generarReporteControl(filtro);
		String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@PostMapping("/empresas")
	@Transactional(readOnly = true)
	public ResponseEntity<?> getEmpresas(@RequestBody Filtro filtro) {
		Resource resource = reporteService.generarReporteEmpresas(filtro);
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@PostMapping("/consolidado")
	@Transactional(readOnly = true)
	public ResponseEntity<?> getConsolidado(@RequestBody Filtro filtro) {
		Resource resource = reporteService.generarReporteConsolidado(filtro);
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
}
