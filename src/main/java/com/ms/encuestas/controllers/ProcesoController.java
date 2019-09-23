package com.ms.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.PosicionServiceI;
import com.ms.encuestas.services.ProcesoServiceI;
import com.ms.encuestas.services.UsuarioServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProcesoController {
    private static final Logger logger = LoggerFactory.getLogger(ProcesoController.class);
	@Autowired
	private ProcesoServiceI procesoService;
	@Autowired
	private UsuarioServiceI usuarioService;
	@Autowired
	private PosicionServiceI posicionService;

	@GetMapping("/procesos/{procesoId}/usuarios/{usuarioCodigo}/posicion")
	public Posicion findByProcesoIdAndUsuarioCodigo(Authentication authentication, @PathVariable Long procesoId, @PathVariable String usuarioCodigo) {
		// User user = (User) authentication.getPrincipal();
		return posicionService.findByProcesoIdAndUsuarioCodigo(procesoId, usuarioCodigo);
	}
	
	@GetMapping("/procesos/actual")
	public Proceso getCurrentProceso() {
		return procesoService.getCurrentProceso();
	}
	
	@GetMapping("/procesos/cantidad")
	public Long count() {
		return procesoService.count();
	}
	
	@GetMapping("/procesos")
	public List<Proceso> index() {
		return procesoService.findAll();
	}

	@GetMapping("/procesos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Proceso proceso = null;
		Map<String, Object> response = new HashMap<>();
		try {
			proceso = this.procesoService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El proceso %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Proceso>(proceso, HttpStatus.OK);
	}

	@PostMapping("/procesos")
	@ResponseStatus(HttpStatus.CREATED)
	public Proceso create(Authentication authentication, @RequestBody Proceso proceso) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioService.findByUsuarioRed(user.getUsername());
		proceso.setUsuario(usuario);
		proceso = procesoService.insert(proceso);
		logger.info(String.format("El usuario con matrícula '%s' creó la encuesta con código '%s'.", usuario.getCodigo(), proceso.getCodigo()));
		return proceso;
	}
	
	@PutMapping("/procesos")
	@ResponseStatus(HttpStatus.CREATED)
	public Proceso update(Authentication authentication, @RequestBody Proceso proceso) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioService.findByUsuarioRed(user.getUsername());
		
		Proceso procesoBuscado = procesoService.findById(proceso.getId());
		if (procesoBuscado != null) {
			proceso.setUsuario(usuario);
			procesoService.update(proceso);
			logger.info(String.format("El usuario con matrícula '%s' actualizó la encuesta con código '%s'.", usuario.getCodigo(), proceso.getCodigo()));
		} else {
			logger.error(String.format("No se pudo actualizar la encuesta con ID=%d porque no se encontró en la base de datos.", proceso.getId()));
		}
		return proceso;
	}

	@DeleteMapping("/procesos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable Long id) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioService.findByUsuarioRed(user.getUsername());
		
		Proceso procesoBuscado = procesoService.findById(id);
		if (procesoBuscado != null) {
			procesoService.deleteById(id);
			logger.info(String.format("El usuario con matrícula '%s' actualizó la encuesta con código '%s'.", usuario.getCodigo(), procesoBuscado.getCodigo()));
		} else {
			logger.error(String.format("No se pudo eliminar la encuesta con ID=%d porque no se encontró en la base de datos.", id));
		}		
	}
}
