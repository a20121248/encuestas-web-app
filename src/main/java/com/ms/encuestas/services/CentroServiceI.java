package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Centro;

public interface CentroServiceI {
	public Long count(Long empresaId);
	public List<String> findAllCodigos();
	public List<Centro> findAll();
	public Centro findById(Long id);
	public Centro findByCodigo(String codigo);
	public Centro save(Centro centro);
	public void delete(Centro centro);
	public void deleteById(Long id);
	public void deleteAllCentros();
	public void deleteAllLineasEps();
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
