package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Tipo;

public interface CentroServiceI {
	public Long count(Long empresaId);
	public List<String> findAllCodigos();
	public List<Tipo> findAllTipos();
	public List<Centro> findAll();
	public Centro findById(Long id);
	public Centro findByCodigo(String codigo);
	public Centro insert(Centro centro);
	public Centro update(Centro centro);
	public void delete(Centro centro);
	public void deleteById(Long id);
	public Centro softDelete(Centro centro);
	public Centro softUndelete(Centro centro);
	public void deleteAllCentros();
	public void deleteAllLineasEps();
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
