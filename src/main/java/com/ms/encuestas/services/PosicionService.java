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

	public Long count() {
		return posicionRepository.count();
	}

	public List<Posicion> findAll() {
		return posicionRepository.findAll();
	}

	public Posicion findByCodigo(String codigo) {
		return posicionRepository.findByCodigo(codigo);
	}

	public Posicion findByCodigoWithAreaAndCentro(String codigo) {
		return posicionRepository.findByCodigoWithAreaAndCentro(codigo);
	}
	
	public Posicion findByCodigoWithArea(String codigo) {
		return posicionRepository.findByCodigoWithArea(codigo);
	}
	
	public Posicion findByCodigoWithCentro(String codigo) {
		return posicionRepository.findByCodigoWithCentro(codigo);
	}

	public Posicion save(Posicion posicion) {
		return null;
	}

	public void delete(Posicion posicion) {
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
