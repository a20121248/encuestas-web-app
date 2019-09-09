package com.ms.encuestas.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.repositories.ObjetoRepository;

@Service
public class ObjetoService implements ObjetoServiceI {
	private Logger logger = LoggerFactory.getLogger(ObjetoService.class);

	@Autowired
	private ObjetoRepository objetoRepository;
	
	@Override
	public Long count(Long objetoTipoId) {
		return objetoRepository.count(objetoTipoId);
	}

	@Override
	public List<Objeto> findAll(Long objetoTipoId) {
		return objetoRepository.findAll(objetoTipoId);
	}
	
	@Override
	public List<Objeto> findAllLineas() {
		try {
			return objetoRepository.findAll(new Long(1));
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ninguna línea registrada en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}
	
	@Override
	public List<Objeto> findAllCanales() {
		try {
			return objetoRepository.findAll(new Long(2));
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún canal registrado en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}
	
	@Override
	public List<Objeto> findAllProductos() {
		return objetoRepository.findAll(new Long(3));
	}
	
	@Override
	public List<Objeto> findAllSubcanales() {
		return objetoRepository.findAll(new Long(4));
	}

	@Override
	public Objeto findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Objeto objeto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Objeto objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}
