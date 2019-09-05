package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(ReporteRepository.class);
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
		String sql = "DELETE FROM REP_ESTADO_F";
		plantilla.update(sql, (MapSqlParameterSource) null);		
		
		sql = "INSERT INTO REP_ESTADO_F(proceso_id,proceso_nombre,usuario_codigo,usuario_nombre_completo,posicion_codigo,posicion_nombre,area_nombre,centro_codigo,centro_nombre,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,responsable_codigo,responsable_nombre_completo,ult_fecha_actualizacion,etapa_1,etapa_2,etapa_3,etapa_4,etapa_total)\r\n" + 
				"SELECT  USR.PROCESO_ID PROCESO_ID,\r\n" + 
				"        USR.PROCESO_NOMBRE PROCESO_NOMBRE,\r\n" + 
				"        USR.usuario_codigo USUARIO_CODIGO,\r\n" + 
				"        USR.usuario_nombre_completo USUARIO_NOMBRE_COMPLETO,\r\n" + 
				"        USR.POSICION_CODIGO POSICION_CODIGO,\r\n" + 
				"        USR.POSICION_NOMBRE POSICION_NOMBRE,\r\n" + 
				"        USR.AREA_NOMBRE AREA_NOMBRE,\r\n" + 
				"        USR.CENTRO_CODIGO CENTRO_CODIGO,\r\n" + 
				"        USR.CENTRO_NOMBRE CENTRO_NOMBRE,\r\n" + 
				"        USR.PERFIL_NOMBRE PERFIL_NOMBRE,\r\n" + 
				"        USR.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"        USR.perfil_tipo_nombre perfil_tipo_nombre,\r\n" + 
				"        USR.RESPONSABLE_CODIGO RESPONSABLE_CODIGO,\r\n" + 
				"        USR.RESPONSABLE_NOMBRE_COMPLETO RESPONSABLE_NOMBRE_COMPLETO,\r\n" + 
				"        USR.FECHA_ACTUALIZACION ult_fecha_actualizacion,\r\n" + 
				"        EG.ETAPA_1 ETAPA_1,\r\n" + 
				"        EG.ETAPA_2 ETAPA_2,\r\n" + 
				"        EG.ETAPA_3 ETAPA_3,\r\n" + 
				"        EG.ETAPA_4 ETAPA_4,\r\n" + 
				"        (CASE \r\n" + 
				"          WHEN EG.ESTADO_GLOBAL = 2 THEN 0\r\n" + 
				"          WHEN EG.ESTADO_GLOBAL = 1 THEN 1\r\n" + 
				"          WHEN EG.ESTADO_GLOBAL = 0 THEN 2\r\n" + 
				"        END)ETAPA_GLOBAL" + 
				"  FROM (SELECT usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,\r\n" + 
				"      MAX(ETAPA_1) ETAPA_1,\r\n" + 
				"      MAX(ETAPA_2) ETAPA_2,\r\n" + 
				"      MAX(ETAPA_3) ETAPA_3,\r\n" + 
				"      MAX(ETAPA_4) ETAPA_4,\r\n" + 
				"      MAX(ESTADO_GLOBAL) ESTADO_GLOBAL\r\n" + 
				"FROM (SELECT usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,          \r\n" + 
				"              MAX(CASE \r\n" + 
				"                WHEN enc.empresa_id is null  then 0\r\n" + 
				"                ELSE 1\r\n" + 
				"              END) ETAPA_1,\r\n" + 
				"              MAX(CASE \r\n" + 
				"                WHEN (enc.empresa_id is null OR (enc.empresa_id != 2)) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='NO') THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"              END) ETAPA_2,\r\n" + 
				"              MAX(CASE \r\n" + 
				"                WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='NO') THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"              END) ETAPA_3,\r\n" + 
				"              0 ETAPA_4,\r\n" + 
				"               MAX(CASE WHEN (encuestas_hijos = '-') AND SUMA_FINAL = 0 THEN 2\r\n" + 
				"                        WHEN (encuestas_hijos = 'REVISAR') AND SUMA_FINAL = 0 THEN 1\r\n" + 
				"                        ELSE 0\r\n" + 
				"               END) ESTADO_GLOBAL\r\n" + 
				"          FROM (SELECT z.usuario_codigo,\r\n" + 
				"                       z.posicion_codigo,\r\n" + 
				"                       z.perfil_id,\r\n" + 
				"                       z.perfil_nombre,\r\n" + 
				"                       z.perfil_tipo_id,\r\n" + 
				"                       z.perfil_tipo_nombre,\r\n" + 
				"                       z.empresa_id,\r\n" + 
				"                       z.nombre,\r\n" + 
				"                       z.encuestas_hijos,\r\n" + 
				"                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL \r\n" + 
				"                  FROM (select B.usuario_codigo usuario_codigo,\r\n" + 
				"                               B.posicion_codigo posicion_codigo,\r\n" + 
				"                               b.perfil_id perfil_id,\r\n" + 
				"                               c.nombre perfil_nombre,\r\n" + 
				"                               C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"                               d.nombre perfil_tipo_nombre,\r\n" + 
				"                               E.empresa_id empresa_id,\r\n" + 
				"                               F.nombre,\r\n" + 
				"                               (case \r\n" + 
				"                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                                     when (E.empresa_id is null) THEN '-'\r\n" + 
				"                                     when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"                               end) encuestas_hijos,\r\n" + 
				"                               (case\r\n" + 
				"                                     when (I.porcentaje is not null) then I.porcentaje\r\n" + 
				"                                     when (K.porcentaje is not null) then K.porcentaje\r\n" + 
				"                               end) porcentaje_final\r\n" + 
				"                          from usuarios A \r\n" + 
				"                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:proceso_id\r\n" + 
				"                          join perfiles C on C.id = B.perfil_id\r\n" + 
				"                          join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"                          left join empresas F on F.id = E.empresa_id\r\n" + 
				"                          left join perfil_centro G on G.perfil_id = B.perfil_id and F.id=1 \r\n" + 
				"                          left join centros H on H.id = G.centro_id  and H.empresa_id = F.id\r\n" + 
				"                          left join encuesta_centro I on I.proceso_id = B.proceso_id and I.posicion_codigo = B.posicion_codigo and I.centro_id = H.id\r\n" + 
				"                          left join centros J on F.id = 2 and J.empresa_id = F.id\r\n" + 
				"                          left join encuesta_centro k on K.proceso_id = B.proceso_id and K.posicion_codigo = B.posicion_codigo and K.centro_id = J.id\r\n" + 
				"                         where D.id = 1)z\r\n" + 
				"                 group by z.usuario_codigo, z.posicion_codigo,z.perfil_id,z.perfil_nombre,z.perfil_tipo_id,z.perfil_tipo_nombre,z.empresa_id,z.nombre,z.encuestas_hijos) enc\r\n" + 
				"         GROUP BY usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,\r\n" + 
				"              (CASE \r\n" + 
				"                WHEN enc.empresa_id is null  then 1\r\n" + 
				"                ELSE 0\r\n" + 
				"              END) ,\r\n" + 
				"              (CASE \r\n" + 
				"                WHEN (enc.empresa_id is null OR (enc.empresa_id != 2)) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='NO') THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 2 and encuestas_hijos='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"              END) ,\r\n" + 
				"              (CASE \r\n" + 
				"                WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='NO') THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"                WHEN (enc.empresa_id = 1 and encuestas_hijos='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"              END))\r\n" + 
				"          GROUP BY usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\r\n" + 
				"          \r\n" + 
				"          UNION\r\n" + 
				"          \r\n" + 
				"          SELECT usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ETAPA_1,ETAPA_2,ETAPA_3,\r\n" + 
				"           (CASE\r\n" + 
				"              WHEN ESTADO_GLOBAL = 2 THEN 0\r\n" + 
				"              WHEN ETAPA_4 = 2 AND ESTADO_GLOBAL= 0   THEN 2\r\n" + 
				"              WHEN ETAPA_4 = 2 AND ESTADO_GLOBAL= 1   THEN 1\r\n" + 
				"              WHEN ETAPA_4 = 1 AND ESTADO_GLOBAL= 1   THEN 0\r\n" + 
				"            END)ETAPA_4,\r\n" + 
				"          ESTADO_GLOBAL\r\n" + 
				"FROM (select  usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,\r\n" + 
				"        MAX(CASE \r\n" + 
				"          WHEN enc.empresa_id is null THEN 0\r\n" + 
				"          else 1\r\n" + 
				"        END) ETAPA_1,\r\n" + 
				"        MAX(CASE \r\n" + 
				"          WHEN (enc.empresa_id is null OR (enc.empresa_id != 2)) THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"        END) ETAPA_2,\r\n" + 
				"        MAX(CASE \r\n" + 
				"          WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'NO') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz != '-') THEN 1\r\n" + 
				"        END) ETAPA_3,\r\n" + 
				"        MAX(CASE \r\n" + 
				"          WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = '-') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'NO') THEN 0\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'REVISAR' AND suma_final!=1) THEN 1\r\n" + 
				"          WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'REVISAR' AND suma_final =1) THEN 2\r\n" + 
				"        END) ETAPA_4,\r\n" + 
				"        MAX(CASE  \r\n" + 
				"                  WHEN (encuestas_hijos_empresa = '-') AND suma_final = 0 THEN 2\r\n" + 
				"                  WHEN (encuestas_hijos_empresa = 'REVISAR') AND (encuesta_hijos_matriz = '-' OR encuesta_hijos_matriz = 'REVISAR') AND suma_final = 0 THEN 1\r\n" + 
				"                  ELSE 0\r\n" + 
				"          END) ESTADO_GLOBAL\r\n" + 
				"          from (select z.usuario_codigo,\r\n" + 
				"                       z.posicion_codigo,\r\n" + 
				"                       z.perfil_id,\r\n" + 
				"                       z.perfil_nombre,\r\n" + 
				"                       z.perfil_tipo_id,\r\n" + 
				"                       z.perfil_tipo_nombre,\r\n" + 
				"                       z.empresa_id,\r\n" + 
				"                       z.nombre,\r\n" + 
				"                       z.encuestas_hijos_empresa,\r\n" + 
				"                       z.encuesta_hijos_matriz,\r\n" + 
				"                       z.linea_id,\r\n" + 
				"                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\r\n" + 
				"                  from (select B.usuario_codigo usuario_codigo,\r\n" + 
				"                               B.posicion_codigo posicion_codigo,\r\n" + 
				"                               b.perfil_id perfil_id,\r\n" + 
				"                               c.nombre perfil_nombre,\r\n" + 
				"                               C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"                               d.nombre perfil_tipo_nombre,\r\n" + 
				"                               e.empresa_id,\r\n" + 
				"                               f.nombre,\r\n" + 
				"                               (case when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                                     when (E.empresa_id is null) THEN '-'\r\n" + 
				"                                     when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"                               end) encuestas_hijos_empresa,\r\n" + 
				"                               (case when  I.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                                     when  I.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                                     when  I.porcentaje is null then '-'\r\n" + 
				"                               end) encuesta_hijos_matriz,\r\n" + 
				"                               I.linea_id,\r\n" + 
				"                               (case when (H.porcentaje is not null and I.porcentaje is null) then h.porcentaje\r\n" + 
				"                                     when (L.porcentaje is not null) then L.porcentaje\r\n" + 
				"                               end) porcentaje_final\r\n" + 
				"                          from usuarios A \r\n" + 
				"                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:proceso_id\r\n" + 
				"                          join perfiles C on C.id = B.perfil_id\r\n" + 
				"                          join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"                          left join empresas F on F.id = E.empresa_id\r\n" + 
				"                          left join centros G on G.empresa_id = 2 and F.id = 2\r\n" + 
				"                          left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\r\n" + 
				"                          left join encuesta_linea I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1\r\n" + 
				"                          left join perfil_linea_canal J on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id\r\n" + 
				"                          left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\r\n" + 
				"                          left join encuesta_Producto_canal L on L.proceso_id = B.proceso_id and L.posicion_codigo = B.posicion_codigo and J.canal_id = L.canal_id and L.producto_id = K.id\r\n" + 
				"                         where D.id = 2) z \r\n" + 
				"                 group by z.usuario_codigo,z.posicion_codigo,z.perfil_id,z.perfil_nombre,z.perfil_tipo_id,z.perfil_tipo_nombre,z.empresa_id,z.nombre,z.encuestas_hijos_empresa,z.encuesta_hijos_matriz,z.linea_id\r\n" + 
				"                ) enc\r\n" + 
				"         group by usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre) enc_estado\r\n" + 
				"         \r\n" + 
				"         UNION\r\n" + 
				"         \r\n" + 
				"         SELECT usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,ETAPA_1,ETAPA_2,ETAPA_3,\r\n" + 
				"           (CASE\r\n" + 
				"              WHEN ESTADO_GLOBAL = 2 THEN 0\r\n" + 
				"              WHEN ETAPA_4 = 2 AND ESTADO_GLOBAL= 0 THEN 2\r\n" + 
				"              WHEN ETAPA_4 = 2 AND ESTADO_GLOBAL= 1 THEN 1\r\n" + 
				"              WHEN ETAPA_4 = 1 AND ESTADO_GLOBAL= 1 THEN 0\r\n" + 
				"            END)ETAPA_4,\r\n" + 
				"           ESTADO_GLOBAL\r\n" + 
				"        FROM (select  usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre,\r\n" + 
				"                MAX(CASE \r\n" + 
				"                  WHEN enc.empresa_id is null THEN 0\r\n" + 
				"                  else 1\r\n" + 
				"                END) ETAPA_1,\r\n" + 
				"                MAX(CASE \r\n" + 
				"                  WHEN (enc.empresa_id is null OR (enc.empresa_id != 2)) THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='REVISAR' AND suma_final!=1) THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 2 and encuestas_hijos_empresa='REVISAR' AND suma_final=1) THEN 1\r\n" + 
				"                END) ETAPA_2,\r\n" + 
				"                MAX(CASE \r\n" + 
				"                  WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'NO') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz != '-') THEN 1\r\n" + 
				"                END) ETAPA_3,\r\n" + 
				"                MAX(CASE \r\n" + 
				"                  WHEN (enc.empresa_id is null OR (enc.empresa_id != 1)) THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='NO') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = '-') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'NO') THEN 0\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'REVISAR' AND suma_final!=1) THEN 1\r\n" + 
				"                  WHEN (enc.empresa_id = 1 and encuestas_hijos_empresa='REVISAR' AND encuesta_hijos_matriz = 'REVISAR' AND suma_final =1) THEN 2\r\n" + 
				"                END) ETAPA_4,\r\n" + 
				"                MAX(CASE  WHEN (encuestas_hijos_empresa = '-') AND suma_final = 0 THEN 2\r\n" + 
				"                          WHEN (encuestas_hijos_empresa = 'REVISAR') AND (encuesta_hijos_matriz = '-' OR encuesta_hijos_matriz = 'REVISAR') AND suma_final = 0 THEN 1\r\n" + 
				"                          ELSE 0\r\n" + 
				"                  END) ESTADO_GLOBAL\r\n" + 
				"          FROM (select z.usuario_codigo,\r\n" + 
				"                       z.posicion_codigo,\r\n" + 
				"                       z.perfil_id,\r\n" + 
				"                       z.perfil_nombre,\r\n" + 
				"                       z.perfil_tipo_id,\r\n" + 
				"                       z.perfil_tipo_nombre,\r\n" + 
				"                       z.empresa_id,\r\n" + 
				"                       z.nombre,\r\n" + 
				"                       z.encuestas_hijos_empresa,\r\n" + 
				"                       z.encuesta_hijos_matriz,\r\n" + 
				"                       z.linea_id,\r\n" + 
				"                       z.canal_id,\r\n" + 
				"                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\r\n" + 
				"                  from (select B.usuario_codigo usuario_codigo,\r\n" + 
				"                               B.posicion_codigo posicion_codigo,\r\n" + 
				"                               b.perfil_id perfil_id,\r\n" + 
				"                               c.nombre perfil_nombre,\r\n" + 
				"                               C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"                               d.nombre perfil_tipo_nombre,\r\n" + 
				"                               e.empresa_id,\r\n" + 
				"                               f.nombre,\r\n" + 
				"                               (case \r\n" + 
				"                                 when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                                 when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                                 when (E.empresa_id is null) THEN '-'\r\n" + 
				"                                 when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"                               end) encuestas_hijos_empresa,\r\n" + 
				"                               (case\r\n" + 
				"                                 when  (I.linea_porcentaje > 0 and I.canal_porcentaje > 0) THEN 'REVISAR'\r\n" + 
				"                                 when  (I.linea_porcentaje > 0 and I.canal_porcentaje <= 0) THEN 'NO'\r\n" + 
				"                                 when  (I.linea_porcentaje is null and I.canal_porcentaje is null)  then '-'\r\n" + 
				"                               end)encuesta_hijos_matriz,\r\n" + 
				"                               I.linea_id,\r\n" + 
				"                               I.canal_id,\r\n" + 
				"                               (case\r\n" + 
				"                                  when (H.porcentaje is not null and I.linea_porcentaje is null and I.canal_porcentaje is null) then h.porcentaje\r\n" + 
				"                                  when (M.porcentaje is not null) then M.porcentaje\r\n" + 
				"                                end) porcentaje_final\r\n" + 
				"                          from usuarios A \r\n" + 
				"                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:proceso_id\r\n" + 
				"                          join perfiles C on C.id = B.perfil_id\r\n" + 
				"                          join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"                          left join empresas F on F.id = E.empresa_id\r\n" + 
				"                          left join centros G on G.empresa_id = 2 and F.id = 2\r\n" + 
				"                          left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\r\n" + 
				"                          left join encuesta_linea_canal I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1 and I.linea_porcentaje > 0\r\n" + 
				"                          left join perfil_linea_canal J  on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id and I.canal_id = J.canal_id\r\n" + 
				"                          left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\r\n" + 
				"                          left join objetos L on L.objeto_tipo_id = 4 and L.padre_objeto_id = J.canal_id\r\n" + 
				"                          left join encuesta_producto_subcanal M on M.proceso_id = B.proceso_id and M.posicion_codigo = B.posicion_codigo and M.producto_id = k.id and M.subcanal_id = L.id\r\n" + 
				"                         where D.id = 3) z\r\n" + 
				"                         group by z.usuario_codigo,z.posicion_codigo,z.perfil_id,z.perfil_nombre,z.perfil_tipo_id,z.perfil_tipo_nombre,z.empresa_id,z.nombre,z.encuestas_hijos_empresa,z.encuesta_hijos_matriz,z.linea_id,z.canal_id) enc\r\n" + 
				"          group by usuario_codigo,posicion_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre)\r\n" + 
				"       ) EG\r\n" + 
				"       JOIN (SELECT C.ID PROCESO_ID,\r\n" + 
				"                     C.nombre PROCESO_NOMBRE,\r\n" + 
				"                     NVL(B.codigo,'VACANTE') USUARIO_CODIGO,\r\n" + 
				"                     NVL(B.nombre_completo,'VACANTE') USUARIO_NOMBRE_COMPLETO,\r\n" + 
				"                     D.codigo POSICION_CODIGO,\r\n" + 
				"                     D.nombre POSICION_NOMBRE,\r\n" + 
				"                     E.nombre AREA_NOMBRE,\r\n" + 
				"                     F.codigo CENTRO_CODIGO,\r\n" + 
				"                     F.nombre CENTRO_NOMBRE,\r\n" + 
				"                     G.nombre PERFIL_NOMBRE,\r\n" + 
				"                     G.perfil_tipo_id,\r\n" + 
				"                     H.nombre perfil_tipo_nombre,\r\n" + 
				"                     J.codigo RESPONSABLE_CODIGO,\r\n" + 
				"                     J.nombre_completo RESPONSABLE_NOMBRE_COMPLETO,\r\n" + 
				"                     MAX(K.FECHA_ACTUALIZACION) FECHA_ACTUALIZACION\r\n" + 
				"                FROM posicion_datos A\r\n" + 
				"                LEFT JOIN usuarios B ON B.codigo=A.usuario_codigo\r\n" + 
				"                JOIN procesos C ON C.id=A.proceso_id\r\n" + 
				"                JOIN posiciones D ON D.codigo=A.posicion_codigo\r\n" + 
				"                JOIN areas E ON E.id=A.area_id\r\n" + 
				"                JOIN centros F ON F.id=A.centro_id\r\n" + 
				"                JOIN perfiles G ON G.id=A.perfil_id\r\n" + 
				"                JOIN perfil_tipos H ON H.id=G.perfil_tipo_id\r\n" + 
				"                JOIN posicion_datos I ON I.posicion_codigo = A.responsable_posicion_codigo\r\n" + 
				"                JOIN usuarios J ON J.codigo = I.usuario_codigo\r\n" + 
				"                LEFT JOIN encuestas K ON K.posicion_codigo = A.posicion_codigo\r\n" + 
				"               WHERE A.proceso_id=:proceso_id\r\n";
		sql += filtroAreas(areas, "A");
		sql += filtroCentros(centros, "A");
		sql += 	"GROUP BY C.ID,C.nombre,B.codigo,B.nombre_completo,D.codigo,D.nombre,E.nombre,F.codigo,F.nombre,G.nombre,G.perfil_tipo_id,H.nombre,J.codigo,J.nombre_completo\r\n" + 
				"               ) USR ON USR.USUARIO_CODIGO = EG.USUARIO_CODIGO";
		plantilla.update(sql, new MapSqlParameterSource("proceso_id", procesoId));
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
		
		String sql = "SELECT SYSDATE FECHA_DESCARGA,\r\n" + 
					"       A.PROCESO_NOMBRE PROCESO,\r\n" + 
					"       A.USUARIO_CODIGO MATRICULA,\r\n" + 
					"       A.USUARIO_NOMBRE_COMPLETO COLABORADOR,\r\n" + 
					"       A.POSICION_CODIGO NRO_POSICION,\r\n" + 
					"       A.POSICION_NOMBRE POSICION,\r\n" + 
					"       A.AREA_NOMBRE AREA,\r\n" + 
					"       A.CENTRO_CODIGO CECO_CODIGO,\r\n" + 
					"       A.CENTRO_NOMBRE CECO_NOMBRE,\r\n" + 
					"       A.PERFIL_NOMBRE PERFIL,\r\n" + 
					"       A.PERFIL_TIPO_NOMBRE PERFIL_TIPO,\r\n" + 
					"       A.RESPONSABLE_CODIGO RESPONSABLE_MATRICULA,\r\n" + 
					"       A.RESPONSABLE_NOMBRE_COMPLETO RESPONSABLE,\r\n" + 
					"       CASE WHEN ETAPA_1=0 THEN 'NO INICIADA'\r\n" + 
					"            WHEN ETAPA_1=1 THEN 'COMPLETADA'\r\n" + 
					"       END ETAPA_1,\r\n" + 
					"       CASE WHEN ETAPA_2=0 THEN 'NO INICIADA'\r\n" + 
					"            WHEN ETAPA_2=1 THEN 'COMPLETADA'\r\n" + 
					"       END ETAPA_2,\r\n" + 
					"       CASE WHEN ETAPA_3=0 THEN 'NO INICIADA'\r\n" + 
					"            WHEN ETAPA_3=1 THEN 'COMPLETADA'\r\n" + 
					"       END ETAPA_3,\r\n" + 
					"       CASE WHEN ETAPA_4=0 THEN 'NO INICIADA'\r\n" + 
					"            WHEN ETAPA_4=1 THEN 'INICIADA'\r\n" + 
					"            WHEN ETAPA_4=2 THEN 'COMPLETADA'\r\n" + 
					"       END ETAPA_4,\r\n" + 
					"       CASE WHEN ETAPA_TOTAL=0 THEN 'NO INICIADA'\r\n" + 
					"            WHEN ETAPA_TOTAL=1 THEN 'INICIADA'\r\n" + 
					"            WHEN ETAPA_TOTAL=2 THEN 'COMPLETADA'\r\n" + 
					"      END ESTADO_GLOBAL,\r\n" + 
					"       A.ULT_FECHA_ACTUALIZACION ULTIMA_MODIFICACION\r\n" + 
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
