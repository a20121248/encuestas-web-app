package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Empresa;

public interface EncuestaServiceI {
	public List<Empresa> getEncuestaEmpresa(long procesoId, String posicionCodigo);
	public int saveEncuestaEmpresa(Empresa empresa, long procesoId, String posicionCodigo);
}
