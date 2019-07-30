package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Encuesta;
import com.ms.encuestas.models.Justificacion;

public interface EncuestaServiceI {
	public Encuesta getEncuestaEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveEncuestaCabecera(Justificacion justificacion, String observaciones, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveEncuestaEmpresaDetalle(List<Empresa> lstItems, Long procesoId, String posicionCodigo);
	public void saveEncuestaCentroDetalle(List<Centro> lstItems, Long procesoId, String posicionCodigo);
	public List<Centro> getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo);
	public void saveLstCentros(List<Centro> lstEmpresas, Long procesoId, String posicionCodigo);
}
