package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.repositories.EmpresaRepository;
import com.ms.encuestas.repositories.EncuestaRepository;

@Service
public class EncuestaService implements EncuestaServiceI {
    @Autowired
    private EncuestaRepository encuestaRepository;
    
    public List<Empresa> getEncuestaEmpresa(long procesoId, String posicionCodigo) {
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo);
    }

	@Override
	public void saveLstEmpresas(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
		encuestaRepository.saveEncuestaEmpresa(lstEmpresas, procesoId, posicionCodigo);
	}    
}
