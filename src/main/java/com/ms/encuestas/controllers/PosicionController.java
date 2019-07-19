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

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.services.PosicionServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PosicionController {
	@Autowired
	private PosicionServiceI posicionService;
	
	@GetMapping("/posiciones/cantidad")
	public Long count() {
		return posicionService.count();
	}
	
	@GetMapping("/posiciones")
	public List<Posicion> index() {
		return posicionService.findAll();
	}
	
	@GetMapping("/posiciones/{codigo}")
	public Posicion show(@PathVariable String codigo) {
		return this.posicionService.findByCodigo(codigo);
	}

	@GetMapping("/posiciones-con-area-y-centro/{codigo}")
	public Posicion showWithAreaAndCentro(@PathVariable String codigo) {
		return this.posicionService.findByCodigoWithAreaAndCentro(codigo);
	}
	
	@GetMapping("/posiciones-con-area/{codigo}")
	public Posicion showWithArea(@PathVariable String codigo) {
		return this.posicionService.findByCodigoWithArea(codigo);
	}
	
	@GetMapping("/posiciones-con-centro/{codigo}")
	public Posicion showWithCentro(@PathVariable String codigo) {
		return this.posicionService.findByCodigoWithCentro(codigo);
	}
	
	@PutMapping("/posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Posicion update(@RequestBody Posicion posicion, @PathVariable String codigo) {
		Posicion currentCentro = this.posicionService.findByCodigo(codigo);
		currentCentro.setNombre(posicion.getNombre());
		//currentCentro.setApellido(centro.get());
		//currentCentro.setEmail(centro.getEmail());
		this.posicionService.save(currentCentro);
		return currentCentro;
	}

	@DeleteMapping("/posiciones/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String codigo) {
		Posicion currentPosicion = this.posicionService.findByCodigo(codigo);
		this.posicionService.delete(currentPosicion);
	}
}
