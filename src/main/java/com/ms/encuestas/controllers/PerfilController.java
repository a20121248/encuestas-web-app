package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.services.PerfilServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PerfilController {
    private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
	
	@Autowired
	private PerfilServiceI perfilService;
	
	@GetMapping("/perfiles/cantidad")
	public Long count() {
		return perfilService.count();
	}
	
	@GetMapping("/perfiles")
	public List<Perfil> index() throws Exception {
		return perfilService.findAll();
	}
	
	@GetMapping("/perfiles/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Perfil perfil = null;
		Map<String, Object> response = new HashMap<>();
		try {
			perfil = this.perfilService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El perfil %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Perfil>(perfil, HttpStatus.OK);
	}
	
	@PostMapping("/perfiles/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo ", file.getOriginalFilename()));
			this.perfilService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}		
	}	
}
