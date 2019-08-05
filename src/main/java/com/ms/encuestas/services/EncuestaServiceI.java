package com.ms.encuestas.services;

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEps;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;

public interface EncuestaServiceI {
	public EncuestaEmpresa getEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);	
	
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel);
	public void saveCentro(EncuestaCentro encuesta, Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaLinea getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveLinea(EncuestaLinea encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaCanal getCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveCanal(EncuestaCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaLineaCanal getLineaCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveLineaCanal(EncuestaLineaCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaProductoSubcanal getProductoSubcanal(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveProductoSubcanal(EncuestaProductoSubcanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	
	public EncuestaProductoCanal getProductoCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveProductoCanal(EncuestaProductoCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
}
