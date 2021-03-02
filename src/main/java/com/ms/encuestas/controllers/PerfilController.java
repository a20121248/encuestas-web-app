package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.services.PerfilServiceI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PerfilController {
    private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
	
	@Autowired
	private PerfilServiceI perfilService;
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/perfiles/cantidad")
	public Long count() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' consultó la cantidad de perfiles en la base de datos.", user.getName()));
		return perfilService.count();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/perfiles")
	public List<Perfil> index() throws Exception {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' consultó todos los perfiles en la base de datos.", user.getName()));
		return perfilService.findAll();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/perfiles/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		Perfil perfil = null;
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info(String.format("El usuario '%s' buscó el perfil con ID=%d en la base de datos.", user.getName(), id));
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
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/perfiles/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.perfilService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/perfiles/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadPerfiles() {
		Resource resource = perfilService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/perfiles/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' eliminó todos los perfiles de la base de datos.", user.getName()));
		perfilService.deleteAll();
	}
}
