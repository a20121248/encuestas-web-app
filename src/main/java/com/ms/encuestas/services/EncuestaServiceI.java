package com.ms.encuestas.services;

import com.ms.encuestas.models.EncuestaEmpresa;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;

public interface EncuestaServiceI {
	public EncuestaEmpresa getEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);	
	
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId);
	public void saveCentro(EncuestaCentro encuesta, Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public Resource downloadCentroExcel(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId);

	public EncuestaObjeto getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId);
	public void saveLinea(EncuestaObjeto encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);

	public EncuestaObjetoObjetos getProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId, Long lineaId);
	public void saveProductoCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId);
	
	public EncuestaObjetoObjetos getLineaCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId);
	public void saveLineaCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaObjetoObjetos getProductoSubcanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId);
	public void saveProductoSubcanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId);
}
