package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Proceso;

public interface ProcesoServiceI {
	public Proceso getCurrentProceso();
	public long count();
	public List<Proceso> findAll();
	public Proceso findById(Long id);
	public int save(Proceso proceso);
	public void delete(Proceso proceso);
	public void deleteById(Long id);
}
