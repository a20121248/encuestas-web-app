package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
	
	@GetMapping("/procesos/{procesoId}/cantidad-datos-posiciones")
	public Long countDatos(@PathVariable Long procesoId) {
		return posicionService.countDatos(procesoId);
	}
	
	@GetMapping("/posiciones")
	public List<Posicion> index() {
		return posicionService.findAll();
	}
	
	@GetMapping("/posiciones/{codigo}")
	public Posicion show(@PathVariable String codigo) {
		return posicionService.findByCodigo(codigo);
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
	
	@PostMapping("/posiciones/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.posicionService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@PostMapping("/posiciones/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadExcel() {
		Resource resource = posicionService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

	@PostMapping("/procesos/{procesoId}/cargar-datos-posiciones")
	@ResponseStatus(HttpStatus.CREATED)
	public void cargarDatosPosiciones(@PathVariable Long procesoId, @RequestParam("file") MultipartFile file) {
		try {
			Proceso proceso = procesoService.findById(procesoId);
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			posicionService.processExcelDatos(proceso, file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@PostMapping("/procesos/{procesoId}/descargar-datos-posiciones")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadDatos(@PathVariable Long procesoId) {
		Resource resource = posicionService.downloadExcelDatos();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@PostMapping("posiciones/eliminar-datos")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Proceso proceso) {
		posicionService.deleteDatos(proceso);
	}
	
	@PostMapping("/posiciones/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll() {
		posicionService.deleteAll();
	}
}
