package com.ms.encuestas.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.repositories.PerfilRepository;

@Service
public class PerfilService implements PerfilServiceI {
	private Logger logger = LoggerFactory.getLogger(PerfilService.class);
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Override
	public Long count() {
		return perfilRepository.count();
	}

	@Override
	public List<Perfil> findAll() {
		return perfilRepository.findAll();
	}
	
	@Override
	public Perfil findById(Long id) {
		return perfilRepository.findById(id);
	}

	@Override
	public int save(Perfil perfil) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Perfil perfil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
