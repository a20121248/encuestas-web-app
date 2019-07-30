package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Encuesta;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.repositories.EncuestaRepository;

@Service
public class EncuestaService implements EncuestaServiceI {
    @Autowired
    private EncuestaRepository encuestaRepository;
    
    @Override
    public Encuesta getEncuestaEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo, encuestaTipoId);
    }

	@Override
	public void saveEncuestaCabecera(Justificacion justificacion, String observaciones, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		encuestaRepository.saveEncuestaCabecera(justificacion, observaciones, procesoId, posicionCodigo, encuestaTipoId);		
	}
	
	@Override
	public void saveEncuestaEmpresaDetalle(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
		encuestaRepository.saveEncuestaEmpresaDetalle(lstEmpresas, procesoId, posicionCodigo);
	}
	
	@Override
	public void saveEncuestaCentroDetalle(List<Centro> lstEmpresas, Long procesoId, String posicionCodigo) {
		encuestaRepository.saveEncuestaCentroDetalle(lstEmpresas, procesoId, posicionCodigo);
	}

	@Override
	public List<Centro> getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo) {
		System.out.println(String.format("empresaId: %d", empresaId));
		System.out.println(String.format("procesoId: %d", procesoId));
		System.out.println(String.format("posicionCodigo: %s", posicionCodigo));
		return encuestaRepository.getEncuestaCentro(empresaId, procesoId, posicionCodigo);
	}
	
	@Override
	public void saveLstCentros(List<Centro> lstCentros, Long procesoId, String posicionCodigo) {
		encuestaRepository.saveEncuestaCentro(lstCentros, procesoId, posicionCodigo);
	}
}
