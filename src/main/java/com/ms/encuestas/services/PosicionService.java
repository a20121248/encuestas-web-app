package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.repositories.PosicionRepository;

@Service
public class PosicionService implements PosicionServiceI {
	@Autowired
	private PosicionRepository posicionRepository;

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Posicion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Posicion findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Posicion save(Posicion posicion) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Posicion posicion) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
}
