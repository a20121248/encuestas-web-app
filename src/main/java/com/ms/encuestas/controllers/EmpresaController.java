package com.ms.encuestas.controllers;

import java.awt.PageAttributes.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.services.EmpresaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EmpresaController {
	@Autowired
	private EmpresaServiceI empresaService;

	@GetMapping("/empresas")
	public List<Empresa> index() throws Exception {
		return empresaService.findAll();
	}

	@GetMapping("/empresas/{id}")
	public Empresa show(@PathVariable Long id) {
		return this.empresaService.findById(id);
	}

	@PostMapping("/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa create(@RequestBody Empresa empresa) {
		this.empresaService.save(empresa);
		return empresa;
	}

	@PutMapping("/empresas/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa update(@RequestBody Empresa empresa, @PathVariable Long id) {
		Empresa currentEmpresa = this.empresaService.findById(id);
		
		currentEmpresa.setNombre(empresa.getNombre());
		this.empresaService.save(currentEmpresa);
		
		return currentEmpresa;
	}

	@DeleteMapping("/empresas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Empresa currentCentro = this.empresaService.findById(id);
		this.empresaService.delete(currentCentro);
	}
}
