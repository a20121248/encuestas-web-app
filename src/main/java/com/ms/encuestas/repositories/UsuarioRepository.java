package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Rol;
import com.ms.encuestas.models.Usuario;

@Repository
public class UsuarioRepository {
	private Logger logger = LoggerFactory.getLogger(UsuarioRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String posicionCodigo) {
		String sql = "SELECT  usr.usuario_codigo,\r\n" + 
				"        usr.usuario_contrasenha,\r\n" + 
				"        usr.usuario_nombre_completo,\r\n" + 
				"        usr.usuario_fecha_creacion,\r\n" + 
				"        usr.usuario_fecha_actualizacion,\r\n" + 
				"        usr.area_id,\r\n" + 
				"        usr.area_nombre,\r\n" + 
				"        usr.area_division,\r\n" + 
				"        usr.area_fecha_creacion,\r\n" + 
				"        usr.area_fecha_actualizacion,\r\n" + 
				"        usr.posicion_codigo,\r\n" + 
				"        usr.posicion_nombre,\r\n" + 
				"        usr.posicion_fecha_creacion,\r\n" + 
				"        usr.posicion_fecha_actualizacion,\r\n" + 
				"        eg.estado estado\r\n" + 
				"FROM (SELECT usuario_codigo,\r\n" + 
				"       MAX(CASE WHEN (encuestas_hijos = 'REVISAR' OR encuestas_hijos = '-') AND SUMA_FINAL = 0 THEN 1\r\n" + 
				"                ELSE 0\r\n" + 
				"           END) ESTADO\r\n" + 
				"FROM (SELECT z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos,\r\n" + 
				"              coalesce(sum(z.porcentaje_final),0) SUMA_FINAL \r\n" + 
				"FROM (select B.usuario_codigo usuario_codigo,\r\n" + 
				"            B.posicion_codigo posicion_codigo,\r\n" + 
				"            b.perfil_id perfil_id,\r\n" + 
				"            c.nombre perfil_nombre,\r\n" + 
				"            C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"            d.nombre perfil_tipo,\r\n" + 
				"            E.empresa_id empresa_id,\r\n" + 
				"            F.nombre,\r\n" + 
				"            (case \r\n" + 
				"              when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"              when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"              when (E.empresa_id is null) THEN '-'\r\n" + 
				"              when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"            end) encuestas_hijos,\r\n" + 
				"            (case\r\n" + 
				"               when (I.porcentaje is not null) then I.porcentaje\r\n" + 
				"               when (K.porcentaje is not null) then K.porcentaje\r\n" + 
				"             end) porcentaje_final\r\n" + 
				"      from usuarios A \r\n" + 
				"      join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\r\n" + 
				"      join perfiles C on C.id = B.perfil_id\r\n" + 
				"      join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"      left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"      left join empresas F on F.id = E.empresa_id\r\n" + 
				"      left join perfil_centro G on G.perfil_id = B.perfil_id and F.id=1 \r\n" + 
				"      left join centros H on H.id = G.centro_id  and H.empresa_id = F.id\r\n" + 
				"      left join encuesta_centro I on I.proceso_id = B.proceso_id and I.posicion_codigo = B.posicion_codigo and I.centro_id = H.id\r\n" + 
				"      left join centros J on F.id = 2 and J.empresa_id = F.id\r\n" + 
				"      left join encuesta_centro k on K.proceso_id = B.proceso_id and K.posicion_codigo = B.posicion_codigo and K.centro_id = J.id\r\n" + 
				"      where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 1)z\r\n" + 
				"      group by z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos) faltan\r\n" + 
				"      group by usuario_codigo\r\n" + 
				"      union\r\n" + 
				"      select usuario_codigo,\r\n" + 
				"       MAX(CASE WHEN (faltan.encuestas_hijos_empresa = 'REVISAR' or faltan.encuestas_hijos_empresa = '-' ) AND (faltan.encuesta_hijos_matriz = '-' OR faltan.encuesta_hijos_matriz = 'REVISAR') AND faltan.suma_final = 0 THEN 1\r\n" + 
				"                ELSE 0\r\n" + 
				"           END) ESTADO\r\n" + 
				"from (select z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos_empresa,\r\n" + 
				"              z.encuesta_hijos_matriz,\r\n" + 
				"              z.linea_id,\r\n" + 
				"              coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\r\n" + 
				"from (select B.usuario_codigo usuario_codigo,\r\n" + 
				"              B.posicion_codigo posicion_codigo,\r\n" + 
				"              b.perfil_id perfil_id,\r\n" + 
				"              c.nombre perfil_nombre,\r\n" + 
				"              C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"              d.nombre perfil_tipo,\r\n" + 
				"              e.empresa_id,\r\n" + 
				"              f.nombre,\r\n" + 
				"              (case \r\n" + 
				"                when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                when (E.empresa_id is null) THEN '-'\r\n" + 
				"                when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"              end) encuestas_hijos_empresa,\r\n" + 
				"              (case\r\n" + 
				"                when  I.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"                when  I.porcentaje <= 0 THEN 'NO'\r\n" + 
				"                when  I.porcentaje is null then '-'\r\n" + 
				"              end)encuesta_hijos_matriz,\r\n" + 
				"              I.linea_id,\r\n" + 
				"              (case\r\n" + 
				"                when (H.porcentaje is not null and I.porcentaje is null) then h.porcentaje\r\n" + 
				"                when (L.porcentaje is not null) then L.porcentaje\r\n" + 
				"                end) porcentaje_final\r\n" + 
				"        from usuarios A \r\n" + 
				"        join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\r\n" + 
				"        join perfiles C on C.id = B.perfil_id\r\n" + 
				"        join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"        left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"        left join empresas F on F.id = E.empresa_id\r\n" + 
				"        left join centros G on G.empresa_id = 2 and F.id = 2\r\n" + 
				"        left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\r\n" + 
				"        left join encuesta_linea I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1\r\n" + 
				"        left join perfil_linea_canal J on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id\r\n" + 
				"        left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\r\n" + 
				"        left join encuesta_Producto_canal L on L.proceso_id = B.proceso_id and L.posicion_codigo = B.posicion_codigo and J.canal_id = L.canal_id and L.producto_id = K.id\r\n" + 
				"        where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 2) z \r\n" + 
				"        group by z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos_empresa,\r\n" + 
				"              z.encuesta_hijos_matriz,\r\n" + 
				"              z.linea_id\r\n" + 
				") faltan\r\n" + 
				"group by usuario_codigo\r\n" + 
				"union\r\n" + 
				"SELECT usuario_codigo,\r\n" + 
				"       MAX(CASE WHEN (faltan.encuestas_hijos_empresa = 'REVISAR' or faltan.encuestas_hijos_empresa = '-' ) AND (faltan.encuesta_hijos_matriz = '-' OR faltan.encuesta_hijos_matriz = 'REVISAR') AND faltan.suma_final = 0 THEN 1\r\n" + 
				"                ELSE 0\r\n" + 
				"           END) ESTADO\r\n" + 
				"FROM ( select z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos_empresa,\r\n" + 
				"              z.encuesta_hijos_matriz,\r\n" + 
				"              z.linea_id,\r\n" + 
				"              z.canal_id,\r\n" + 
				"      coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\r\n" + 
				"from (select B.usuario_codigo usuario_codigo,\r\n" + 
				"            B.posicion_codigo posicion_codigo,\r\n" + 
				"            b.perfil_id perfil_id,\r\n" + 
				"            c.nombre perfil_nombre,\r\n" + 
				"            C.perfil_tipo_id perfil_tipo_id,\r\n" + 
				"            d.nombre perfil_tipo,\r\n" + 
				"            e.empresa_id,\r\n" + 
				"            f.nombre,\r\n" + 
				"            (case \r\n" + 
				"              when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\r\n" + 
				"              when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\r\n" + 
				"              when (E.empresa_id is null) THEN '-'\r\n" + 
				"              when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\r\n" + 
				"            end) encuestas_hijos_empresa,\r\n" + 
				"            (case\r\n" + 
				"              when  (I.linea_porcentaje > 0 and I.canal_porcentaje > 0) THEN 'REVISAR'\r\n" + 
				"              when  (I.linea_porcentaje > 0 and I.canal_porcentaje <= 0) THEN 'NO'\r\n" + 
				"              when  (I.linea_porcentaje is null and I.canal_porcentaje is null)  then '-'\r\n" + 
				"            end)encuesta_hijos_matriz,\r\n" + 
				"            I.linea_id,\r\n" + 
				"            I.canal_id,\r\n" + 
				"            (case\r\n" + 
				"               when (H.porcentaje is not null and I.linea_porcentaje is null and I.canal_porcentaje is null) then h.porcentaje\r\n" + 
				"               when (M.porcentaje is not null) then M.porcentaje\r\n" + 
				"             end) porcentaje_final\r\n" + 
				"      from usuarios A \r\n" + 
				"      join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\r\n" + 
				"      join perfiles C on C.id = B.perfil_id\r\n" + 
				"      join perfil_tipos D on c.perfil_tipo_id = d.id\r\n" + 
				"      left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\r\n" + 
				"      left join empresas F on F.id = E.empresa_id\r\n" + 
				"      left join centros G on G.empresa_id = 2 and F.id = 2\r\n" + 
				"      left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\r\n" + 
				"      left join encuesta_linea_canal I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1 and I.linea_porcentaje > 0\r\n" + 
				"      left join perfil_linea_canal J  on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id and I.canal_id = J.canal_id\r\n" + 
				"      left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\r\n" + 
				"      left join objetos L on L.objeto_tipo_id = 4 and L.padre_objeto_id = J.canal_id\r\n" + 
				"      left join encuesta_producto_subcanal M on M.proceso_id = B.proceso_id and M.posicion_codigo = B.posicion_codigo and M.producto_id = k.id and M.subcanal_id = L.id\r\n" + 
				"      where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 3) z\r\n" + 
				"       group by z.usuario_codigo,\r\n" + 
				"              z.posicion_codigo,\r\n" + 
				"              z.perfil_id,\r\n" + 
				"              z.perfil_nombre,\r\n" + 
				"              z.perfil_tipo_id,\r\n" + 
				"              z.perfil_tipo,\r\n" + 
				"              z.empresa_id,\r\n" + 
				"              z.nombre,\r\n" + 
				"              z.encuestas_hijos_empresa,\r\n" + 
				"              z.encuesta_hijos_matriz,\r\n" + 
				"              z.linea_id,\r\n" + 
				"              z.canal_id) faltan\r\n" + 
				"group by usuario_codigo\r\n" + 
				") EG\r\n" + 
				"JOIN (SELECT A.usuario_codigo,\r\n" + 
				"       B.contrasenha usuario_contrasenha,\r\n" + 
				"       B.nombre_completo usuario_nombre_completo,\r\n" + 
				"       B.fecha_creacion usuario_fecha_creacion,\r\n" + 
				"       B.fecha_actualizacion usuario_fecha_actualizacion,\r\n" + 
				"       A.area_id,\r\n" + 
				"       C.nombre area_nombre,\r\n" + 
				"       C.division area_division,\r\n" + 
				"       C.fecha_creacion area_fecha_creacion,\r\n" + 
				"       C.fecha_actualizacion area_fecha_actualizacion,\r\n" + 
				"       D.codigo posicion_codigo,\r\n" + 
				"       D.nombre posicion_nombre,\r\n" + 
				"       D.fecha_creacion posicion_fecha_creacion,\r\n" + 
				"       D.fecha_actualizacion posicion_fecha_actualizacion\r\n" + 
				"  FROM posicion_datos A\r\n" + 
				"  JOIN usuarios B ON A.usuario_codigo=B.codigo\r\n" + 
				"  JOIN areas C ON A.area_id=C.id\r\n" + 
				"  JOIN posiciones D ON A.posicion_codigo=D.codigo\r\n" + 
				" WHERE proceso_id=:procesoId\r\n" + 
				"   AND responsable_posicion_codigo='66322') USR ON USR.USUARIO_CODIGO = EG.USUARIO_CODIGO\r\n" + 
				"ORDER BY usuario_codigo" ;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("procesoId", procesoId);
        paramMap.put("posicionCodigo", posicionCodigo);
		return plantilla.query(sql, paramMap, new UsuarioMapper());
	}
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM usuarios WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Usuario> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT B.codigo usuario_codigo,\n" +
				 	 "       B.contrasenha usuario_contrasenha,\n" + 
				 	 "       B.nombre_completo usuario_nombre_completo,\n" + 
				 	 "       B.fecha_creacion usuario_fecha_creacion,\n" + 
				 	 "       B.fecha_actualizacion usuario_fecha_actualizacion,\n" + 
				 	 "       A.area_id,\n" + 
				 	 "       C.nombre area_nombre,\n" +
				 	 "       C.division area_division,\n" +
				 	 "       C.fecha_creacion area_fecha_creacion,\n" + 
				 	 "       C.fecha_actualizacion area_fecha_actualizacion,\n" +
				 	 "       D.codigo posicion_codigo,\n" + 
				 	 "       D.nombre posicion_nombre,\n" + 
				 	 "       D.fecha_creacion posicion_fecha_creacion,\n" + 
				 	 "       D.fecha_actualizacion posicion_fecha_actualizacion\n" +
				 	 "  FROM usuarios B \n" + 
				 	 "  LEFT JOIN posicion_datos A ON A.usuario_codigo=B.codigo AND A.proceso_id=0\n" + 
				 	 "  LEFT JOIN areas C ON A.area_id=C.id\n" +
				 	 "  LEFT JOIN posiciones D ON A.posicion_codigo=D.codigo\n" +
				 	 " WHERE B.fecha_eliminacion IS NULL";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return plantilla.query(sql, paramMap, new UsuarioMapper());
	}

	public Usuario findByUsuarioGenerales(String usuarioRed) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE A.usuario_generales=:usuario_red\n" +
					 "   AND A.fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_red", usuarioRed), new UsuarioMapper());
	}
	
	public Usuario findByUsuarioVida(String usuarioRed) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE A.usuario_vida=:usuario_red\n" +
					 "   AND A.fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_red", usuarioRed), new UsuarioMapper());
	}
	
	public Usuario findByCodigo(String usuarioCodigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE A.codigo=:usuario_codigo\n" +
					 "   AND A.fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_codigo", usuarioCodigo), new UsuarioMapper());
	}
	
	public Usuario findByCodigoAndProceso(String usuarioCodigo, Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       B.posicion_codigo,\n" +
				     "       C.nombre posicion_nombre,\n" +
				     "       C.fecha_creacion posicion_fecha_creacion,\n" +
				     "       C.fecha_actualizacion posicion_fecha_actualizacion,\n" +
				     "       B.area_id,\n" +
				     "       D.nombre area_nombre,\n" +
				     "       D.division area_division,\n" +
				     "       D.fecha_creacion area_fecha_creacion,\n" +
				     "       D.fecha_actualizacion area_fecha_actualizacion,\n" +
				     "       B.centro_id,\n" +
				     "       E.codigo centro_codigo,\n" +
				     "       E.nombre centro_nombre,\n" +
				     "       E.nivel centro_nivel,\n" +
				     "       E.grupo centro_grupo,\n" +
				     "       E.fecha_creacion centro_fecha_creacion,\n" +
				     "       E.fecha_actualizacion centro_fecha_actualizacion,\n" +
				     "       E.centro_tipo_id,\n" +
				     "       F.nombre centro_tipo_nombre,\n" +
				     "       F.fecha_creacion centro_tipo_fec_creacion,\n" +
				     "       F.fecha_actualizacion centro_tipo_fec_actualizacion\n" +
					 "  FROM usuarios A\n" +
					 "  LEFT JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" +
					 "  LEFT JOIN posiciones C ON B.posicion_codigo=C.codigo\n" +
					 "  LEFT JOIN areas D ON B.area_id=D.id\n" +
					 "  LEFT JOIN centros E ON B.centro_id=E.id\n" +
					 "  LEFT JOIN centro_tipos F ON E.centro_tipo_id=F.id\n" +
				     " WHERE A.codigo=:usuario_codigo\n" +
					 "   AND B.proceso_id=:proceso_id\n" +
					 "   AND A.fecha_eliminacion IS NULL";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("usuario_codigo", usuarioCodigo);
        paramMap.put("proceso_id", procesoId);
		return plantilla.queryForObject(sql, paramMap, new UsuarioMapper());
	}
	
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       B.posicion_codigo,\n" +
				     "       C.nombre posicion_nombre,\n" +
				     "       C.fecha_creacion posicion_fecha_creacion,\n" +
				     "       C.fecha_actualizacion posicion_fecha_actualizacion,\n" +
				     "       B.area_id,\n" +
				     "       D.nombre area_nombre,\n" +
				     "       D.division area_division,\n" +
				     "       D.fecha_creacion area_fecha_creacion,\n" +
				     "       D.fecha_actualizacion area_fecha_actualizacion,\n" +
				     "       B.centro_id,\n" +
				     "       E.codigo centro_codigo,\n" +
				     "       E.nombre centro_nombre,\n" +
				     "       E.nivel centro_nivel,\n" +
				     "       E.grupo centro_grupo,\n" +
				     "       E.fecha_creacion centro_fecha_creacion,\n" +
				     "       E.fecha_actualizacion centro_fecha_actualizacion,\n" +
				     "       E.centro_tipo_id,\n" +
				     "       F.nombre centro_tipo_nombre,\n" +
				     "       F.fecha_creacion centro_tipo_fec_creacion,\n" +
				     "       F.fecha_actualizacion centro_tipo_fec_actualizacion,\n" +
				     "       H.id perfil_id,\n" +
				     "       H.nombre perfil_nombre,\n" +
				     "       I.id perfil_tipo_id,\n" +
				     "       I.nombre perfil_tipo_nombre\n" +
					 "  FROM usuarios A\n" +
					 "  LEFT JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" +
					 "  LEFT JOIN posiciones C ON B.posicion_codigo=C.codigo\n" +
					 "  LEFT JOIN areas D ON B.area_id=D.id\n" +
					 "  LEFT JOIN centros E ON B.centro_id=E.id\n" +
					 "  LEFT JOIN centro_tipos F ON E.centro_tipo_id=F.id\n" +
					 "  LEFT JOIN perfiles H ON B.perfil_id=H.id\n" +
					 "  LEFT JOIN perfil_tipos I ON H.perfil_tipo_id=I.id\n" +
				     " WHERE proceso_id=:procesoId\n" +
					 "   AND B.posicion_codigo=:posicionCodigo\n" +
					 "   AND A.fecha_eliminacion IS NULL";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("procesoId", procesoId);
        paramMap.put("posicionCodigo", posicionCodigo);
		return plantilla.queryForObject(sql,paramMap,new UsuarioMapper());
	}
	
	public Usuario findByCodigoWithPosicion(String codigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
					 "       B.posicion_codigo,\n" + 
					 "       C.nombre posicion_nombre,\n" + 
					 "       C.fecha_creacion posicion_fecha_creacion\n" +
					 "  FROM usuarios A\n" + 
					 "  JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" + 
					 "  JOIN posiciones C ON B.posicion_codigo=C.codigo\n" + 
					 " WHERE A.codigo=:codigo\n" + 
					 "   AND A.fecha_eliminacion IS NULL";		
		return plantilla.queryForObject(sql,
				new MapSqlParameterSource("codigo", codigo),
				new UsuarioMapper());
	}

	public Usuario save(Usuario usuario) {
		return null;
	}

	public void delete(Usuario usuario) {
		return;
	}

	public List<Rol> findRolesByCodigo(String codigo) {
		String sql = "SELECT B.*\n" +
					 "  FROM rol_usuario A\n" +
					 "  JOIN roles B\n" +
					 "    ON A.rol_id=B.id\n" + 
					 " WHERE A.usuario_codigo=:codigo\n" + 
					 "   AND B.fecha_eliminacion IS NULL";
		return plantilla.query(sql, new MapSqlParameterSource("codigo", codigo), new RolMapper());
	}
}