package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Centro;

public interface CentroServiceI {
	public long count();
	public List<Centro> findAll();
	public Centro findById(Long id);
	public Centro save(Centro centro);
	public void delete(Centro centro);
	public void deleteById(Long id);
}
