package com.ms.encuestas.services;

import com.ms.encuestas.models.EncuestaEmpresa;

import java.util.List;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.Usuario;

public interface EncuestaServiceI {
	public EncuestaEmpresa getEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);	
	public Resource downloadEmpresaExcel(Long procesoId, String posicionCodigo);
	
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId);
	public void saveCentro(EncuestaCentro encuesta, Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public Resource downloadCentroExcel(Long empresaId, Long procesoId, String posicionCodigo, int nivel, Long perfilId);

	public EncuestaObjeto getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId);
	public void saveLinea(EncuestaObjeto encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public Resource downloadLineaExcel(Long procesoId, String posicionCodigo, Long perfilId);

	public EncuestaObjetoObjetos getProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId, Long lineaId);
	public void saveProductoCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId);
	public Resource downloadProductoCanalesExcel(Long procesoId, String posicionCodigo, Long perfilId, Long lineaId);
	
	public EncuestaObjetoObjetos getLineaCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId);
	public void saveLineaCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId);
	public Resource downloadLineaCanalesExcel(Long procesoId, String posicionCodigo, Long perfilId);
	
	public EncuestaObjetoObjetos getProductoSubcanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId);
	public void saveProductoSubcanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId);
	public Resource downloadProductoSubcanalesExcel(Long procesoId, String posicionCodigo, Long lineaId, Long canalId);
	
	public void replicarEncuestas(Long procesoId, String posicionCodigo, List<Usuario> usuarios);
	public void eliminarEncuestas(Long procesoId, String posicionCodigo);
}
