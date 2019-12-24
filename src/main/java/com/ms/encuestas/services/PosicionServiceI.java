package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;

public interface PosicionServiceI {
	public Long count();
	public Long countDatos(Long procesoId);
	public List<String> findAllCodigos();
	public List<String> findAllPosicionCodigosByProceso(Proceso proceso);
	public List<String> findAllUsuarioCodigosByProceso(Proceso proceso);
	public List<Posicion> findAll();
	public void deleteAll();
	public Posicion findByProcesoIdAndUsuarioCodigo(Long procesoId, String usuarioCodigo);
	public Posicion findByCodigo(String codigo);
	public void deleteByCodigo(String codigo);
	public Posicion insert(Posicion posicion);
	public Posicion update(Posicion posicion);
	public void delete(Posicion posicion);
	public void processExcel(InputStream file);
	public Resource downloadExcel();
	public void processExcelDatos(Proceso proceso, InputStream file);
	public Resource downloadExcelDatos(Long procesoId);
	public void deleteDatos(Proceso proceso);
}
