package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Centro;

public interface CentroServiceI {
	public Long count();
	public List<Centro> findAll();
	public Centro findById(Long id);
	public Centro save(Centro centro);
	public void delete(Centro centro);
	public void deleteById(Long id);
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
