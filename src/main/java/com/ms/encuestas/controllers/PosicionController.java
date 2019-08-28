package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.services.PosicionServiceI;
import com.ms.encuestas.services.ProcesoServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PosicionController {
	private Logger logger = LoggerFactory.getLogger(PosicionController.class);	
	@Autowired
	private PosicionServiceI posicionService;
	@Autowired
	private ProcesoServiceI procesoService;
	
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
		return posicionService.findByCodigo(codigo);
	}

	@GetMapping("/posiciones-con-area-y-centro/{codigo}")
	public Posicion showWithAreaAndCentro(@PathVariable String codigo) {
		return posicionService.findByCodigoWithAreaAndCentro(codigo);
	}
	
	@GetMapping("/posiciones-con-area/{codigo}")
	public Posicion showWithArea(@PathVariable String codigo) {
		return posicionService.findByCodigoWithArea(codigo);
	}
	
	@GetMapping("/posiciones-con-centro/{codigo}")
	public Posicion showWithCentro(@PathVariable String codigo) {
		return posicionService.findByCodigoWithCentro(codigo);
	}
	
	@PutMapping("/posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Posicion update(@RequestBody Posicion posicion, @PathVariable String codigo) {
		Posicion currentCentro = posicionService.findByCodigo(codigo);
		currentCentro.setNombre(posicion.getNombre());
		//currentCentro.setApellido(centro.get());
		//currentCentro.setEmail(centro.getEmail());
		posicionService.save(currentCentro);
		return currentCentro;
	}

	@DeleteMapping("/posiciones/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String codigo) {
		Posicion currentPosicion = posicionService.findByCodigo(codigo);
		posicionService.delete(currentPosicion);
	}

	@PostMapping("/procesos/{procesoId}/cargar-datos-posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public void cargarDatosPosiciones(@PathVariable Long procesoId, @RequestParam("file") MultipartFile file) {
		try {
			Proceso proceso = procesoService.findById(procesoId);
			posicionService.deleteDatos(proceso);
			logger.info(String.format("Leyendo el archivo ", file.getOriginalFilename()));
			posicionService.processExcelDatos(proceso, file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
}
