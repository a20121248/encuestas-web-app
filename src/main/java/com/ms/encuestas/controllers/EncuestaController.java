package com.ms.encuestas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEps;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;
import com.ms.encuestas.services.EncuestaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EncuestaController {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

	@Autowired
	private EncuestaServiceI encuestaService;
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/empresas")
	@Transactional(readOnly = true)
	public EncuestaEmpresa getEmpresa(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getEmpresa(procesoId, posicionCodigo, new Long(1));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public void createEmpresa(/*Authentication authentication, */@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaEmpresa encuesta) {
		//User user = (User) authentication.getPrincipal();System.out.println("POST " + user.getUsername());
		this.encuestaService.saveEmpresa(encuesta, procesoId, posicionCodigo, new Long(1));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/eps")
	@Transactional(readOnly = true)
	public EncuestaEps getEps(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getEps(procesoId, posicionCodigo, new Long(2));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/eps")
	@ResponseStatus(HttpStatus.CREATED)
	public void createEps(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaEps encuesta) {
		this.encuestaService.saveEps(encuesta, procesoId, posicionCodigo, new Long(2));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/centro")
	@Transactional(readOnly = true)
	public EncuestaCentro getCentro(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getCentro(procesoId, posicionCodigo, new Long(3));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/centro")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCentro(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaCentro encuesta) {
		this.encuestaService.saveCentro(encuesta, procesoId, posicionCodigo, new Long(3));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea")
	@Transactional(readOnly = true)
	public EncuestaLinea getLinea(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getLinea(procesoId, posicionCodigo, new Long(4));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLinea(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaLinea encuesta) {
		this.encuestaService.saveLinea(encuesta, procesoId, posicionCodigo, new Long(4));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/canal")
	@Transactional(readOnly = true)
	public EncuestaCanal getCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getCanal(procesoId, posicionCodigo, new Long(5));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/canal")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaCanal encuesta) {
		this.encuestaService.saveCanal(encuesta, procesoId, posicionCodigo, new Long(5));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea-canal")
	@Transactional(readOnly = true)
	public EncuestaLineaCanal getLineaCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getLineaCanal(procesoId, posicionCodigo, new Long(6));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea-canal")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLineaCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaLineaCanal encuesta) {
		this.encuestaService.saveLineaCanal(encuesta, procesoId, posicionCodigo, new Long(6));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-subcanal")
	@Transactional(readOnly = true)
	public EncuestaProductoSubcanal getProductoSubcanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getProductoSubcanal(procesoId, posicionCodigo, new Long(7));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-subcanal")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductoSubcanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaProductoSubcanal encuesta) {
		this.encuestaService.saveProductoSubcanal(encuesta, procesoId, posicionCodigo, new Long(7));
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-canal")
	@Transactional(readOnly = true)
	public EncuestaProductoCanal getProductoCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return encuestaService.getProductoCanal(procesoId, posicionCodigo, new Long(8));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-canal")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductoCanal(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaProductoCanal encuesta) {
		this.encuestaService.saveProductoCanal(encuesta, procesoId, posicionCodigo, new Long(8));
	}
}
