package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.services.CentroService;
import com.ms.encuestas.services.CentroServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CentroController {
	private Logger logger = LoggerFactory.getLogger(CentroController.class);
	
	@Autowired
	private CentroServiceI centroService;

	@GetMapping("/centros/cantidad")
	public Long count() {
		Long empresaId = new Long(1);
		return centroService.count(empresaId);
	}
	
	@GetMapping("/centros")
	public List<Centro> index() throws Exception {
		return centroService.findAll();
	}
	
	@GetMapping("/centros/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Centro centro = null;
		Map<String, Object> response = new HashMap<>();
		try {
			centro = this.centroService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El centro %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Centro>(centro, HttpStatus.OK);
	}

	@PostMapping("/centros")
	@ResponseStatus(HttpStatus.CREATED)
	public Centro create(@RequestBody Centro centro) {
		centro.setFechaCreacion(new Date());
		this.centroService.save(centro);
		return centro;
	}

	@PutMapping("/centros/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Centro update(@RequestBody Centro centro, @PathVariable Long id) {
		Centro currentCentro = this.centroService.findById(id);
		currentCentro.setNombre(centro.getNombre());
		//currentCentro.setApellido(centro.get());
		//currentCentro.setEmail(centro.getEmail());
		this.centroService.save(currentCentro);
		return currentCentro;
	}

	@DeleteMapping("/centros/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Centro currentCentro = this.centroService.findById(id);
		this.centroService.delete(currentCentro);
	}
	
	@PostMapping("/centros/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo ", file.getOriginalFilename()));
			this.centroService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
		
	}
	
	
	@PostMapping("/centros/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadCentros() {
		Resource resource = centroService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
}
