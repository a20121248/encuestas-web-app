package com.ms.encuestas.services;

import java.util.ArrayList;
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
	public Long count() {
		return procesoRepository.count();
	}
	
	@Override
	public Proceso getCurrentProceso() {
		try {
			return procesoRepository.getCurrentProceso();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No se encontró una encuesta activa en la base de datos.");
			return null;
		}
	}

	@Override
	public List<Proceso> findAll() {
		try {
			return procesoRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ninguna encuesta registrada en la base de datos.");
			return new ArrayList<Proceso>();
		}
	}
	
	@Override
	public Proceso findById(Long id) {
		try {
			return procesoRepository.findById(id);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró la encuesta con ID=%d.", id));
			return null;
		}
	}
	
	@Override
	public Proceso findByCodigo(String codigo) {
		try {
			return procesoRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró la encuesta con código '%s'.", codigo));
			return null;
		}
	}
	
	@Override
	public Proceso insert(Proceso proceso) {
		return procesoRepository.insert(proceso);
	}
	
	@Override
	public Proceso update(Proceso proceso) {
		return procesoRepository.update(proceso);
	}

	@Override
	public void deleteById(Long id) {
		procesoRepository.deleteById(id);
	}
}
