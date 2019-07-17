package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Area;

public interface AreaServiceI {
	public long count();
	public List<Area> findAll();
	public List<Area> findAllWithDivision();
	public Area findById(Long id);
	public Area findByIdWithDivision(Long id);
	public Area save(Area area);
	public void delete(Area area);
	public void deleteById(Long id);
}
