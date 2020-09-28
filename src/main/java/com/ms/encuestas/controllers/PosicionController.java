package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
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

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.services.PosicionServiceI;
import com.ms.encuestas.services.ProcesoServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PosicionController {
	private Logger logger = LoggerFactory.getLogger(PosicionController.class);	
	@Autowired
	private PosicionServiceI posicionService;
	@Autowired
	private ProcesoServiceI procesoService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/posiciones/cantidad")
	public Long count(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de posiciones en la base de datos.", user.getUsername()));
		return posicionService.count();
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/procesos/{procesoId}/cantidad-datos-posiciones")
	public Long countDatos(Authentication authentication, @PathVariable Long procesoId) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de posiciones en la encuesta con ID=%d en la base de datos.", user.getUsername(), procesoId));
		return posicionService.countDatos(procesoId);
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/posiciones")
	public List<Posicion> index(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó todas las posiciones en la base de datos.", user.getUsername()));
		return posicionService.findAll();
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/posiciones/{codigo}")
	public ResponseEntity<?> show(Authentication authentication, @PathVariable String codigo) {
		User user = (User) authentication.getPrincipal();
		Posicion posicion = null;
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info(String.format("El usuario '%s' buscó la posición con código '%s' en la base de datos.", user.getUsername(), codigo));
			posicion = posicionService.findByCodigo(codigo);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("La posición con código '%s' no existe en la base de datos.", codigo));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Posicion>(posicion, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Posicion create(Authentication authentication, @RequestBody Posicion posicion) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' creó la posición con código '%s'.", user.getUsername(), posicion.getCodigo()));
		return posicionService.insert(posicion);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Posicion update(Authentication authentication, @RequestBody Posicion posicion) {
		User user = (User) authentication.getPrincipal();
		Posicion posicionBuscada = posicionService.findByCodigo(posicion.getCodigo());		
		if (posicionBuscada != null) {
			logger.info(String.format("El usuario '%s' actualizó la posición con código '%s'.", user.getUsername(), posicion.getCodigo()));
			return posicionService.update(posicion);
		} else {
			logger.error(String.format("El usuario '%s' no pudo actualizar la posición con código '%s' porque no se encontró en la base de datos.", user.getUsername(), posicion.getCodigo()));
			return null;
		}
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/posiciones/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable String codigo) {
		User user = (User) authentication.getPrincipal();		
		Posicion posicionBuscada = posicionService.findByCodigo(codigo);
		if (posicionBuscada != null) {
			logger.info(String.format("El usuario '%s' eliminó la posición con código '%s'.", user.getUsername(), posicionBuscada.getCodigo()));
			posicionService.deleteByCodigo(codigo);
		} else {
			logger.error(String.format("El usuario '%s' no pudo eliminar la posición con código '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/posiciones/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.posicionService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/posiciones/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadExcel() {
		Resource resource = posicionService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/procesos/{procesoId}/cargar-datos-posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public void cargarDatosPosiciones(@PathVariable Long procesoId, @RequestParam("file") MultipartFile file) {
		try {
			Proceso proceso = procesoService.findById(procesoId);
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			posicionService.processExcelDatos(proceso, file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/procesos/{procesoId}/descargar-datos-posiciones")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadDatos(@PathVariable Long procesoId) {
		Resource resource = posicionService.downloadExcelDatos(procesoId);
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("posiciones/eliminar-datos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAllDatos(Authentication authentication, @RequestBody Proceso proceso) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' eliminó todas las posiciones de la encuesta con código '%s' de la base de datos.", user.getUsername(), proceso.getCodigo()));
		posicionService.deleteDatos(proceso);
	}
	
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/posiciones/{codigo}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Posicion softDelete(Authentication authentication, @PathVariable String codigo) {
        User user = (User) authentication.getPrincipal();   
        Posicion posicionBuscada = posicionService.findByCodigo(codigo);
        if (posicionBuscada != null) {
        	posicionBuscada = posicionService.softDelete(posicionBuscada);
            logger.info(String.format("El usuario '%s' deshabilitó la posición con código '%s'.", user.getUsername(), posicionBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar la posición con código '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
        }
        return posicionBuscada;
    }
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/posiciones/{codigo}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Posicion softUndelete(Authentication authentication, @PathVariable String codigo) {
        User user = (User) authentication.getPrincipal();   
        Posicion posicionBuscada = posicionService.findByCodigo(codigo);
        if (posicionBuscada != null) {
        	posicionBuscada = posicionService.softUndelete(posicionBuscada);
            logger.info(String.format("El usuario '%s' habilitó la posición con código '%s'.", user.getUsername(), posicionBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar la posición con código '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
        }
        return posicionBuscada;
    }
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/posiciones/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' eliminó todas las posiciones de la base de datos.", user.getUsername()));
		posicionService.deleteAll();
	}
}
