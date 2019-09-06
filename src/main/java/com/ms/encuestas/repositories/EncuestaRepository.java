package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;

@CrossOrigin(origins={})
@Repository
public class EncuestaRepository {
	private Logger logger = LoggerFactory.getLogger(EncuestaRepository.class);
	
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
		paramMap.put("estado", 0);
		paramMap.put("justificacion_id", justificacion.getId());
		paramMap.put("justificacion_detalle", justificacion.getDetalle());
		paramMap.put("observaciones", observaciones);
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);        
		plantilla.update(sql,paramMap);
	}
	
	public void updateEncuestaCabecera(Justificacion justificacion, String observaciones, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		String sql = "UPDATE encuestas\n" +
					 "   SET estado=:estado,\n" +
					 "       justificacion_id=:justificacion_id,\n" +
					 "       justificacion_detalle=:justificacion_detalle,\n" +
					 "       observaciones=:observaciones,\n" +
					 "		 fecha_creacion=:fecha_creacion,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				     " WHERE proceso_id=:proceso_id\n" + 
				     "	 AND encuesta_tipo_id=:encuesta_tipo_id\n" + 
				     "   AND posicion_codigo=:posicion_codigo";
		
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
		plantilla.update(sql, paramMap);
	}
	
	public EncuestaEmpresa getEncuestaEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {			
		String sql = "SELECT A.justificacion_id,\n" + 
			         "       NVL(B.nombre,'NULL') justificacion_nombre,\n" +
			         "       NVL(A.justificacion_detalle,'NULL') justificacion_detalle,\n" +
			         "       B.fecha_creacion justificacion_fecha_cre,\n" + 
			         "       B.fecha_actualizacion justificacion_fecha_act,\n" + 
			         "       NVL(A.observaciones,'NULL') observaciones\n" + 
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
		      "       NVL(B.porcentaje,0)*100 porcentaje\n" + 
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
	
	public EncuestaCentro getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId) {
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
		paramMap.put("perfil_id", perfilId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		paramMap.put("nivel", nivel);
		EncuestaCentro encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaCentroMapper());
		
		sql = "SELECT A.id centro_id,\n" +
			  "       A.codigo centro_codigo,\n" +
			  "       A.nombre centro_nombre,\n" +
			  "       A.nivel centro_nivel,\n" +
			  "       A.grupo centro_grupo,\n" +
			  "       A.fecha_creacion centro_fecha_creacion,\n" +
			  "       A.fecha_actualizacion centro_fecha_actualizacion,\n" +
			  "       NVL(B.porcentaje,0)*100 porcentaje\n" + 
			  "  FROM centros A\n" + 
			  "  LEFT JOIN encuesta_centro B\n" +
			  "    ON A.id=B.centro_id\n" +
			  "   AND B.proceso_id=:proceso_id\n" +
			  "   AND B.posicion_codigo=:posicion_codigo\n";
			  if (empresaId.intValue() != 2) {
				  sql += "  JOIN perfil_centro C\n" +
                         "    ON C.perfil_id=:perfil_id\n" + 
                         "   AND A.id=C.centro_id\n";
			  }
			  sql +=
			  " WHERE A.empresa_id=:empresa_id\n" +
			  "   AND A.nivel>:nivel\n" +
			  "   AND A.fecha_eliminacion IS NULL\n" +
			  " ORDER BY A.nivel,A.id";
		logger.info(sql);
		encuesta.setLstItems(plantilla.query(sql, paramMap, new CentroMapper()));		
		return encuesta;
	}
	
	public void insertLstEmpresas(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
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
	
	public void insertLstCentros(List<Centro> lstCentros, Long empresaId, Long procesoId, String posicionCodigo) {
		String sql = "DELETE FROM encuesta_centro A\n" + 
					 " WHERE EXISTS (SELECT 1\n" + 
					 "                 FROM centros B\n" + 
					 "                WHERE B.empresa_id=:empresa_id\n" +
					 "                  AND A.centro_id=B.id\n" +
					 "                  AND A.proceso_id=:proceso_id\n" + 
					 "                  AND A.posicion_codigo=:posicion_codigo)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("empresa_id", empresaId);
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		plantilla.update(sql,paramMap);
		
		for (Centro centro: lstCentros) {
			sql = "INSERT INTO encuesta_centro(proceso_id,posicion_codigo,centro_id,porcentaje)\n"+
	              "VALUES(:proceso_id,:posicion_codigo,:centro_id,:porcentaje)";
			paramMap = new HashMap<String, Object>();
			paramMap.put("proceso_id", procesoId);
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("centro_id", centro.getId());
			paramMap.put("porcentaje", centro.getPorcentaje()/100);
			plantilla.update(sql,paramMap);
		}
	}
	
	public EncuestaObjeto getEncuestaLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {			
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
		paramMap.put("perfil_id", perfilId);
		EncuestaObjeto encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaObjetoMapper());
		
		sql = "SELECT DISTINCT B.id,\n" + 
		      "       B.nombre,\n" +
		      "       B.codigo,\n" +
		      "       B.fecha_creacion,\n" +
		      "       B.fecha_actualizacion,\n" +
		      "       NVL(C.porcentaje,0)*100 porcentaje\n" + 
		      "  FROM perfil_linea_canal A\n" +
			  "  JOIN objetos B\n" +
			  "    ON B.objeto_tipo_id=1\n" + // lineas 
			  "   AND B.id=A.linea_id\n" +
			  "  LEFT JOIN encuesta_linea C\n" +
			  "    ON C.proceso_id=:proceso_id\n" +
			  "   AND C.posicion_codigo=:posicion_codigo\n" +
			  "   AND C.linea_id=A.linea_id\n" + 
			  " WHERE A.perfil_id=:perfil_id\n" +
		      " ORDER BY B.nombre";
		
		encuesta.setLstItems(plantilla.query(sql, paramMap, new ObjetoMapper()));		
		return encuesta;
	}
	
	public void insertLstLinea(List<Objeto> lstItems, Long procesoId, String posicionCodigo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		String sql = "DELETE FROM encuesta_linea A\n" + 
					 " WHERE A.proceso_id=:proceso_id\n" + 
					 "   AND A.posicion_codigo=:posicion_codigo";
		plantilla.update(sql,paramMap);
		
		for (Objeto linea: lstItems) {
			sql = "INSERT INTO encuesta_linea(proceso_id,posicion_codigo,linea_id,porcentaje)\n"+
	              "VALUES(:proceso_id,:posicion_codigo,:linea_id,:porcentaje)";
			paramMap = new HashMap<String, Object>();
			paramMap.put("proceso_id", procesoId);
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("linea_id", linea.getId());
			paramMap.put("porcentaje", linea.getPorcentaje()/100);
			plantilla.update(sql,paramMap);
		}
	}
	
	public EncuestaObjetoObjetos getEncuestaLineaCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		paramMap.put("perfil_id", perfilId);
		
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
		EncuestaObjetoObjetos encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaObjetoObjetosMapper());
		
		sql = "SELECT A.linea_id,\n" + 
			  "       B.codigo linea_codigo,\n" + 
			  "       B.nombre linea_nombre,\n" + 
			  "       NVL(D.linea_porcentaje,0)*100 linea_porcentaje,\n" + 
			  "       A.canal_id,\n" + 
			  "       C.codigo canal_codigo,\n" + 
			  "       C.nombre canal_nombre,\n" + 
			  "       NVL(D.canal_porcentaje,0)*100 canal_porcentaje\n" + 
			  "  FROM perfil_linea_canal A\n" + 
			  "  JOIN objetos B\n" + 
			  "    ON B.objeto_tipo_id=1\n" + // lineas 
			  "   AND B.id=A.linea_id\n" + 
			  "  JOIN objetos C\n" + 
			  "    ON C.objeto_tipo_id=2\n" + // canales
			  "   AND C.id=A.canal_id\n" +
			  "  LEFT JOIN encuesta_linea_canal D\n" + 
			  "    ON D.proceso_id=:proceso_id\n" +
			  "   AND D.posicion_codigo=:posicion_codigo\n" +
			  "   AND D.linea_id=A.linea_id\n" + 
			  "   AND D.canal_id=A.canal_id\n" + 
			  " WHERE A.perfil_id=:perfil_id\n" + 
			  " ORDER BY A.linea_id,A.canal_id";
		
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("encuesta_tipo_id", encuestaTipoId);
		paramMap.put("perfil_id", perfilId);
			
	   	encuesta.setLstItems(plantilla.query(sql, paramMap, new EncuestaLineaCanalesExtractor()));		
		return encuesta;
	}
	
	public void insertLstLineaCanales(List<ObjetoObjetos> lstItems, Long procesoId, String posicionCodigo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		String sql = "DELETE FROM encuesta_linea_canal\n" + 
					 " WHERE proceso_id=:proceso_id\n" + 
					 "   AND posicion_codigo=:posicion_codigo";
		plantilla.update(sql,paramMap);
		
		for (ObjetoObjetos linea: lstItems) {
			paramMap = new HashMap<String, Object>();
			paramMap.put("proceso_id", procesoId);
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("linea_id", linea.getObjeto().getId());
			paramMap.put("porcentaje", linea.getObjeto().getPorcentaje()/100);

			sql = "INSERT INTO encuesta_linea(proceso_id,posicion_codigo,linea_id,porcentaje)\n"+
	              "VALUES(:proceso_id,:posicion_codigo,:linea_id,:porcentaje)";
			plantilla.update(sql,paramMap);
			
			for (Objeto canal: linea.getLstObjetos()) {
				sql = "INSERT INTO encuesta_linea_canal(proceso_id,posicion_codigo,linea_id,linea_porcentaje,canal_id,canal_porcentaje)\n"+
		              "VALUES(:proceso_id,:posicion_codigo,:linea_id,:linea_porcentaje,:canal_id,:canal_porcentaje)";
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("linea_id", linea.getObjeto().getId());
				paramMap.put("linea_porcentaje", linea.getObjeto().getPorcentaje()/100);
				paramMap.put("canal_id", canal.getId());
				paramMap.put("canal_porcentaje", canal.getPorcentaje()/100);
				plantilla.update(sql,paramMap);
			}
		}
	}
	
	public EncuestaObjetoObjetos getEncuestaProductoSubcanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId) {			
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
		paramMap.put("linea_id", lineaId);
		paramMap.put("canal_id", canalId);
		EncuestaObjetoObjetos encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaObjetoObjetosMapper());
		
		sql = "SELECT A1.id linea_id,\n" +
			  "       A1.codigo linea_codigo,\n" + 
			  "       A1.nombre linea_nombre,\n" + 
			  "       0 linea_porcentaje,\n" + 
			  "       B1.id canal_id,\n" +
			  "       B1.codigo canal_codigo,\n" +
			  "       B1.nombre canal_nombre,\n" +
			  "       NVL(C.porcentaje,0)*100 canal_porcentaje\n" + 
			  "  FROM objetos A1\n" + 
			  "  JOIN objetos B1 ON 1=1\n" +
			  "  LEFT JOIN encuesta_producto_subcanal C\n" + 
			  "    ON C.proceso_id=:proceso_id\n" +
			  "   AND C.posicion_codigo=:posicion_codigo\n" +
			  "   AND C.producto_id=A1.id\n" + 
			  "   AND C.subcanal_id=B1.id\n" + 
			  " WHERE A1.objeto_tipo_id=3\n" +  // productos
			  "   AND A1.padre_objeto_id=:linea_id" +
			  "   AND B1.objeto_tipo_id=4\n" +  // subcanales 
			  "   AND B1.padre_objeto_id=:canal_id\n" + 
			  " ORDER BY A1.id,B1.id";
	   	encuesta.setLstItems(plantilla.query(sql, paramMap, new EncuestaLineaCanalesExtractor()));		
		return encuesta;
	}
	
	public void insertLstProductoSubcanales(List<ObjetoObjetos> lstItems, Long procesoId, String posicionCodigo, Long lineaId, Long canalId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("linea_id", lineaId);
		paramMap.put("canal_id", canalId);
		
		String sql = "DELETE FROM encuesta_producto_subcanal\n" + 
					 " WHERE proceso_id=:proceso_id\n" + 
					 "   AND posicion_codigo=:posicion_codigo\n" +
					 "   AND linea_id=:linea_id\n" +
					 "   AND canal_id=:canal_id";
		
		plantilla.update(sql,paramMap);
		
		for (ObjetoObjetos producto: lstItems) {
			for (Objeto subcanal: producto.getLstObjetos()) {
				sql = "INSERT INTO encuesta_producto_subcanal(proceso_id,posicion_codigo,linea_id,producto_id,canal_id,subcanal_id,porcentaje)\n"+
		              "VALUES(:proceso_id,:posicion_codigo,:linea_id,:producto_id,:canal_id,:subcanal_id,:porcentaje)";
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("linea_id", lineaId);
				paramMap.put("producto_id", producto.getObjeto().getId());
				paramMap.put("canal_id", canalId);
				paramMap.put("subcanal_id", subcanal.getId());
				paramMap.put("porcentaje", subcanal.getPorcentaje()/100);
				plantilla.update(sql,paramMap);
			}
		}
	}
	
	public EncuestaObjetoObjetos getEncuestaProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId, Long lineaId) {			
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
	    paramMap.put("linea_id", lineaId);
	    paramMap.put("perfil_id", perfilId);
	    EncuestaObjetoObjetos encuesta = plantilla.queryForObject(sql, paramMap, new EncuestaObjetoObjetosMapper());
		
	    sql = "SELECT B1.id linea_id,\n" + 
	          "       B1.codigo linea_codigo,\n" + 
	          "       B1.nombre linea_nombre,\n" + 
	          "       0 linea_porcentaje,\n" + 
	          "       A2.id canal_id,\n" + 
	          "       A2.codigo canal_codigo,\n" + 
	          "       A2.nombre canal_nombre,\n" + 
	          "       NVL(C1.porcentaje,0)*100 canal_porcentaje\n" + 
	          "  FROM perfil_linea_canal A1\n" + 
	          "  JOIN objetos A2\n" + 
	          "    ON A2.id=A1.canal_id\n" + 
	          "  JOIN objetos B1\n" + 
	          "    ON B1.padre_objeto_id=A1.linea_id\n" + 
	          "  LEFT JOIN encuesta_producto_canal C1\n" + 
	          "    ON C1.proceso_id=:proceso_id\n" + 
	          "   AND C1.posicion_codigo=:posicion_codigo\n" + 
	          "   AND C1.producto_id=B1.id\n" + 
	          "   AND C1.canal_id=A2.id\n" + 
	          " WHERE A1.linea_id=:linea_id\n" + 
	          "   AND A1.perfil_id=:perfil_id\n" + 
	          " ORDER BY B1.nombre,A2.nombre";
	   	encuesta.setLstItems(plantilla.query(sql, paramMap, new EncuestaLineaCanalesExtractor()));		
		return encuesta;
	}
	
	public void insertLstProductoCanales(List<ObjetoObjetos> lstItems, Long procesoId, String posicionCodigo, Long lineaId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		paramMap.put("linea_id", lineaId);
		
		String sql = "DELETE FROM encuesta_producto_canal\n" +
					 " WHERE proceso_id=:proceso_id\n" + 
					 "   AND posicion_codigo=:posicion_codigo\n" +
					 "   AND linea_id=:linea_id";
		plantilla.update(sql,paramMap);
		
		for (ObjetoObjetos producto: lstItems) {
			for (Objeto canal: producto.getLstObjetos()) {
				sql = "INSERT INTO encuesta_producto_canal(proceso_id,posicion_codigo,linea_id,producto_id,canal_id,porcentaje)\n"+
		              "VALUES(:proceso_id,:posicion_codigo,:linea_id,:producto_id,:canal_id,:porcentaje)";
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("linea_id", lineaId);
				paramMap.put("producto_id", producto.getObjeto().getId());
				paramMap.put("canal_id", canal.getId());
				paramMap.put("porcentaje", canal.getPorcentaje()/100);
				plantilla.update(sql,paramMap);
			}
		}
	}
}
