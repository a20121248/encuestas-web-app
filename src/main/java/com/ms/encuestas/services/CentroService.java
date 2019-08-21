package com.ms.encuestas.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.JustificacionRepository;

@Service
public class CentroService implements CentroServiceI {
	private Logger logger = LoggerFactory.getLogger(CentroService.class);
	
	@Autowired
	private CentroRepository centroRepository;

	public Long count() {
		return centroRepository.count();
	}

	public List<Centro> findAll() {
		return centroRepository.findAll();
	}

	public Centro findById(Long id) {
		return centroRepository.findById(id);
	}

	public Centro save(Centro centro) {
		return null;
	}

	public void delete(Centro centro) {
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
