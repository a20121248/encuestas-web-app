package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.repositories.JustificacionRepository;

@Service
public class JustificacionService implements JustificacionServiceI {
	@Autowired
	private JustificacionRepository justificacionRepository;

	public long count() {
		return justificacionRepository.count();
	}

	public List<Justificacion> findAll() {
		return justificacionRepository.findAll();
	}

	public Justificacion findById(Long id) {
		return justificacionRepository.findById(id);
	}

	public int save(Justificacion justificacion) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(Justificacion justificacion) {
		// TODO Auto-generated method stub

	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
