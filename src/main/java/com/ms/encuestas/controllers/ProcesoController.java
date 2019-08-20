package com.ms.encuestas.controllers;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Proceso;
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
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);

        logger.debug("Hello from Logback {}", data);


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
/*
	@PutMapping("/divisiones")
	@ResponseStatus(HttpStatus.CREATED)
	public Division update(@RequestBody Division area, @PathVariable Long id) {
		Division currentDivision = this.divisionService.findById(id);
		currentDivision.setNombre(area.getNombre());
		//currentCentro.setApellido(centro.get());
		//currentCentro.setEmail(centro.getEmail());
		this.divisionService.save(currentDivision);
		return currentDivision;
	}

	@DeleteMapping("/divisiones/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Division currentDivision = this.divisionService.findById(id);
		this.divisionService.delete(currentDivision);
	}
*/
}
