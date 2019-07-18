package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Posicion;

public interface UsuarioServiceI {
	public long count();
	public List<Posicion> findAll();
	public Posicion findById(Long id);
	public Posicion save(Posicion posicion);
	public void delete(Posicion posicion);
	public void deleteById(Long id);
}
