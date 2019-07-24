package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.repositories.EmpresaRepository;
import com.ms.encuestas.repositories.EncuestaRepository;

@Service
public class EncuestaService implements EncuestaServiceI {
    @Autowired
    private EncuestaRepository encuestaRepository;
    
    @Override
    public List<Empresa> getEncuestaEmpresa(Long procesoId, String posicionCodigo) {
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo);
    }

	@Override
	public void saveLstEmpresas(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
		encuestaRepository.saveEncuestaEmpresa(lstEmpresas, procesoId, posicionCodigo);
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
