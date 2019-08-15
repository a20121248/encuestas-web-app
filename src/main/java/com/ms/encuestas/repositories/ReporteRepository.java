package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Linea;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;
import com.ms.encuestas.models.ProductoCanal;
import com.ms.encuestas.models.ProductoSubcanal;

@CrossOrigin(origins={})
@Repository
public class ReporteRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	private String filtroAreas(List<Area> areas, String alias) {
		if (areas == null) return "";
		String sql = String.format("\n   AND %s.area_id IN (", alias);
		for (int i = 0; i < areas.size(); ++i) {
			if (i != areas.size()-1)
				sql += String.format("%d,", areas.get(i).getId());
			else 
				sql += String.format("%d)\n", areas.get(i).getId());
		}
		return sql;
	}
	
	private String filtroCentros(List<Centro> centros, String alias) {
		if (centros == null) return "";
		String sql = String.format("\n   AND %s.centro_id IN (", alias);
		for (int i = 0; i < centros.size(); ++i)
			if (i != centros.size()-1)
				sql += String.format("%d,", centros.get(i).getId());
			else 
				sql += String.format("%d)", centros.get(i).getId());
		return sql;
	}
	
	public boolean hasData(Long procesoId, List<Area> areas, List<Centro> centros, List<Integer> estadoIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		
		String sql = "SELECT COUNT(1)\n" +
		             "  FROM posicion_datos A\n" +
				     " WHERE A.proceso_id=:proceso_id";
		sql += filtroAreas(areas, "A");
		sql += filtroCentros(centros, "A");		
		return plantilla.queryForObject(sql, paramMap, Long.class) != 0;
	}
	
	public List<Map<String,Object>> reporteControl(Long procesoId, List<Area> areas, List<Centro> centros, List<Integer> estadoIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		
		String sql;
		if (!hasData(procesoId, areas, centros, estadoIds)) {
	        sql = "SELECT NULL FECHA_DESCARGA,\n" + 
	              "       NULL PROCESO,\n" + 
	              "       NULL MATRICULA,\n" + 
	              "       NULL COLABORADOR,\n" + 
	              "       NULL NRO_POSICION,\n" + 
	              "       NULL POSICION,\n" + 
	              "       NULL AREA,\n" + 
	              "       NULL CECO_CODIGO,\n" + 
	              "       NULL CECO_NOMBRE,\n" + 
	              "       NULL PERFIL,\n" + 
	              "       NULL PERFIL_TIPO,\n" +
	              "       NULL ETAPA_1,\n" + 
	              "       NULL ETAPA_2,\n" + 
	              "       NULL ETAPA_3,\n" + 
	              "       NULL ETAPA_4,\n" + 
	              "       NULL ESTADO_GLOBAL,\n" +
	              "       NULL ULTIMA_MODIFICACION\n" +
	              "  FROM DUAL";
	  		return plantilla.queryForList(sql, paramMap);
		}
		
		sql = "DELETE FROM REP_CONTROL_01";
		plantilla.update(sql,paramMap);
		sql = "DELETE FROM REP_CONTROL_02";
		plantilla.update(sql,paramMap);
		sql = "DELETE FROM REP_CONTROL_F";
		plantilla.update(sql,paramMap);		
				
		sql = "INSERT INTO REP_CONTROL_01(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre)\n" +
              "SELECT C.ID PROCESO_ID,\n" + 
			  "       C.nombre PROCESO_NOMBRE,\n" + 
			  "       NVL(B.codigo,'VACANTE') USUARIO_CODIGO,\n" + 
	  		  "       NVL(B.nombre_completo,'VACANTE') USUARIO_NOMBRE_COMPLETO,\n" + 
		      "       D.codigo POSICION_CODIGO,\n" + 
			  "       D.nombre POSICION_NOMBRE,\n" + 
			  "       E.nombre AREA_NOMBRE,\n" + 
			  "       F.codigo CENTRO_CODIGO,\n" + 
			  "       F.nombre CENTRO_NOMBRE,\n" + 
			  "       G.nombre PERFIL_NOMBRE,\n" + 
			  "       G.perfil_tipo_id,\n" +
			  "       H.nombre perfil_tipo_nombre\n" + 
			  "  FROM posicion_datos A\n" + 
			  "  LEFT JOIN usuarios B ON B.codigo=A.usuario_codigo\n" + 
			  "  JOIN procesos C ON C.id=A.proceso_id\n" + 
			  "  JOIN posiciones D ON D.codigo=A.posicion_codigo\n" + 
			  "  JOIN areas E ON E.id=A.area_id\n" + 
			  "  JOIN centros F ON F.id=A.centro_id\n" + 
			  "  JOIN perfiles G ON G.id=A.perfil_id\n" +
			  "  JOIN perfil_tipos H ON H.id=G.perfil_tipo_id\n" +
			  " WHERE A.proceso_id=:proceso_id";
		
		sql += filtroAreas(areas, "A");
		sql += filtroCentros(centros, "A");
		System.out.println(sql);
		plantilla.update(sql,paramMap);
		
		sql = "INSERT INTO REP_CONTROL_02(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ult_fecha_actualizacion,tipo_1,tipo_2,tipo_3,tipo_4,tipo_5,tipo_6,tipo_7)\n" +
		      "SELECT A.*,\n" + 
		      "       MAX(B.FECHA_ACTUALIZACION) ULT_FECHA_ACTUALIZACION,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=1 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=1 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_1,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=2 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=2 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_2,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=3 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=3 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_3,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=4 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=4 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_4,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=5 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=5 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_5,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=6 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=6 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_6,\n" + 
		      "       MAX(CASE WHEN B.ENCUESTA_TIPO_ID=7 AND B.ESTADO=1 THEN 2\n" + 
		      "                WHEN B.ENCUESTA_TIPO_ID=7 AND B.ESTADO=0 THEN 1\n" + 
		      "                ELSE 0\n" + 
		      "       END) TIPO_7\n" + 
		      "  FROM REP_CONTROL_01 A\n" + 
		      "  LEFT JOIN ENCUESTAS B\n" + 
		      "    ON B.PROCESO_ID=A.PROCESO_ID\n" + 
		      "   AND B.POSICION_CODIGO=A.POSICION_CODIGO\n" + 
		      " GROUP BY A.PROCESO_ID,A.PROCESO_NOMBRE,A.USUARIO_CODIGO,A.USUARIO_NOMBRE_COMPLETO,A.POSICION_CODIGO,A.POSICION_NOMBRE,A.AREA_NOMBRE,A.CENTRO_CODIGO,A.CENTRO_NOMBRE,A.PERFIL_NOMBRE,A.PERFIL_TIPO_ID,A.PERFIL_TIPO_NOMBRE\n" + 
		      " ORDER BY A.PROCESO_ID,A.POSICION_CODIGO";
		plantilla.update(sql,paramMap);
			
		sql = "INSERT INTO REP_CONTROL_F(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ult_fecha_actualizacion,tipo_1,tipo_2,tipo_3,tipo_4,tipo_5,tipo_6,tipo_7,etapa_1,etapa_2,etapa_3,etapa_4,etapa_total)\n" +
		      "SELECT A.*,\n" + 
		      "       TIPO_1 ETAPA_1,--EMPRESA\n" + 
		      "       TIPO_2 ETAPA_2,--EPS\n" + 
		      "       CASE WHEN TIPO_3=2 OR TIPO_4=2 OR TIPO_7=2 THEN 2\n" + 
		      "            WHEN TIPO_3=1 OR TIPO_4=1 OR TIPO_7=1 THEN 1\n" + 
		      "            ELSE 0\n" + 
		      "       END ETAPA_3,--CECO, LINEA-CANAL, LINEA\n" + 
		      "       CASE WHEN TIPO_5=2 OR TIPO_6=2 THEN 2\n" + 
		      "            WHEN TIPO_5=1 OR TIPO_6=1 THEN 1\n" + 
		      "            ELSE 0\n" + 
		      "       END ETAPA_4,--PRODUCTO-SUBCANAL, PRODUCTO-CANAL\n" + 
		      "       CASE WHEN PERFIL_TIPO_ID=1 AND (TIPO_1=2 AND TIPO_2=2 AND TIPO_3=2) THEN 2\n" + 
		      "            WHEN PERFIL_TIPO_ID=1 AND (TIPO_1=1 OR  TIPO_2=1 OR  TIPO_3=1) THEN 1\n" + 
		      "            WHEN PERFIL_TIPO_ID=2 AND (TIPO_1=2 AND TIPO_2=2 AND TIPO_7=2 AND TIPO_6=2) THEN 2\n" + 
		      "            WHEN PERFIL_TIPO_ID=2 AND (TIPO_1=1 OR  TIPO_2=1 OR  TIPO_7=1 OR  TIPO_6=1) THEN 1\n" + 
		      "            WHEN PERFIL_TIPO_ID=3 AND (TIPO_1=2 AND TIPO_2=2 AND TIPO_4=2 AND TIPO_5=2) THEN 2\n" + 
		      "            WHEN PERFIL_TIPO_ID=3 AND (TIPO_1=1 OR  TIPO_2=1 OR  TIPO_4=1 OR  TIPO_5=1) THEN 1\n" + 
		      "            WHEN PERFIL_TIPO_ID=4 AND (TIPO_1=2 AND TIPO_2=2 AND TIPO_4=2 AND TIPO_5=2) THEN 2\n" + 
		      "            WHEN PERFIL_TIPO_ID=4 AND (TIPO_1=1 OR  TIPO_2=1 OR  TIPO_4=1 OR  TIPO_5=1) THEN 1\n" + 
		      "            ELSE 0\n" + 
		      "       END ETAPA_TOTAL\n" + 
		      "  FROM REP_CONTROL_02 A";
        plantilla.update(sql,paramMap);
        
        sql = "SELECT SYSDATE FECHA_DESCARGA,\n" + 
              "       PROCESO_NOMBRE PROCESO,\n" + 
              "       USUARIO_CODIGO MATRICULA,\n" + 
              "       USUARIO_NOMBRE_COMPLETO COLABORADOR,\n" + 
              "       POSICION_CODIGO NRO_POSICION,\n" + 
              "       POSICION_NOMBRE POSICION,\n" + 
              "       AREA_NOMBRE AREA,\n" + 
              "       CENTRO_CODIGO CECO_CODIGO,\n" + 
              "       CENTRO_NOMBRE CECO_NOMBRE,\n" + 
              "       PERFIL_NOMBRE PERFIL,\n" + 
              "       PERFIL_TIPO_NOMBRE PERFIL_TIPO,\n" +
              "       CASE WHEN ETAPA_1=0 THEN 'NO INICIADA'\n" + 
              "            WHEN ETAPA_1=1 THEN 'INICIADA'\n" + 
              "            WHEN ETAPA_1=2 THEN 'COMPLETADA'\n" + 
              "       END ETAPA_1,\n" + 
              "       CASE WHEN ETAPA_2=0 THEN 'NO INICIADA'\n" + 
              "            WHEN ETAPA_2=1 THEN 'INICIADA'\n" + 
              "            WHEN ETAPA_2=2 THEN 'COMPLETADA'\n" + 
              "       END ETAPA_2,\n" + 
              "       CASE WHEN ETAPA_3=0 THEN 'NO INICIADA'\n" + 
              "            WHEN ETAPA_3=1 THEN 'INICIADA'\n" + 
              "            WHEN ETAPA_3=2 THEN 'COMPLETADA'\n" + 
              "       END ETAPA_3,\n" + 
              "       CASE WHEN ETAPA_4=0 THEN 'NO INICIADA'\n" + 
              "            WHEN ETAPA_4=1 THEN 'INICIADA'\n" + 
              "            WHEN ETAPA_4=2 THEN 'COMPLETADA'\n" + 
              "       END ETAPA_4,\n" + 
              "       CASE WHEN ETAPA_TOTAL=0 THEN 'NO INICIADA'\n" + 
              "            WHEN ETAPA_TOTAL=1 THEN 'INICIADA'\n" + 
              "            WHEN ETAPA_TOTAL=2 THEN 'COMPLETADA'\n" + 
              "       END ESTADO_GLOBAL,\n" +
              "       ULT_FECHA_ACTUALIZACION ULTIMA_MODIFICACION\n" +
              "  FROM REP_CONTROL_F";
		return plantilla.queryForList(sql, paramMap);
	}
	
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
		plantilla.update(sql,paramMap);
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
		
		sql = "SELECT A.id,\n" +
			  "       A.codigo,\n" +
			  "       A.nombre,\n" +
			  "       A.nivel,\n" +
			  "       A.grupo,\n" +
			  "       A.fecha_creacion,\n" +
			  "       A.fecha_actualizacion,\n" +
			  "       NVL(B.porcentaje,0)*100 porcentaje\n" + 
			  "  FROM centros A\n" + 
			  "  LEFT JOIN encuesta_centro B\n" +
			  "    ON A.id=B.centro_id\n" +
			  "   AND B.proceso_id=:proceso_id\n" +
			  "   AND B.posicion_codigo=:posicion_codigo\n" +
			  "  JOIN perfil_centro C\r\n" + 
			  "    ON C.perfil_id=:perfil_id\r\n" + 
			  "   AND A.id=C.centro_id" +
			  " WHERE A.empresa_id=:empresa_id\n" +
			  "   AND A.nivel>:nivel\n" +
			  "   AND A.fecha_eliminacion IS NULL\n" +
			  " ORDER BY A.nivel,A.id";
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
		
		sql = "SELECT DISTINCT B.id id,\n" + 
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
			  "    ON C.proceso_id=A.proceso_id\n" +
			  "   AND C.posicion_codigo=:posicion_codigo\n" +
			  "   AND C.linea_id=A.linea_id\n" + 
			  " WHERE A.proceso_id=:proceso_id\n" +
			  "   AND A.perfil_id=:perfil_id\n" +
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
			  "    ON D.proceso_id=A.proceso_id\n" +
			  "   AND D.posicion_codigo=:posicion_codigo\n" +
			  "   AND D.linea_id=A.linea_id\n" + 
			  "   AND D.canal_id=A.canal_id\n" + 
			  " WHERE A.proceso_id=:proceso_id\n" +
			  "   AND A.perfil_id=:perfil_id\n" + 
			  " ORDER BY A.linea_id,A.canal_id";
		
		//System.out.println("proceso_id:" + procesoId);
		//System.out.println("posicion_codigo:"+ posicionCodigo);
		//System.out.println("encuesta_tipo_id:"+ encuestaTipoId);
		//System.out.println("perfil_id:"+ perfilId);
		//System.out.println(sql);
		
		paramMap.put("proceso_id", procesoId);
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
	
	public void insertLstProductoSubcanales(List<ObjetoObjetos> lstItems, Long procesoId, String posicionCodigo) {
		System.out.println("AQUI en el REPOSITORY");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		String sql = "DELETE FROM encuesta_producto_subcanal\n" + 
					 " WHERE proceso_id=:proceso_id\n" + 
					 "   AND posicion_codigo=:posicion_codigo";
		
		plantilla.update(sql,paramMap);
		
		for (ObjetoObjetos producto: lstItems) {
			for (Objeto subcanal: producto.getLstObjetos()) {
				sql = "INSERT INTO encuesta_producto_subcanal(proceso_id,posicion_codigo,producto_id,subcanal_id,porcentaje)\n"+
		              "VALUES(:proceso_id,:posicion_codigo,:producto_id,:subcanal_id,:porcentaje)";
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("producto_id", producto.getObjeto().getId());
				paramMap.put("subcanal_id", subcanal.getId());
				paramMap.put("porcentaje", subcanal.getPorcentaje()/100);
				plantilla.update(sql,paramMap);
			}
		}
	}
	
	public EncuestaObjetoObjetos getEncuestaProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId) {			
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
			  "  LEFT JOIN encuesta_producto_canal C\n" + 
			  "    ON C.proceso_id=:proceso_id\n" +
			  "   AND C.posicion_codigo=:posicion_codigo\n" +
			  "   AND C.producto_id=A1.id\n" + 
			  "   AND C.canal_id=B1.id\n" +
			  " WHERE A1.objeto_tipo_id=3\n" + // productos
			  "   AND A1.padre_objeto_id=:linea_id\n" +
			  "   AND B1.objeto_tipo_id=2\n" + // canales 
			  " ORDER BY A1.id,B1.id";
	   	encuesta.setLstItems(plantilla.query(sql, paramMap, new EncuestaLineaCanalesExtractor()));		
		return encuesta;
	}
	
	public void insertLstProductoCanales(List<ObjetoObjetos> lstItems, Long procesoId, String posicionCodigo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		String sql = "DELETE FROM encuesta_producto_canal\n" + 
					 " WHERE proceso_id=:proceso_id\n" + 
					 "   AND posicion_codigo=:posicion_codigo";
		plantilla.update(sql,paramMap);
		
		for (ObjetoObjetos producto: lstItems) {
			for (Objeto canal: producto.getLstObjetos()) {
				sql = "INSERT INTO encuesta_producto_canal(proceso_id,posicion_codigo,producto_id,canal_id,porcentaje)\n"+
		              "VALUES(:proceso_id,:posicion_codigo,:producto_id,:canal_id,:porcentaje)";
				paramMap = new HashMap<String, Object>();
				paramMap.put("proceso_id", procesoId);
				paramMap.put("posicion_codigo", posicionCodigo);
				paramMap.put("producto_id", producto.getObjeto().getId());
				paramMap.put("canal_id", canal.getId());
				paramMap.put("porcentaje", canal.getPorcentaje()/100);
				plantilla.update(sql,paramMap);
			}
		}
	}
}
