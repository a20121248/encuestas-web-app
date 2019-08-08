package com.ms.encuestas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.repositories.EncuestaRepository;
import com.ms.encuestas.repositories.PosicionRepository;

@Service
public class EncuestaService implements EncuestaServiceI {
    @Autowired
    private EncuestaRepository encuestaRepository;
    @Autowired
    private PosicionRepository posicionRepository;
    
    public Justificacion getJustificacionDefault() {
		Justificacion justificacion = new Justificacion();
		justificacion.setId(new Long(0));
		justificacion.setNombre("Sin nombre");
		justificacion.setDetalle("Sin detalle.");
		Date fecha = new Date();
		justificacion.setFechaCreacion(fecha);
		justificacion.setFechaActualizacion(fecha);
		return justificacion;
    }
    
    public String getObservacionesDefault() {
    	return "Sin observaciones.";
    }
    
    @Override
    public EncuestaEmpresa getEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo, encuestaTipoId);
    }

	@Override
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstEmpresas(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}
	
	@Override
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaCentro(empresaId, procesoId, posicionCodigo, encuestaTipoId, nivel, perfilId);
	}
	
	@Override
	public void saveCentro(EncuestaCentro encuesta, Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstCentros(encuesta.getLstItems(), empresaId, procesoId, posicionCodigo);
	}

	@Override
	public EncuestaObjeto getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaLinea(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@Override
	public void saveLinea(EncuestaObjeto encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstLinea(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}

	@Override
	public EncuestaObjetoObjetos getLineaCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaLineaCanales(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@Override
	public void saveLineaCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstLineaCanales(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}

	@Override
	public EncuestaObjetoObjetos getProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaProductoCanales(procesoId, posicionCodigo, encuestaTipoId, lineaId);
	}

	@Override
	public void saveProductoCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstProductoCanales(encuesta.getLstItems(), procesoId, posicionCodigo);
	}

	@Override
	public EncuestaObjetoObjetos getProductoSubcanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaProductoSubcanales(procesoId, posicionCodigo, encuestaTipoId, lineaId, canalId);
	}

	@Override
	public void saveProductoSubcanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
    	System.out.println("AQUI en el SERVICE save producto subcanales");
		encuestaRepository.insertLstProductoSubcanales(encuesta.getLstItems(), procesoId, posicionCodigo);
	}
}
