package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Proceso;

public interface ProcesoServiceI {
	public Proceso getCurrentProceso();
	public Long count();
	public List<Proceso> findAll();
	public Proceso findById(Long id);
	public Proceso findByCodigo(String codigo);
	public int store(Proceso proceso);
	public int update(Proceso proceso);
	public void delete(Proceso proceso);
	public void deleteById(Long id);
}
