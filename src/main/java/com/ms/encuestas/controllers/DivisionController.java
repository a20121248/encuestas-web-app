package com.ms.encuestas.controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.DivisionServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class DivisionController {
	@Autowired
	private DivisionServiceI divisionService;
	
	@GetMapping("/divisiones/cantidad")
	public Long count() {
		return divisionService.count();
	}
	
	@GetMapping("/divisiones")
	public List<Division> index() {
		return divisionService.findAll();
	}

	@GetMapping("/divisiones/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Division division = null;
		Map<String, Object> response = new HashMap<>();
		try {
			division = this.divisionService.findById(id);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("La división %d no existe en la base de datos.", id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Division>(division, HttpStatus.OK);
	}

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
}
