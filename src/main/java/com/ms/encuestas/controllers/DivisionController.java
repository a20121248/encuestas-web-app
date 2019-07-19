package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Division show(@PathVariable Long id) {
		return this.divisionService.findById(id);
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
