package com.ms.encuestas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaEps;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
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
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
    		if (!posicionRepository.exists(procesoId, posicionCodigo))
    			return null;
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo, encuestaTipoId);
    }

	@Override
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveEmpresa(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel) {
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
    		if (!posicionRepository.exists(procesoId, posicionCodigo))
    			return null;
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaCentro(empresaId, procesoId, posicionCodigo, encuestaTipoId, nivel);
	}
	
	@Override
	public void saveCentro(EncuestaCentro encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveEps(lstItems, justificacion, observaciones, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaLinea getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
		//return encuestaRepository.getLinea(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@Override
	public void saveLinea(EncuestaLinea encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveLinea(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaCanal getCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
		//return encuestaRepository.getLinea(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@Override
	public void saveCanal(EncuestaCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveLinea(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaLineaCanal getLineaCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
		//return encuestaRepository.getLineaCanal(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@Override
	public void saveLineaCanal(EncuestaLineaCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveLineaCanal(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaProductoSubcanal getProductoSubcanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
		//return encuestaRepository.getProductoSubcanal(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@Override
	public void saveProductoSubcanal(EncuestaProductoSubcanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveEncuestaProductoSubcanal(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public EncuestaProductoCanal getProductoCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
		//return encuestaRepository.getProductoSubcanal(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@Override
	public void saveProductoCanal(EncuestaProductoCanal encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		//encuestaRepository.saveEncuestaProductoSubcanal(encuesta, procesoId, posicionCodigo, encuestaTipoId);		
	}
}
