package com.ms.encuestas.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.repositories.EmpresaRepository;

@Service
public class EmpresaService implements EmpresaServiceI {
	private Logger logger = LoggerFactory.getLogger(EmpresaService.class);
    @Autowired
    private EmpresaRepository empresaRepository;
    
	public Long count() {
		return empresaRepository.count();
	}
    
	public List<Empresa> findAll() {
		try {
			return empresaRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ninguna empresa registrada en la base de datos.");
			return new ArrayList<Empresa>();
		}
	}

	public Empresa findById(Long id) {
		return empresaRepository.findById(id);
	}
}
