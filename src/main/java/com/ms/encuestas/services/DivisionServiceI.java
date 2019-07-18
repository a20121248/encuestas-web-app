package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Division;

public interface DivisionServiceI {
	public long count();
	public List<Division> findAll();
	public Division findById(Long id);
	public int save(Division division);
	public void delete(Division division);
	public void deleteById(Long id);
}
