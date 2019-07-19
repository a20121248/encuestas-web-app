package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Empresa;

public interface EncuestaServiceI {
	public List<Empresa> getEncuestaEmpresa(long procesoId, String posicionCodigo);
	public void saveLstEmpresas(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo);
}
