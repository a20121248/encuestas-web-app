package com.ms.encuestas.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.repositories.ProcesoRepository;

@Service
public class ProcesoService implements ProcesoServiceI {
	private Logger logger = LoggerFactory.getLogger(ProcesoService.class);

	@Autowired
	private ProcesoRepository procesoRepository;
	
	@Override
	public Proceso getCurrentProceso() {
		try {
			return procesoRepository.getCurrentProceso();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No se encontró un proceso activo en la base de datos.");
			return null;
		}
	}
	
	@Override
	public Long count() {
		return procesoRepository.count();
	}

	@Override
	public List<Proceso> findAll() {
		return procesoRepository.findAll();
	}
	
	@Override
	public Proceso findById(Long id) {
		return procesoRepository.findById(id);
	}
	
	@Override
	public Proceso findByCodigo(String codigo) {
		return procesoRepository.findByCodigo(codigo);
	}
	
	@Override
	public int store(Proceso proceso) {
		return procesoRepository.insert(proceso);
	}
	
	@Override
	public int update(Proceso proceso) {
		return procesoRepository.update(proceso);
	}

	@Override
	public void delete(Proceso proceso) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}
}
