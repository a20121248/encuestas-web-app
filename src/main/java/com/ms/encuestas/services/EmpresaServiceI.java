package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Empresa;

public interface EmpresaServiceI {
	public Long count();	
	public List<Empresa> findAll();
	public Empresa findById(Long id);
}
