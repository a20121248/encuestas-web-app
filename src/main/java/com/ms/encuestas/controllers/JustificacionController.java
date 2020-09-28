package com.ms.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.services.JustificacionServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class JustificacionController {
	private final Logger logger = LoggerFactory.getLogger(JustificacionController.class);
	@Autowired
	private JustificacionServiceI justificacionService;
	
	@GetMapping("/justificaciones/cantidad")
	public Long count() {
		return justificacionService.count();
	}
	
	@GetMapping("/justificaciones")
	public List<Justificacion> index() {
		return justificacionService.findAll();
	}

	@GetMapping("/justificaciones/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Justificacion justificacion = null;
		Map<String, Object> response = new HashMap<>();
		try {
			justificacion = this.justificacionService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("La justificación %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Justificacion>(justificacion, HttpStatus.OK);
	}
	
	@PutMapping("/justificaciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Justificacion update(@RequestBody Justificacion justificacion, @PathVariable Long id) {
		Justificacion currentJustificacion = this.justificacionService.findById(id);
		currentJustificacion.setNombre(justificacion.getNombre());
		//currentJustificacion.setApellido(centro.get());
		//currentJustificacion.setEmail(centro.getEmail());
		this.justificacionService.save(currentJustificacion);
		return currentJustificacion;
	}

	@DeleteMapping("/justificaciones/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Justificacion currentJustificacion = this.justificacionService.findById(id);
		this.justificacionService.delete(currentJustificacion);
	}
	
    @Secured("ROLE_ADMIN")
    @PutMapping("/justificaciones/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Justificacion softDelete(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();   
        Justificacion justificacionBuscada = justificacionService.findById(id);
        if (justificacionBuscada != null) {
        	justificacionBuscada = justificacionService.softDelete(justificacionBuscada);
            logger.info(String.format("El usuario '%s' deshabilitó el producto con ID=%d.", user.getUsername(), justificacionBuscada.getId()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el producto con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
        }
        return justificacionBuscada;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/justificaciones/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Justificacion softUndelete(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();   
        Justificacion justificacionBuscada = justificacionService.findById(id);
        if (justificacionBuscada != null) {
        	justificacionBuscada = justificacionService.softUndelete(justificacionBuscada);
            logger.info(String.format("El usuario '%s' habilitó el producto con ID=%d.", user.getUsername(), justificacionBuscada.getId()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el producto con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
        }
        return justificacionBuscada;
    }
}
