package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;

public interface EncuestaServiceI {
	public List<Empresa> getEncuestaEmpresa(Long procesoId, String posicionCodigo);
	public void saveLstEmpresas(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo);
	public List<Centro> getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo);
	public void saveLstCentros(List<Centro> lstEmpresas, Long procesoId, String posicionCodigo);
}
