package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Canal;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaEps;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Linea;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.ProductoCanal;
import com.ms.encuestas.models.ProductoSubcanal;

@CrossOrigin(origins={})
@Repository
public class EncuestaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public boolean hasEncuesta(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		
		String sql = "SELECT COUNT(1)\n" +
		             "  FROM encuestas\n" +
				     " WHERE proceso_id=:proceso_id\n" +
		             "   AND posicion_codigo=:posicion_codigo\n" +
		             "   AND encuesta_tipo_id=:encuesta_tipo_id";
		
		return plantilla.queryForObject(sql, paramMap, Long.class) == 1;
	}
	
	public void insertEncuestaCabecera(Justificacion justificacion, String observaciones, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		String sql = "INSERT INTO encuestas(proceso_id,encuesta_tipo_id,posicion_codigo,estado,justificacion_id,justificacion_detalle,observaciones,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:proceso_id,:encuesta_tipo_id,:posicion_codigo,:estado,:justificacion_id,:justificacion_detalle,:observaciones,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("estado", 1);
		paramMap.put("justificacion_id", justificacion.getId());
		paramMap.put("justificacion_detalle", justificacion.getDetalle());
		paramMap.put("observaciones", observaciones);
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);        
		plantilla.update(sql,paramMap);
	}
	
	public EncuestaEmpresa getEncuestaEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {			
		String sql = "SELECT A.justificacion_id,\n" + 
			         "       B.nombre justificacion_nombre,\n" +
			         "       A.justificacion_detalle,\n" +
			         "       B.fecha_creacion justificacion_fecha_cre,\n" + 
			         "       B.fecha_actualizacion justificacion_fecha_act,\n" + 
			         "       A.observaciones\n" + 
			         "  FROM encuestas A\n" + 
			         "  LEFT JOIN justificaciones B ON A.justificacion_id=B.id\n" + 
			         " WHERE proceso_id=:proceso_id\n" + 
			         "   AND posicion_codigo=:posicion_codigo\n" + 
			         "   AND encuesta_tipo_id=:encuesta_tipo_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		EncuestaEmpresa encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaMapper());
		
		sql = "SELECT A.id,\n" + 
		      "       A.nombre,\n" +
		      "       A.fecha_creacion,\n" +
		      "       A.fecha_actualizacion,\n" +
		      "       NVL(B.porcentaje*100,0) porcentaje\n" + 
		      "  FROM empresas A\n" + 
			  "  LEFT JOIN encuesta_empresa B\n" +
			  "    ON A.id=B.empresa_id\n" +
			  "   AND B.proceso_id=:proceso_id\n" +
			  "   AND B.posicion_codigo=:posicion_codigo\n" + 
		      " WHERE A.fecha_eliminacion IS NULL\n" +
		      " ORDER BY A.id";
		encuesta.setLstItems(plantilla.query(sql, paramMap, new EmpresaMapper()));		
		return encuesta;
	}
	
	public EncuestaCentro getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel) {
		String sql = "SELECT A.justificacion_id,\n" + 
			         "       B.nombre justificacion_nombre,\n" +
			         "       A.justificacion_detalle,\n" +
			         "       B.fecha_creacion justificacion_fecha_cre,\n" + 
			         "       B.fecha_actualizacion justificacion_fecha_act,\n" + 
			         "       A.observaciones\n" + 
			         "  FROM encuestas A\n" + 
			         "  LEFT JOIN justificaciones B ON A.justificacion_id=B.id\n" + 
			         " WHERE proceso_id=:proceso_id\n" + 
			         "   AND posicion_codigo=:posicion_codigo\n" + 
			         "   AND encuesta_tipo_id=:encuesta_tipo_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("proceso_id", procesoId);
		paramMap.put("empresa_id", empresaId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		paramMap.put("nivel", nivel);
		EncuestaCentro encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaCentroMapper());
		
		sql = "SELECT A.id,\n" +
			  "       A.codigo,\n" +
			  "       A.nombre,\n" +
			  "       A.nivel,\n" +
			  "       A.fecha_creacion,\n" +
			  "       A.fecha_actualizacion,\n" +
			  "       NVL(B.porcentaje*100,0) porcentaje\n" + 
			  "  FROM centros A\n" + 
			  "  LEFT JOIN encuesta_centro B\n" +
			  "    ON A.id=B.centro_id\n" +
			  "   AND B.proceso_id=:proceso_id\n" +
			  "   AND B.posicion_codigo=:posicion_codigo\n" + 
			  " WHERE A.empresa_id=:empresa_id\n" +
			  "   AND A.nivel>:nivel\n" +
			  "   AND A.fecha_eliminacion IS NULL\n" +
			  " ORDER BY A.nivel,A.id";
		encuesta.setLstItems(plantilla.query(sql, paramMap, new CentroMapper()));		
		return encuesta;
	}
	
	public void saveEncuestaCentroDetalle(List<Centro> lstCentros, Long procesoId, String posicionCodigo) {
		
	}
	
	public EncuestaLinea getEncuestaLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
	}
	
	public void saveEncuestaLineaDetalle(List<Linea> lstCentros, Long procesoId, String posicionCodigo) {
		
	}	
	
	public EncuestaCanal getEncuestaCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
	}
	
	public void saveEncuestaCanalDetalle(List<Canal> lstCentros, Long procesoId, String posicionCodigo) {
		
	}
	
	public EncuestaLineaCanal getEncuestaLineaCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
	}
	
	public void saveEncuestaLineaCanalDetalle(List<LineaCanal> lstCentros, Long procesoId, String posicionCodigo) {
		
	}
	
	public EncuestaProductoCanal getEncuestaProductoCanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
	}
	
	public void saveEncuestaProductoCanalDetalle(List<ProductoCanal> lstCentros, Long procesoId, String posicionCodigo) {
		
	}
	
	public EncuestaProductoSubcanal getEncuestaProductoSubcanal(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		return null;
	}
	
	public void saveEncuestaProductoSubcanalDetalle(List<ProductoSubcanal> lstCentros, Long procesoId, String posicionCodigo) {
		
	}
}
