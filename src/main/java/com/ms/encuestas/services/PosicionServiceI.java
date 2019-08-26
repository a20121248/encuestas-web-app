package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import com.ms.encuestas.models.Posicion;

public interface PosicionServiceI {
	public Long count();
	public List<Posicion> findAll();
	public Posicion findByCodigo(String codigo);
	public Posicion findByCodigoWithAreaAndCentro(String codigo);
	public Posicion findByCodigoWithArea(String codigo);
	public Posicion findByCodigoWithCentro(String codigo);
	public Posicion save(Posicion posicion);
	public void delete(Posicion posicion);
	public void deleteById(Long id);
	public void processExcel(Long procesoId, InputStream file);
}
