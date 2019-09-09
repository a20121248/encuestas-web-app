package com.ms.encuestas.controllers;

import java.io.IOException;
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

import com.ms.encuestas.models.Area;
import com.ms.encuestas.services.AreaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AreaController {
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	@Autowired
	private AreaServiceI areaService;
	
	@GetMapping("/areas/cantidad")
	public Long count() {
		return areaService.count();
	}
	
	@GetMapping("/areas")
	public List<Area> index() {
		return areaService.findAll();
	}

	@GetMapping("/areas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Area area = null;
		Map<String, Object> response = new HashMap<>();
		try {
			area = this.areaService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El área %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Area>(area, HttpStatus.OK);
	}

	@PutMapping("/areas")
	@ResponseStatus(HttpStatus.CREATED)
	public Area update(@RequestBody Area area, @PathVariable Long id) {
		Area currentArea = this.areaService.findById(id);
		currentArea.setNombre(area.getNombre());
		this.areaService.save(currentArea);
		return currentArea;
	}

	@DeleteMapping("/areas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Area currentArea = this.areaService.findById(id);
		this.areaService.delete(currentArea);
	}
	
	@PostMapping("/areas/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.areaService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@PostMapping("/areas/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadExcel() {
		Resource resource = areaService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@PostMapping("/areas/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll() {
		areaService.deleteAll();
	}
}
