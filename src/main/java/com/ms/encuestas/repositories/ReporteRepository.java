package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;
import com.ms.encuestas.models.Tipo;

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
	
	private String filtroEstados(List<Tipo> estados, String alias) {
		if (estados == null) return "";
		String sql = String.format("\n WHERE %s.etapa_total IN (", alias);
		for (int i = 0; i < estados.size(); ++i)
			if (i != estados.size()-1)
				sql += String.format("%d,", estados.get(i).getId());
			else 
				sql += String.format("%d)", estados.get(i).getId());
		return sql;
	}
	
	private void calcularEstado(Long procesoId, List<Area> areas, List<Centro> centros, List<Tipo> estados) {
		String sql = "DELETE FROM REP_ESTADO_01";
		plantilla.update(sql, (MapSqlParameterSource) null);
		sql = "DELETE FROM REP_ESTADO_02";
		plantilla.update(sql, (MapSqlParameterSource) null);
		sql = "DELETE FROM REP_ESTADO_F";
		plantilla.update(sql, (MapSqlParameterSource) null);		
				
		sql = "INSERT INTO REP_ESTADO_01(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre)\n" +
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
		
		plantilla.update(sql, new MapSqlParameterSource("proceso_id", procesoId));
		sql = "INSERT INTO REP_ESTADO_02(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ult_fecha_actualizacion,tipo_1,tipo_2,tipo_3,tipo_4,tipo_5,tipo_6,tipo_7)\n" +
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
		      "  FROM REP_ESTADO_01 A\n" + 
		      "  LEFT JOIN ENCUESTAS B\n" + 
		      "    ON B.PROCESO_ID=A.PROCESO_ID\n" + 
		      "   AND B.POSICION_CODIGO=A.POSICION_CODIGO\n" + 
		      " GROUP BY A.PROCESO_ID,A.PROCESO_NOMBRE,A.USUARIO_CODIGO,A.USUARIO_NOMBRE_COMPLETO,A.POSICION_CODIGO,A.POSICION_NOMBRE,A.AREA_NOMBRE,A.CENTRO_CODIGO,A.CENTRO_NOMBRE,A.PERFIL_NOMBRE,A.PERFIL_TIPO_ID,A.PERFIL_TIPO_NOMBRE\n" + 
		      " ORDER BY A.PROCESO_ID,A.POSICION_CODIGO";
		plantilla.update(sql, (MapSqlParameterSource) null);
		
		sql = "INSERT INTO REP_ESTADO_F(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ult_fecha_actualizacion,tipo_1,tipo_2,tipo_3,tipo_4,tipo_5,tipo_6,tipo_7,etapa_1,etapa_2,etapa_3,etapa_4,etapa_total)\n" +
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
		      "  FROM REP_ESTADO_02 A";
        plantilla.update(sql, (MapSqlParameterSource) null);
	}
	
	public List<Map<String,Object>> reporteEmpresasVacio() {        
        String sql = "SELECT NULL FECHA_DESCARGA,\n" + 
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
        			 "       NULL ULTIMA_MODIFICACION\n," +        			 
        			 "       NULL EMPRESA,\n" +
        			 "       NULL EMPRESA_PORCENTAJE,\n" +
        			 "       NULL LINEA_EPS,\n" +
        			 "       NULL LINEA_EPS_PORCENTAJE\n" +
        			 "  FROM DUAL";
        return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> reporteControlVacio() {        
        String sql = "SELECT NULL FECHA_DESCARGA,\n" + 
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
        return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> reporteControl(Long procesoId, List<Area> areas, List<Centro> centros, List<Tipo> estados) {
		calcularEstado(procesoId, areas, centros, estados);
		
		String sql = "SELECT SYSDATE FECHA_DESCARGA,\n" + 
					 "       A.PROCESO_NOMBRE PROCESO,\n" + 
					 "       A.USUARIO_CODIGO MATRICULA,\n" + 
					 "       A.USUARIO_NOMBRE_COMPLETO COLABORADOR,\n" + 
					 "       A.POSICION_CODIGO NRO_POSICION,\n" + 
					 "       A.POSICION_NOMBRE POSICION,\n" + 
					 "       A.AREA_NOMBRE AREA,\n" + 
					 "       A.CENTRO_CODIGO CECO_CODIGO,\n" + 
					 "       A.CENTRO_NOMBRE CECO_NOMBRE,\n" + 
					 "       A.PERFIL_NOMBRE PERFIL,\n" + 
					 "       A.PERFIL_TIPO_NOMBRE PERFIL_TIPO,\n" +
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
				 	 "			       END ESTADO_GLOBAL,\n" +
				 	 "       A.ULT_FECHA_ACTUALIZACION ULTIMA_MODIFICACION\n" +
              		 "  FROM REP_ESTADO_F A";
        sql += filtroEstados(estados, "A");
        return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> reporteEmpresas(Long procesoId, List<Area> areas, List<Centro> centros, List<Tipo> estados) {
		calcularEstado(procesoId, areas, centros, estados);
		
		String sql = "SELECT SYSDATE FECHA_DESCARGA,\n" + 
					 "       A.PROCESO_NOMBRE PROCESO,\n" + 
					 "       A.USUARIO_CODIGO MATRICULA,\n" + 
					 "       A.USUARIO_NOMBRE_COMPLETO COLABORADOR,\n" + 
					 "       A.POSICION_CODIGO NRO_POSICION,\n" + 
					 "       A.POSICION_NOMBRE POSICION,\n" + 
					 "       A.AREA_NOMBRE AREA,\n" + 
					 "       A.CENTRO_CODIGO CECO_CODIGO,\n" + 
					 "       A.CENTRO_NOMBRE CECO_NOMBRE,\n" + 
					 "       A.PERFIL_NOMBRE PERFIL,\n" + 
					 "       A.PERFIL_TIPO_NOMBRE PERFIL_TIPO,\n" +
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
					 "       A.ULT_FECHA_ACTUALIZACION ULTIMA_MODIFICACION,\n" +
					 "       B.nombre EMPRESA,\n" + 
					 "       NVL(D.porcentaje,0) EMPRESA_PORCENTAJE,\n" + 
					 "       NVL(C.nombre,'NO APLICA') LINEA_EPS,\n" + 
					 "       NVL(E.porcentaje,0) LINEA_EPS_PORCENTAJE\n" +
					 "  FROM REP_ESTADO_F A\n" +
					 "  LEFT JOIN empresas B\n" + 
					 "    ON 1=1\n" + 
					 "  LEFT JOIN centros C\n" + 
					 "    ON C.empresa_id=2\n" + 
					 "   AND C.empresa_id=B.id\n" + 
					 "  LEFT JOIN encuesta_centro D\n" + 
					 "    ON D.centro_id=C.id\n" + 
					 "   AND D.proceso_id=A.proceso_id\n" + 
					 "   AND D.posicion_codigo=A.posicion_codigo\n" +
					 "  LEFT JOIN encuesta_centro E\n" + 
					 "    ON E.centro_id=C.id\n" + 
					 "   AND E.proceso_id=A.proceso_id\n" + 
					 "   AND E.posicion_codigo=A.posicion_codigo\n" +
              		 " WHERE A.perfil_tipo_id=1";
        sql += filtroEstados(estados, "A");
        return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> reporteConsolidado(Long procesoId, List<Area> areas, List<Centro> centros, List<Tipo> estados) {
		calcularEstado(procesoId, areas, centros, estados);
		
		String sql = "DELETE FROM REP_CONS_01";
		plantilla.update(sql, (MapSqlParameterSource) null);
		
		sql = "INSERT INTO REP_CONS_01(proceso_id,posicion_codigo,dimension1_codigo,dimension1,dimension2_codigo,dimension2,porcentaje)\n" +
			  "SELECT A.PROCESO_ID,\n" + 
			  "       A.POSICION_CODIGO,\n" +
			  "       C.codigo DIMENSION1_CODIGO,\n" +
			  "       C.nombre DIMENSION1,\n" +
			  "       'N/A' DIMENSION2_CODIGO,\n" +
			  "       'N/A' DIMENSION2,\n" + 
			  "       NVL(B.porcentaje,0) PORCENTAJE\n" + 
			  "  FROM REP_ESTADO_F A\n" + 
			  "  LEFT JOIN encuesta_centro B\n" + 
			  "    ON B.proceso_id=A.proceso_id\n" + 
			  "   AND B.posicion_codigo=A.posicion_codigo\n" + 
			  "  LEFT JOIN centros C\n" + 
			  "    ON C.id=B.centro_id\n" +
			  "   AND C.empresa_id=1\n" + 
			  " WHERE A.perfil_tipo_id=1";
		plantilla.update(sql, (MapSqlParameterSource) null);
		
		sql = "INSERT INTO REP_CONS_01(proceso_id,posicion_codigo,dimension1_codigo,dimension1,dimension2_codigo,dimension2,porcentaje)\n" +
			  "SELECT A.PROCESO_ID,\n" + 
			  "       A.POSICION_CODIGO,\n" +
			  "       NVL(P.codigo,'N/A') DIMENSION1_CODIGO,\n" + 
			  "       NVL(P.nombre,'N/A') DIMENSION1,\n" + 
			  "       NVL(C.codigo,'N/A') DIMENSION2_CODIGO,\n" + 
			  "       NVL(C.nombre,'N/A') DIMENSION2,\n" + 
			  "       NVL(E1.porcentaje,0)*NVL(E2.porcentaje,0) PORCENTAJE\n" + 
			  "  FROM REP_estado_F A\n" + 
			  "  LEFT JOIN ENCUESTA_LINEA E1\n" + 
			  "    ON E1.proceso_id=A.proceso_id\n" + 
			  "   AND E1.posicion_codigo=A.posicion_codigo\n" +
			  "  LEFT JOIN ENCUESTA_PRODUCTO_CANAL E2\n" + 
			  "    ON E2.proceso_id=A.proceso_id\n" + 
			  "   AND E2.posicion_codigo=A.posicion_codigo\n" + 
			  "  JOIN objetos P\n" + 
			  "    ON P.id=E2.producto_id\n" + 
			  "   AND P.objeto_tipo_id=3 -- PRODUCTO\n" + 
			  "   AND P.padre_objeto_id=E1.linea_id -- PRODUCTO DE ESA LINEA\n" + 
			  "  LEFT JOIN objetos C\n" + 
			  "    ON C.id=E2.canal_id\n" + 
			  "   AND C.objeto_tipo_id=2 -- CANAL\n" + 
			  " WHERE A.perfil_tipo_id=2";
		plantilla.update(sql, (MapSqlParameterSource) null);
			
		sql = "INSERT INTO REP_CONS_01(proceso_id,posicion_codigo,dimension1_codigo,dimension1,dimension2_codigo,dimension2,porcentaje)\n" +
				  "SELECT A.PROCESO_ID,\n" + 
				  "       A.POSICION_CODIGO,\n" +
				  "       NVL(P.codigo,'N/A') DIMENSION1_CODIGO,\n" + 
				  "       NVL(P.nombre,'N/A') DIMENSION1,\n" + 
				  "       NVL(S.codigo,'N/A') DIMENSION2_CODIGO,\n" + 
				  "       NVL(S.nombre,'N/A') DIMENSION2,\n" + 
				  "       NVL(E1.linea_porcentaje,0)*NVL(E1.canal_porcentaje,0)*NVL(E2.porcentaje,0) PORCENTAJE\n" + 
				  "  FROM REP_estado_F A\n" + 
				  "  LEFT JOIN ENCUESTA_LINEA_CANAL E1\n" + 
				  "    ON E1.proceso_id=A.proceso_id\n" + 
				  "   AND E1.posicion_codigo=A.posicion_codigo\n" +
				  "  LEFT JOIN ENCUESTA_PRODUCTO_SUBCANAL E2\n" + 
				  "    ON E2.proceso_id=A.proceso_id\n" + 
				  "   AND E2.posicion_codigo=A.posicion_codigo\n" + 
				  "  JOIN objetos P\n" + 
				  "    ON P.id=E2.producto_id\n" + 
				  "   AND P.objeto_tipo_id=3 -- PRODUCTO\n" + 
				  "   AND P.padre_objeto_id=E1.linea_id -- PRODUCTO DE ESA LINEA\n" + 
				  "  LEFT JOIN objetos S\n" + 
				  "    ON S.id=E2.subcanal_id\n" + 
				  "   AND S.objeto_tipo_id=4 -- SUBCANAL\n" + 
				  " WHERE A.perfil_tipo_id IN (3,4)";
		plantilla.update(sql, (MapSqlParameterSource) null);
		
		sql = "SELECT SYSDATE FECHA_DESCARGA,\n" + 
			  "       A.PROCESO_NOMBRE PROCESO,\n" + 
			  "       A.USUARIO_CODIGO MATRICULA,\n" + 
			  "       A.USUARIO_NOMBRE_COMPLETO COLABORADOR,\n" + 
			  "       A.POSICION_CODIGO NRO_POSICION,\n" + 
			  "       A.POSICION_NOMBRE POSICION,\n" + 
			  "       A.AREA_NOMBRE AREA,\n" + 
			  "       A.CENTRO_CODIGO CECO_CODIGO,\n" + 
			  "       A.CENTRO_NOMBRE CECO_NOMBRE,\n" + 
			  "       A.PERFIL_NOMBRE PERFIL,\n" + 
			  "       A.PERFIL_TIPO_NOMBRE PERFIL_TIPO,\n" +
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
			  "       A.ULT_FECHA_ACTUALIZACION ULTIMA_MODIFICACION,\n" + 
			  "       NVL(B.DIMENSION1_CODIGO,'N/A') DIMENSION1_CODIGO,\n" + 
			  "       NVL(B.DIMENSION1,'N/A') DIMENSION1,\n" +
			  "       NVL(B.DIMENSION2_CODIGO,'N/A') DIMENSION2_CODIGO,\n" +
			  "       NVL(B.DIMENSION2,'N/A') DIMENSION2,\n" +
			  "       NVL(B.PORCENTAJE,0) PORCENTAJE\n" +
			  "  FROM REP_ESTADO_F A\n" +
			  "  LEFT JOIN REP_CONS_01 B\n" + 
			  "    ON B.proceso_id=A.proceso_id\n" +
			  "   AND B.posicion_codigo=A.posicion_codigo";
        sql += filtroEstados(estados, "A");
        return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
}
