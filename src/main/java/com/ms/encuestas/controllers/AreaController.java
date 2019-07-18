package com.ms.encuestas.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ms.encuestas.models.Area;
import com.ms.encuestas.services.AreaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AreaController {
	@Autowired
	private AreaServiceI areaService;
	
	@GetMapping("/areas/cantidad")
	public Long count() {
		return areaService.count();
	}
	
	@GetMapping("/areas")
	public List<Area> index() {
		return areaService.findAll();
	}
	
	@GetMapping("/areas-con-division")
	public List<Area> showWithDivision() {
		return this.areaService.findAllWithDivision();
	}	

	@GetMapping("/areas/{id}")
	public Area show(@PathVariable Long id) {
		return this.areaService.findById(id);
	}
	
	@GetMapping("/areas-con-division/{id}")
	public Area showWithDivision(@PathVariable Long id) {
		return this.areaService.findByIdWithDivision(id);
	}

	@PutMapping("/areas")
	@ResponseStatus(HttpStatus.CREATED)
	public Area update(@RequestBody Area area, @PathVariable Long id) {
		Area currentCentro = this.areaService.findById(id);
		currentCentro.setNombre(area.getNombre());
		//currentCentro.setApellido(centro.get());
		//currentCentro.setEmail(centro.getEmail());
		this.areaService.save(currentCentro);
		return currentCentro;
	}

	@DeleteMapping("/areas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Area currentArea = this.areaService.findById(id);
		this.areaService.delete(currentArea);
	}
}
