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
	public Area insert(Area area);
	public Area update(Area area);
	public void deleteById(Long id);
	public Area softDelete(Area area);
	public Area softUndelete(Area area);
	public void deleteAll();
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
