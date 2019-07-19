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

import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.services.JustificacionServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class JustificacionController {
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
	public Justificacion show(@PathVariable Long id) {
		return this.justificacionService.findById(id);
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
}
