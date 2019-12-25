package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Justificacion;

public interface JustificacionServiceI {
	public long count();
	public List<Justificacion> findAll();
	public Justificacion findById(Long id);
	public int save(Justificacion justificacion);
	public void delete(Justificacion justificacion);
	public void deleteById(Long id);
	public Justificacion softDelete(Justificacion justificacion);
	public Justificacion softUndelete(Justificacion justificacion);
}
