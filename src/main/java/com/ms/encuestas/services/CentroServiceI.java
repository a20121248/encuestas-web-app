package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Centro;

public interface CentroServiceI {
	
	public List<Centro> findAll() throws Exception;
	public Centro findById(Long id);
	public Centro findByCodigo(String codigo);
	public Centro save(Centro centro);
	public void delete(Centro centro);
}
