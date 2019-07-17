package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.repositories.EmpresaRepository;

@Service
public class EmpresaService implements EmpresaServiceI {
    @Autowired
    private EmpresaRepository empresaRepository;
    
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}
    
	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

	public Empresa findById(Long id) {
		return empresaRepository.findById(id);
	}
	
	public int save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	public void delete(Empresa empresa) {
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}
