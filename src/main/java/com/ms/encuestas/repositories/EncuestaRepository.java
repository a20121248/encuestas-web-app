package com.ms.encuestas.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Encuesta;
import com.ms.encuestas.models.Justificacion;

@CrossOrigin(origins={})
@Repository
public class EncuestaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public void saveEncuestaCabecera(Justificacion justificacion, String observaciones, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		String sql = "DELETE FROM encuestas\n" +
                     " WHERE proceso_id=:proceso_id\n" +
	                 "   AND posicion_codigo=:posicion_codigo\n" +
                     "   AND encuesta_tipo_id=:encuesta_tipo_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		plantilla.update(sql,paramMap);
		
		sql = "INSERT INTO encuestas(proceso_id,encuesta_tipo_id,posicion_codigo,estado,justificacion_id,justificacion_detalle,observaciones,fecha_creacion,fecha_actualizacion)\n" +
              "VALUES(:proceso_id,:encuesta_tipo_id,:posicion_codigo,:estado,:justificacion_id,:justificacion_detalle,:observaciones,:fecha_creacion,:fecha_actualizacion)";
		paramMap = new HashMap<String, Object>();
		paramMap = new HashMap<String, Object>();
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
	
	public List<Empresa> findAll() {
		String sql = "SELECT * FROM empresas";
		return plantilla.query(sql, new EmpresaMapper());
	}

	public Encuesta getEncuestaEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		// TO-DO: Tal vez se pueda hacer mejor
		// Verifico si el usuario tiene encuesta, si no, le creo su cabecera.
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		
		String sql = "SELECT COUNT(1) CNT\n" +
		             "  FROM encuestas" +
				     " WHERE proceso_id=:proceso_id\n" +
		             "   AND posicion_codigo=:posicion_codigo\n" +
		             "   AND encuesta_tipo_id=:encuesta_tipo_id";
		if (plantilla.queryForObject(sql, paramMap, Long.class) == 0) {
			sql = "INSERT INTO encuestas(proceso_id,encuesta_tipo_id,posicion_codigo,estado,justificacion_id,justificacion_detalle,observaciones,fecha_creacion,fecha_actualizacion)\n" +
		          "VALUES(:proceso_id,:encuesta_tipo_id,:posicion_codigo,:estado,:justificacion_id,:justificacion_detalle,:observaciones,:fecha_creacion,:fecha_actualizacion)";
				paramMap = new HashMap<String, Object>();
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("encuesta_tipo_id", encuestaTipoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("estado", 1);
				paramMap.put("justificacion_id", 0);
				paramMap.put("justificacion_detalle", "No aplica");
				paramMap.put("observaciones", "No aplica");
				Date fecha = new Date();
				paramMap.put("fecha_creacion", fecha);
				paramMap.put("fecha_actualizacion", fecha);
		        plantilla.update(sql,paramMap);
		};

		
		sql = "SELECT A.justificacion_id,\n" + 
			  "       B.nombre justificacion_nombre,\n" +
			  "       NVL(A.justificacion_detalle,'No aplica') justificacion_detalle,\n" +
			  "       B.fecha_creacion justificacion_fecha_cre,\n" + 
			  "       B.fecha_actualizacion justificacion_fecha_act,\n" + 
			  "       NVL(A.observaciones,'No aplica') observaciones\n" + 
			  "  FROM encuestas A\n" + 
			  "  LEFT JOIN justificaciones B ON A.justificacion_id=B.id\n" + 
			  " WHERE proceso_id=:proceso_id\n" + 
			  "   AND posicion_codigo=:posicion_codigo\n" + 
			  "   AND encuesta_tipo_id=:encuesta_tipo_id";
		
		Encuesta encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaMapper());
		
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
		paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		encuesta.setLstItems(plantilla.query(sql, paramMap, new EmpresaMapper()));
		
		return encuesta;
	}
	
	public List<Centro> getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo) {
		String sql = "SELECT A.id,\n" + 
					 "       A.codigo,\n" +
			         "       A.nombre,\n" +
			         "       A.nivel,\n" +
			         "       A.fecha_creacion,\n" +
			         "       NVL(B.porcentaje,0) porcentaje\n" + 
			         "  FROM centros A\n" + 
			         "  LEFT JOIN encuesta_centro B\n" +
			         "    ON A.id=B.centro_id\n" +
			         "   AND B.proceso_id=:proceso_id\n" +
			         "   AND B.posicion_codigo=:posicion_codigo\n" + 
			         " WHERE A.empresa_id=:empresa_id\n" + 
			         "   AND A.fecha_eliminacion IS NULL\n" +
			         " ORDER BY A.id";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("empresa_id", empresaId);
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		return plantilla.query(sql, paramMap, new CentroMapper());
	}
	
	public void saveEncuestaEmpresaDetalle(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
		String sql = "DELETE FROM encuesta_empresa\n" +
	                 " WHERE proceso_id=:proceso_id\n" +
				     "   AND posicion_codigo=:posicion_codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		plantilla.update(sql,paramMap);
		
		for (Empresa empresa: lstEmpresas) {
			sql = "INSERT INTO encuesta_empresa(proceso_id,posicion_codigo,empresa_id,porcentaje)\n"+
                  "VALUES(:proceso_id,:posicion_codigo,:empresa_id,:porcentaje)";
			paramMap = new HashMap<String, Object>();
			paramMap.put("proceso_id", procesoId);
			paramMap.put("posicion_codigo", posicionCodigo);
	        paramMap.put("empresa_id", empresa.getId());
	        paramMap.put("porcentaje", empresa.getPorcentaje()/100);
	        plantilla.update(sql,paramMap);
		}
	}
	
	public void saveEncuestaCentroDetalle(List<Centro> lstCentros, Long procesoId, String posicionCodigo) {
		String sql;
		for (Centro centro: lstCentros) {
			sql = "INSERT INTO encuesta_empresa(proceso_id,posicion_codigo,empresa_id,porcentaje)\n"+
                  "VALUES(:proceso_id,:posicion_codigo,:empresa_id,:porcentaje)";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proceso_id", procesoId);
			paramMap.put("posicion_codigo", posicionCodigo);
	        paramMap.put("empresa_id", centro.getId());
	        paramMap.put("porcentaje", centro.getPorcentaje());
	        plantilla.update(sql,paramMap);
		}
	}
	
	public void saveEncuestaCentro(List<Centro> lstCentros, Long procesoId, String posicionCodigo) {
		String sql = "DELETE FROM encuesta_centro\n" +
		             " WHERE posicion_codigo=:posicion_codigo\n" +
		             "   AND proceso_id=:proceso_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("proceso_id", procesoId);
		plantilla.update(sql,paramMap);
		
		for (Centro centro: lstCentros) {
			sql = "INSERT INTO encuesta_centro(proceso_id,posicion_codigo,centro_id,porcentaje)\n" +
                  "VALUES (:proceso_id,:posicion_codigo,:centro_id,:porcentaje)";
			paramMap = new HashMap<String, Object>();
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("proceso_id", procesoId);
	        paramMap.put("centro_id", centro.getId());
	        paramMap.put("porcentaje", centro.getPorcentaje());
	        plantilla.update(sql,paramMap);
		}
	}
}
