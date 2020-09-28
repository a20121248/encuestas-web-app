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
import org.springframework.security.core.userdetails.User;
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
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.services.CentroServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CentroController {
	private Logger logger = LoggerFactory.getLogger(CentroController.class);
	
	@Autowired
	private CentroServiceI centroService;

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/centros/cantidad")
	public Long count(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de centros de costos en la base de datos.", user.getUsername()));
		Long empresaId = new Long(1);
		return centroService.count(empresaId);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/centros/tipos")
	public List<Tipo> findAllTipos(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó los tipos de centros de costos en la base de datos.", user.getUsername()));
		return centroService.findAllTipos();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/centros")
	public List<Centro> index(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó todos los centros de costos en la base de datos.", user.getUsername()));
		return centroService.findAll();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/centros/{id}")
	public ResponseEntity<?> show(Authentication authentication, @PathVariable Long id) {
		User user = (User) authentication.getPrincipal();
		Centro centro = null;
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info(String.format("El usuario '%s' buscó el centro de costos con ID=%d en la base de datos.", user.getUsername(), id));
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

	@Secured("ROLE_ADMIN")
	@PostMapping("/centros")
	@ResponseStatus(HttpStatus.CREATED)
	public Centro create(Authentication authentication, @RequestBody Centro centro) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' creó el centro de costos con código '%s'.", user.getUsername(), centro.getCodigo()));
		return centroService.insert(centro);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/centros")
	@ResponseStatus(HttpStatus.CREATED)
	public Centro update(Authentication authentication, @RequestBody Centro centro) {
		User user = (User) authentication.getPrincipal();
		Centro centroBuscado = centroService.findById(centro.getId());
		if (centroBuscado != null) {
			logger.info(String.format("El usuario '%s' actualizó el centro de costos con código '%s'.", user.getUsername(), centro.getCodigo()));
			return centroService.update(centro);
		} else {
			logger.error(String.format("El usuario '%s' no pudo actualizar el centro de costos con ID=%d porque no se encontró en la base de datos.", centro.getId()));
			return null;
		}
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/centros/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable Long id) {
		User user = (User) authentication.getPrincipal();		
		Centro centroBuscado = centroService.findById(id);
		if (centroBuscado != null) {
			logger.info(String.format("El usuario '%s' eliminó el centro de costos con código '%s'.", user.getUsername(), centroBuscado.getCodigo()));
			centroService.deleteById(id);
		} else {
			logger.error(String.format("El usuario '%s' no pudo eliminar la encuesta con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
		}
	}
	
    @Secured("ROLE_ADMIN")
    @PutMapping("/centros/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Centro softDelete(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();   
        Centro centroBuscado = centroService.findById(id);
        if (centroBuscado != null) {
        	centroBuscado = centroService.softDelete(centroBuscado);
            logger.info(String.format("El usuario '%s' deshabilitó el centro con código '%s'.", user.getUsername(), centroBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el centro con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
        }
        return centroBuscado;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/centros/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Centro softUndelete(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();   
        Centro centroBuscado = centroService.findById(id);
        if (centroBuscado != null) {
        	centroBuscado = centroService.softUndelete(centroBuscado);
            logger.info(String.format("El usuario '%s' habilitó el centro con código '%s'.", user.getUsername(), centroBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el centro con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
        }
        return centroBuscado;
    }
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/centros/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.centroService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}		
	}	
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/centros/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadCentros() throws IOException {
		Resource resource = centroService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/centros/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAllCentros(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' eliminó todas los centros de costos de la base de datos.", user.getUsername()));
		centroService.deleteAllCentros();
	}
}
