package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Area;

public interface AreaServiceI {
	public Long count();
	public List<String> findAllCodigos();
	public List<Area> findAll();
	public Area findById(Long id);
	public Area findByCodigo(String codigo);
	public Area save(Area area);
	public void delete(Area area);
	public void deleteById(Long id);
	public void deleteAll();
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
