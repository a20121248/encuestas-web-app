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

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.ProcesoServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/procesos")
public class ProcesoController {
    private static final Logger logger = LoggerFactory.getLogger(ProcesoController.class);

	@Autowired
	private ProcesoServiceI procesoService;
	
	@GetMapping("/cantidad")
	public Long count() {
		return procesoService.count();
	}
	
	@GetMapping("")
	public List<Proceso> index() {
		return procesoService.findAll();
	}

	@GetMapping("/{id}")
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

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Proceso create(Authentication authentication, @RequestBody Proceso proceso) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = new Usuario();
		usuario.setCodigo(user.getUsername());
		proceso.setUsuario(usuario);
		this.procesoService.store(proceso);
		return this.procesoService.findByCodigo(proceso.getCodigo());
	}	
	
	@PutMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Proceso update(Authentication authentication, @RequestBody Proceso proceso) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = new Usuario();
		usuario.setCodigo(user.getUsername());
		
		Proceso currentProceso = this.procesoService.findById(proceso.getId());
		currentProceso.setCodigo(proceso.getCodigo());
		currentProceso.setNombre(proceso.getNombre());
		currentProceso.setUsuario(usuario);
		currentProceso.setFechaCierre(proceso.getFechaCierre());
		this.procesoService.update(currentProceso);
		return currentProceso;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable Long id) {
		Proceso proceso = this.procesoService.findById(id);
		this.procesoService.delete(proceso);
	}

}
