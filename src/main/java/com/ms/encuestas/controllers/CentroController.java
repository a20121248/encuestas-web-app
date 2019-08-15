package com.ms.encuestas.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.services.CentroServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CentroController {
	@Autowired
	private CentroServiceI centroService;

	@GetMapping("/centros/cantidad")
	public Long count() {
		return centroService.count();
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
}
