package com.ms.encuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.repositories.ProcesoRepository;

@Service
public class ProcesoService implements ProcesoServiceI {
	@Autowired
	private ProcesoRepository procesoRepository;
	
	public Proceso getCurrentProceso() {
		return procesoRepository.getCurrentProceso();
	}

}
