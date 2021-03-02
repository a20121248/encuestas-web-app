package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.services.EmpresaServiceI;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmpresaController {
	@Autowired
	private EmpresaServiceI empresaService;

	@GetMapping("/empresas/cantidad")
	public Long count() {
		return empresaService.count();
	}
	
	@GetMapping("/empresas")
	public List<Empresa> index() throws Exception {
		return empresaService.findAll();
	}

	@GetMapping("/empresas/{id}")
	public Empresa show(@PathVariable Long id) {
		return this.empresaService.findById(id);
	}
}
