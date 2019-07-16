package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.repositories.EmpresaRepository;

public class EmpresaService implements EmpresaServiceI {
    @Autowired
    private EmpresaRepository empresaRepository;
    
	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

	public Empresa findById(Long id) {
		return empresaRepository.findById(id);
	}
	
	public Empresa save(Empresa centro) {
		return null;
	}
	
	public void delete(Centro centro) {
		return;
	}
}
