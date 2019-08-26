package com.ms.encuestas.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.repositories.ObjetoRepository;
import com.ms.encuestas.repositories.ProcesoRepository;

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
