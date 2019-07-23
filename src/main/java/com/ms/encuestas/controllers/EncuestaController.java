package com.ms.encuestas.controllers;

import java.util.List;

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

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.services.EncuestaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EncuestaController {
	@Autowired
	private EncuestaServiceI encuestaService;
	
	@GetMapping("/encuesta/empresas")
	@Transactional(readOnly = true)
	public List<Empresa> getEncuestaEmpresa() {
		Long procesoId = new Long(2);
		String posicionCodigo = "208829";
		return encuestaService.getEncuestaEmpresa(procesoId, posicionCodigo);
	}
	
	@GetMapping("/encuesta/empresas/{empresaId}/centros")
	@Transactional(readOnly = true)
	public List<Centro> getEncuestaEmpresa(@PathVariable Long empresaId) {
		Long procesoId = new Long(2);
		String posicionCodigo = "208829";
		
		System.out.println(String.format("empresaId: %d", empresaId));
		System.out.println(String.format("procesoId: %d", procesoId));
		System.out.println(String.format("posicionCodigo: %s", posicionCodigo));
		
		return encuestaService.getEncuestaCentro(empresaId, procesoId, posicionCodigo);
	}
	
	/*@PostMapping("/encuesta/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa create(@RequestBody Empresa empresa) {
		System.out.println("aqui:" + empresa.getPorcentaje());
		long procesoId = 2;
		String posicionCodigo = "208829";
		this.encuestaService.saveEncuestaEmpresa(empresa, procesoId, posicionCodigo);
		return empresa;
	}*/
	
	@PostMapping("/encuesta/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody List<Empresa> lstEmpresas) {
		System.out.println("cnt" + lstEmpresas.size());
		Long procesoId = Long.valueOf(2);
		String posicionCodigo = "208829";
		this.encuestaService.saveLstEmpresas(lstEmpresas, procesoId, posicionCodigo);
	}
}
