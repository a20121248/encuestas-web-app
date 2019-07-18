package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Empresa;

public interface EmpresaServiceI {
	public long count();	
	public List<Empresa> findAll();
	public Empresa findById(Long id);
	public int save(Empresa empresa);
	public void delete(Empresa empresa);
	public void deleteById(Long id);
}
