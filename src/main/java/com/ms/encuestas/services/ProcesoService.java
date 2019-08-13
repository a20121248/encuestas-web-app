package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.repositories.ProcesoRepository;

@Service
public class ProcesoService implements ProcesoServiceI {
	@Autowired
	private ProcesoRepository procesoRepository;
	
	@Override
	public Proceso getCurrentProceso() {
		return procesoRepository.getCurrentProceso();
	}
	
	@Override
	public long count() {
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
	public int save(Proceso proceso) {
		// TODO Auto-generated method stub
		return 0;
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
