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
	private final Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaServiceI areaService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/areas/cantidad")
	public Long count() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' consultó la cantidad de áreas en la base de datos.", user.getName()));
		return areaService.count();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/areas")
	public List<Area> index() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' consultó todas las áreas en la base de datos.", user.getName()));
		return areaService.findAll();
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/areas/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll() {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		logger.info(String.format("El usuario '%s' eliminó todas las áreas de la base de datos.", user.getName()));
		areaService.deleteAll();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/areas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		Area area = null;
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info(String.format("El usuario '%s' buscó el área con ID=%d.", user.getName(), id));
			area = areaService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El área con ID='%d' no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Area>(area, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/areas")
	@ResponseStatus(HttpStatus.CREATED)
	public Area create(@RequestBody Area area) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		area = areaService.insert(area);
		logger.info(String.format("El usuario '%s' creó el área con código '%s'.", user.getName(), area.getCodigo()));
		return area;
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/areas")
	@ResponseStatus(HttpStatus.CREATED)
	public Area update(@RequestBody Area area) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		Area areaBuscada = areaService.findById(area.getId());
		if (areaBuscada != null) {
			area = areaService.update(area);
			logger.info(String.format("El usuario '%s' actualizó el área con código '%s'.", user.getName(), area.getCodigo()));
		} else {
			logger.error(String.format("El usuario '%s' no pudo actualizar el área con ID=%d porque no se encontró en la base de datos.", area.getId()));
		}
		return area;
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/areas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();		
		Area areaBuscada = areaService.findById(id);
		if (areaBuscada != null) {
			logger.info(String.format("El usuario '%s' eliminó el área con código '%s'.", user.getName(), areaBuscada.getCodigo()));
			areaService.deleteById(id);
		} else {
			logger.error(String.format("El usuario '%s' no pudo eliminar el área con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
		}
	}
	
    @Secured("ROLE_ADMIN")
    @PutMapping("/areas/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Area softDelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Area areaBuscada = areaService.findById(id);
        if (areaBuscada != null) {
        	areaBuscada = areaService.softDelete(areaBuscada);
            logger.info(String.format("El usuario '%s' deshabilitó el área con código '%s'.", user.getName(), areaBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el área con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return areaBuscada;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/areas/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Area softUndelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Area areaBuscada = areaService.findById(id);
        if (areaBuscada != null) {
        	areaBuscada = areaService.softUndelete(areaBuscada);
            logger.info(String.format("El usuario '%s' habilitó el área con código '%s'.", user.getName(), areaBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el área con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return areaBuscada;
    }
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured("ROLE_ADMIN")
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
}
