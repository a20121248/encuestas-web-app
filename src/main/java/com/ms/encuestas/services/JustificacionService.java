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
		justificacionRepository.delete(justificacion);
	}

	public void deleteById(Long id) {
		justificacionRepository.deleteById(id);
	}

	@Override
	public Justificacion softDelete(Justificacion justificacion) {
		return justificacionRepository.softDelete(justificacion);
	}

	@Override
	public Justificacion softUndelete(Justificacion justificacion) {
		return justificacionRepository.softUndelete(justificacion);
	}
}
