package com.ms.encuestas.repositories;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Rol;
import com.ms.encuestas.models.Usuario;

@Repository
public class UsuarioRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM usuarios";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}

	public List<Usuario> findUsuariosDependientes(Long procesoId, String posicionCodigo) {
		String sql = "SELECT usr.usuario_codigo,\n" + 
                     "       usr.usuario_contrasenha,\n" + 
                     "       usr.usuario_nombre_completo,\n" + 
                     "       usr.usuario_fecha_creacion,\n" + 
                     "       usr.usuario_fecha_actualizacion,\n" + 
                  "       usr.area_id,\n" + 
                  "       usr.area_nombre,\n" + 
                  "       usr.area_division,\n" + 
                  "       usr.area_fecha_creacion,\n" + 
                  "       usr.area_fecha_actualizacion,\n" + 
                  "       usr.posicion_codigo,\n" + 
                  "       usr.posicion_nombre,\n" + 
                  "       usr.posicion_fecha_creacion,\n" + 
                  "       usr.posicion_fecha_actualizacion,\n" +
                  "       eg.perfil_id,eg.perfil_nombre,eg.perfil_tipo_id,eg.perfil_tipo_nombre,\n" +
                  "       eg.estado estado\n" + 
                  "  FROM (SELECT usuario_codigo,\n" + 
                  "               MAX(CASE WHEN (encuestas_hijos = 'REVISAR' OR encuestas_hijos = '-') AND SUMA_FINAL = 0 THEN 1\n" + 
                  "                        ELSE 0\n" + 
                  "               END) ESTADO,\n" +
                  "               perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" +
                  "          FROM (SELECT z.usuario_codigo,\n" + 
                  "                       z.posicion_codigo,\n" + 
                  "                       z.perfil_id,\n" + 
                  "                       z.perfil_nombre,\n" + 
                  "                       z.perfil_tipo_id,\n" + 
                  "                       z.perfil_tipo_nombre,\n" + 
                  "                       z.empresa_id,\n" + 
                  "                       z.nombre,\n" + 
                  "                       z.encuestas_hijos,\n" + 
                  "                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL \n" + 
                  "                  FROM (select B.usuario_codigo usuario_codigo,\n" + 
                  "                               B.posicion_codigo posicion_codigo,\n" + 
                  "                               b.perfil_id perfil_id,\n" + 
                  "                               c.nombre perfil_nombre,\n" + 
                  "                               C.perfil_tipo_id perfil_tipo_id,\n" + 
                  "                               d.nombre perfil_tipo_nombre,\n" + 
                  "                               E.empresa_id empresa_id,\n" + 
                  "                               F.nombre,\n" + 
                  "                               (case \n" + 
                  "                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\n" + 
                  "                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\n" + 
                  "                                     when (E.empresa_id is null) THEN '-'\n" + 
                  "                                     when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\n" + 
                  "                               end) encuestas_hijos,\n" + 
                  "                               (case\n" + 
                  "                                     when (I.porcentaje is not null) then I.porcentaje\n" + 
                  "                                     when (K.porcentaje is not null) then K.porcentaje\n" + 
                  "                               end) porcentaje_final\n" + 
                  "                          from usuarios A \n" + 
                  "                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\n" + 
                  "                          join perfiles C on C.id = B.perfil_id\n" + 
                  "                          join perfil_tipos D on c.perfil_tipo_id = d.id\n" + 
                  "                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\n" + 
                  "                          left join empresas F on F.id = E.empresa_id\n" + 
                  "                          left join perfil_centro G on G.perfil_id = B.perfil_id and F.id=1 \n" + 
                  "                          left join centros H on H.id = G.centro_id  and H.empresa_id = F.id\n" + 
                  "                          left join encuesta_centro I on I.proceso_id = B.proceso_id and I.posicion_codigo = B.posicion_codigo and I.centro_id = H.id\n" + 
                  "                          left join centros J on F.id = 2 and J.empresa_id = F.id\n" + 
                  "                          left join encuesta_centro k on K.proceso_id = B.proceso_id and K.posicion_codigo = B.posicion_codigo and K.centro_id = J.id\n" + 
                  "                         where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 1)z\n" + 
                  "                 group by z.usuario_codigo,\n" + 
                  "                          z.posicion_codigo,\n" + 
                  "                          z.perfil_id,\n" + 
                  "                          z.perfil_nombre,\n" + 
                  "                          z.perfil_tipo_id,\n" + 
                  "                          z.perfil_tipo_nombre,\n" + 
                  "                          z.empresa_id,\n" + 
                  "                          z.nombre,\n" + 
                  "                          z.encuestas_hijos) faltan\n" + 
                  "         GROUP BY usuario_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" + 
                  "         union\n" + 
                  "        select usuario_codigo,\n" + 
                  "               MAX(CASE WHEN (faltan.encuestas_hijos_empresa = 'REVISAR' or faltan.encuestas_hijos_empresa = '-' ) AND (faltan.encuesta_hijos_matriz = '-' OR faltan.encuesta_hijos_matriz = 'REVISAR') AND faltan.suma_final = 0 THEN 1\n" + 
                  "                        ELSE 0\n" + 
                  "               END) ESTADO,\n" + 
                  "               perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" + 
                  "          from (select z.usuario_codigo,\n" + 
                  "                       z.posicion_codigo,\n" + 
                  "                       z.perfil_id,\n" + 
                  "                       z.perfil_nombre,\n" + 
                  "                       z.perfil_tipo_id,\n" + 
                  "                       z.perfil_tipo_nombre,\n" + 
                  "                       z.empresa_id,\n" + 
                  "                       z.nombre,\n" + 
                  "                       z.encuestas_hijos_empresa,\n" + 
                  "                       z.encuesta_hijos_matriz,\n" + 
                  "                       z.linea_id,\n" + 
                  "                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\n" + 
                  "                  from (select B.usuario_codigo usuario_codigo,\n" + 
                  "                               B.posicion_codigo posicion_codigo,\n" + 
                  "                               b.perfil_id perfil_id,\n" + 
                  "                               c.nombre perfil_nombre,\n" + 
                  "                               C.perfil_tipo_id perfil_tipo_id,\n" + 
                  "                               d.nombre perfil_tipo_nombre,\n" + 
                  "                               e.empresa_id,\n" + 
                  "                               f.nombre,\n" + 
                  "                               (case when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\n" + 
                  "                                     when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\n" + 
                  "                                     when (E.empresa_id is null) THEN '-'\n" + 
                  "                                     when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\n" + 
                  "                               end) encuestas_hijos_empresa,\n" + 
                  "                               (case when  I.porcentaje > 0 THEN 'REVISAR'\n" + 
                  "                                     when  I.porcentaje <= 0 THEN 'NO'\n" + 
                  "                                     when  I.porcentaje is null then '-'\n" + 
                  "                               end) encuesta_hijos_matriz,\n" + 
                  "                               I.linea_id,\n" + 
                  "                               (case when (H.porcentaje is not null and I.porcentaje is null) then h.porcentaje\n" + 
                  "                                     when (L.porcentaje is not null) then L.porcentaje\n" + 
                  "                               end) porcentaje_final\n" + 
                  "                          from usuarios A \n" + 
                  "                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\n" + 
                  "                          join perfiles C on C.id = B.perfil_id\n" + 
                  "                          join perfil_tipos D on c.perfil_tipo_id = d.id\n" + 
                  "                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\n" + 
                  "                          left join empresas F on F.id = E.empresa_id\n" + 
                  "                          left join centros G on G.empresa_id = 2 and F.id = 2\n" + 
                  "                          left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\n" + 
                  "                          left join encuesta_linea I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1\n" + 
                  "                          left join perfil_linea_canal J on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id\n" + 
                  "                          left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\n" + 
                  "                          left join encuesta_Producto_canal L on L.proceso_id = B.proceso_id and L.posicion_codigo = B.posicion_codigo and J.canal_id = L.canal_id and L.producto_id = K.id\n" + 
                  "                         where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 2) z \n" + 
                  "                 group by z.usuario_codigo,\n" + 
                  "                          z.posicion_codigo,\n" + 
                  "                          z.perfil_id,\n" + 
                  "                          z.perfil_nombre,\n" + 
                  "                          z.perfil_tipo_id,\n" + 
                  "                          z.perfil_tipo_nombre,\n" + 
                  "                          z.empresa_id,\n" + 
                  "                          z.nombre,\n" + 
                  "                          z.encuestas_hijos_empresa,\n" + 
                  "                          z.encuesta_hijos_matriz,\n" + 
                  "                          z.linea_id\n" + 
                  "                ) faltan\n" + 
                  "         group by usuario_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" + 
                  "         union\n" + 
                  "        SELECT usuario_codigo,\n" + 
                  "               MAX(CASE WHEN (faltan.encuestas_hijos_empresa = 'REVISAR' or faltan.encuestas_hijos_empresa = '-' ) AND (faltan.encuesta_hijos_matriz = '-' OR faltan.encuesta_hijos_matriz = 'REVISAR') AND faltan.suma_final = 0 THEN 1\n" + 
                  "                        ELSE 0\n" + 
                  "               END) ESTADO,\n" + 
                  "               perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" + 
                  "          FROM (select z.usuario_codigo,\n" + 
                  "                       z.posicion_codigo,\n" + 
                  "                       z.perfil_id,\n" + 
                  "                       z.perfil_nombre,\n" + 
                  "                       z.perfil_tipo_id,\n" + 
                  "                       z.perfil_tipo_nombre,\n" + 
                  "                       z.empresa_id,\n" + 
                  "                       z.nombre,\n" + 
                  "                       z.encuestas_hijos_empresa,\n" + 
                  "                       z.encuesta_hijos_matriz,\n" + 
                  "                       z.linea_id,\n" + 
                  "                       z.canal_id,\n" + 
                  "                       coalesce(sum(z.porcentaje_final),0) SUMA_FINAL\n" + 
                  "                  from (select B.usuario_codigo usuario_codigo,\n" + 
                  "                               B.posicion_codigo posicion_codigo,\n" + 
                  "                               b.perfil_id perfil_id,\n" + 
                  "                               c.nombre perfil_nombre,\n" + 
                  "                               C.perfil_tipo_id perfil_tipo_id,\n" + 
                  "                               d.nombre perfil_tipo_nombre,\n" + 
                  "                               e.empresa_id,\n" + 
                  "                               f.nombre,\n" + 
                  "                               (case \n" + 
                  "                                 when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje > 0 THEN 'REVISAR'\n" + 
                  "                                 when (E.empresa_id = 1 or E.empresa_id = 2) and E.porcentaje <= 0 THEN 'NO'\n" + 
                  "                                 when (E.empresa_id is null) THEN '-'\n" + 
                  "                                 when (E.empresa_id != 1 or E.empresa_id != 2) THEN 'NO'\n" + 
                  "                               end) encuestas_hijos_empresa,\n" + 
                  "                               (case\n" + 
                  "                                 when  (I.linea_porcentaje > 0 and I.canal_porcentaje > 0) THEN 'REVISAR'\n" + 
                  "                                 when  (I.linea_porcentaje > 0 and I.canal_porcentaje <= 0) THEN 'NO'\n" + 
                  "                                 when  (I.linea_porcentaje is null and I.canal_porcentaje is null)  then '-'\n" + 
                  "                               end)encuesta_hijos_matriz,\n" + 
                  "                               I.linea_id,\n" + 
                  "                               I.canal_id,\n" + 
                  "                               (case\n" + 
                  "                                  when (H.porcentaje is not null and I.linea_porcentaje is null and I.canal_porcentaje is null) then h.porcentaje\n" + 
                  "                                  when (M.porcentaje is not null) then M.porcentaje\n" + 
                  "                                end) porcentaje_final\n" + 
                  "                          from usuarios A \n" + 
                  "                          join posicion_datos B on B.usuario_codigo = A.codigo and b.proceso_id =:procesoId\n" + 
                  "                          join perfiles C on C.id = B.perfil_id\n" + 
                  "                          join perfil_tipos D on c.perfil_tipo_id = d.id\n" + 
                  "                          left join encuesta_empresa E on E.posicion_codigo = B.posicion_codigo and E.proceso_id = b.proceso_id\n" + 
                  "                          left join empresas F on F.id = E.empresa_id\n" + 
                  "                          left join centros G on G.empresa_id = 2 and F.id = 2\n" + 
                  "                          left join encuesta_centro H on H.proceso_id = b.proceso_id and H.posicion_codigo = B.posicion_codigo and H.centro_id = G.id\n" + 
                  "                          left join encuesta_linea_canal I on  I.proceso_id = b.proceso_id and I.posicion_codigo = B.posicion_codigo and e.empresa_id = 1 and I.linea_porcentaje > 0\n" + 
                  "                          left join perfil_linea_canal J  on j.perfil_id = B.perfil_id and I.linea_id = J.linea_id and I.canal_id = J.canal_id\n" + 
                  "                          left join objetos K on k.objeto_tipo_id = 3 and K.padre_objeto_id = J.linea_id\n" + 
                  "                          left join objetos L on L.objeto_tipo_id = 4 and L.padre_objeto_id = J.canal_id\n" + 
                  "                          left join encuesta_producto_subcanal M on M.proceso_id = B.proceso_id and M.posicion_codigo = B.posicion_codigo and M.producto_id = k.id and M.subcanal_id = L.id\n" + 
                  "                         where B.RESPONSABLE_POSICION_CODIGO =:posicionCodigo and D.id = 3) z\n" + 
                  "                         group by z.usuario_codigo,\n" + 
                  "                                  z.posicion_codigo,\n" + 
                  "                                  z.perfil_id,\n" + 
                  "                                  z.perfil_nombre,\n" + 
                  "                                  z.perfil_tipo_id,\n" + 
                  "                                  z.perfil_tipo_nombre,\n" + 
                  "                                  z.empresa_id,\n" + 
                  "                                  z.nombre,\n" + 
                  "                                  z.encuestas_hijos_empresa,\n" + 
                  "                                  z.encuesta_hijos_matriz,\n" + 
                  "                                  z.linea_id,\n" + 
                  "                                  z.canal_id) faltan\n" + 
                  "          group by usuario_codigo,perfil_id,perfil_nombre,perfil_tipo_id,perfil_tipo_nombre\n" + 
                  "       ) EG\n" + 
                  "       JOIN (SELECT A.usuario_codigo,\n" + 
                  "                    B.contrasenha usuario_contrasenha,\n" + 
                  "                    B.nombre_completo usuario_nombre_completo,\n" + 
                  "                    B.fecha_creacion usuario_fecha_creacion,\n" + 
                  "                    B.fecha_actualizacion usuario_fecha_actualizacion,\n" + 
                  "                    A.area_id,\n" + 
                  "                    C.nombre area_nombre,\n" + 
                  "                    C.division area_division,\n" + 
                  "                    C.fecha_creacion area_fecha_creacion,\n" + 
                  "                    C.fecha_actualizacion area_fecha_actualizacion,\n" + 
                  "                    D.codigo posicion_codigo,\n" + 
                  "                    D.nombre posicion_nombre,\n" + 
                  "                    D.fecha_creacion posicion_fecha_creacion,\n" + 
                  "                    D.fecha_actualizacion posicion_fecha_actualizacion\n" + 
                  "               FROM posicion_datos A\n" + 
                  "               JOIN usuarios B ON A.usuario_codigo=B.codigo\n" + 
                  "               JOIN areas C ON A.area_id=C.id\n" + 
                  "               JOIN posiciones D ON A.posicion_codigo=D.codigo\n" +
                  "              WHERE proceso_id=:procesoId\n" + 
                  "                AND responsable_posicion_codigo=:posicionCodigo) USR ON USR.USUARIO_CODIGO = EG.USUARIO_CODIGO\n" + 
                  "             ORDER BY usuario_codigo";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("procesoId", procesoId);
        paramMap.put("posicionCodigo", posicionCodigo);
		return plantilla.query(sql, paramMap, new UsuarioMapper());
	}
	
	public List<Usuario> findUsuariosDependientesReplicar(Long procesoId, String posicionCodigo, String responsablePosicionCodigo, Long perfilId) {
		String sql = "SELECT A.usuario_codigo,\n" + 
			         "       B.nombre_completo usuario_nombre_completo,\n" +
			         "       B.fecha_creacion usuario_fecha_creacion,\n" +
			         "       B.fecha_actualizacion usuario_fecha_actualizacion,\n" +
			         "       B.fecha_eliminacion usuario_fecha_eliminacion,\n" +
			         "       A.posicion_codigo,\n" +
			         "       C.nombre posicion_nombre\n" +
			         "  FROM posicion_datos A\n" + 
			         "  JOIN usuarios B ON A.usuario_codigo=B.codigo\n" +
			         "  JOIN posiciones C ON A.posicion_codigo=C.codigo\n" + 
				     " WHERE A.proceso_id=:proceso_id\n" +
			         "   AND A.responsable_posicion_codigo=:responsable_posicion_codigo\n" +
				     "   AND A.perfil_id=:perfil_id\n" +
				     "   AND A.posicion_codigo!=:posicion_codigo";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("proceso_id", procesoId);
        paramMap.put("responsable_posicion_codigo", responsablePosicionCodigo);
        paramMap.put("perfil_id", perfilId);
        paramMap.put("posicion_codigo", posicionCodigo);
		return plantilla.query(sql, paramMap, new UsuarioMapper());
	}
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM usuarios WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Usuario> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT B.codigo usuario_codigo,\n" +
				     "       B.usuario_vida,\n" +
				     "       B.usuario_generales,\n" +
				 	 "       B.contrasenha usuario_contrasenha,\n" + 
				 	 "       B.nombre_completo usuario_nombre_completo,\n" + 
				 	 "       B.fecha_creacion usuario_fecha_creacion,\n" + 
				 	 "       B.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				 	 "       B.fecha_eliminacion usuario_fecha_eliminacion,\n" +
				 	 "       A.area_id,\n" + 
				 	 "       C.nombre area_nombre,\n" +
				 	 "       C.division area_division,\n" +
				 	 "       C.fecha_creacion area_fecha_creacion,\n" + 
				 	 "       C.fecha_actualizacion area_fecha_actualizacion,\n" +
				 	 "       D.codigo posicion_codigo,\n" + 
				 	 "       D.nombre posicion_nombre,\n" + 
				 	 "       D.fecha_creacion posicion_fecha_creacion,\n" + 
				 	 "       D.fecha_actualizacion posicion_fecha_actualizacion\n" +
				 	 "  FROM usuarios B\n" + 
				 	 "  LEFT JOIN posicion_datos A ON A.usuario_codigo=B.codigo AND A.proceso_id=0\n" + 
				 	 "  LEFT JOIN areas C ON A.area_id=C.id\n" +
				 	 "  LEFT JOIN posiciones D ON A.posicion_codigo=D.codigo\n" +
				 	 " WHERE B.codigo!='admin.encuestas'\n" +
				 	 " ORDER BY B.nombre_completo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return plantilla.query(sql, paramMap, new UsuarioMapper());
	}

	public void deleteAll() {
		plantilla.update("DELETE FROM usuarios WHERE codigo!='admin.encuestas'", (MapSqlParameterSource) null);
	}
	
	public Usuario findByUsuarioGenerales(String usuarioRed) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
				     "       A.usuario_vida,\n" +
				     "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE upper(A.usuario_generales)=:usuario_red";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_red", usuarioRed.toUpperCase()), new UsuarioMapper());
	}
	
	public Usuario findByUsuarioVida(String usuarioRed) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
			         "       A.usuario_vida,\n" +
			         "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE upper(A.usuario_vida)=:usuario_red";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_red", usuarioRed.toUpperCase()), new UsuarioMapper());
	}
	
	public Usuario findByCodigo(String usuarioCodigo) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
			         "       A.usuario_vida,\n" +
			         "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion\n" +
					 "  FROM usuarios A\n" +
				     " WHERE A.codigo=:usuario_codigo";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("usuario_codigo", usuarioCodigo), new UsuarioMapper());
	}
	
	public Usuario findByCodigoAndProceso(String usuarioCodigo, Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
			         "       A.usuario_vida,\n" +
			         "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion,\n" +
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
					 "   AND B.proceso_id=:proceso_id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("usuario_codigo", usuarioCodigo);
        paramMap.put("proceso_id", procesoId);
		return plantilla.queryForObject(sql, paramMap, new UsuarioMapper());
	}
	
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
			         "       A.usuario_vida,\n" +
			         "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion,\n" +				     
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
					 "   AND B.posicion_codigo=:posicionCodigo";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("procesoId", procesoId);
        paramMap.put("posicionCodigo", posicionCodigo);
		return plantilla.queryForObject(sql,paramMap,new UsuarioMapper());
	}
	
	public Usuario findByCodigoWithPosicion(String codigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo usuario_codigo,\n" +
			         "       A.usuario_vida,\n" +
			         "       A.usuario_generales,\n" +
				     "       A.contrasenha usuario_contrasenha,\n" +
				     "       A.nombre_completo usuario_nombre_completo,\n" +
				     "       A.fecha_creacion usuario_fecha_creacion,\n" +
				     "       A.fecha_actualizacion usuario_fecha_actualizacion,\n" +
				     "       A.fecha_eliminacion usuario_fecha_eliminacion,\n" +
					 "       B.posicion_codigo,\n" + 
					 "       C.nombre posicion_nombre,\n" + 
					 "       C.fecha_creacion posicion_fecha_creacion\n" +
					 "  FROM usuarios A\n" + 
					 "  JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" + 
					 "  JOIN posiciones C ON B.posicion_codigo=C.codigo\n" + 
					 " WHERE A.codigo=:codigo";		
		return plantilla.queryForObject(sql, new MapSqlParameterSource("codigo", codigo), new UsuarioMapper());
	}

	public Usuario insert(Usuario usuario) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO usuarios(codigo,contrasenha,usuario_vida,usuario_generales,nombre_completo,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:contrasenha,:usuario_vida,:usuario_generales,:nombre_completo,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", usuario.getCodigo());
		paramMap.put("contrasenha", "$2a$10$TrHYVE2HDH7XIi9CaGKbde2EI2aZAlRRTfQpyXOsT3u8l7GXN2qnq");
		paramMap.put("usuario_vida", usuario.getUsuarioVida());
		paramMap.put("usuario_generales", usuario.getUsuarioGenerales());
		paramMap.put("nombre_completo", usuario.getNombreCompleto());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql, paramMap);
		
		usuario.setFechaCreacion(fecha);
		usuario.setFechaActualizacion(fecha);
		return usuario;
	}
	
	public Usuario update(Usuario usuario) throws EmptyResultDataAccessException {
		String sql = "UPDATE usuarios\n" +
					 "   SET usuario_vida=:usuario_vida,\n" +
					 "       usuario_generales=:usuario_generales,\n" +
					 "		 nombre_completo=:nombre_completo,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
                     " WHERE codigo=:codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", usuario.getCodigo());
		paramMap.put("usuario_vida", usuario.getUsuarioVida());
		paramMap.put("usuario_generales", usuario.getUsuarioGenerales());
		paramMap.put("nombre_completo", usuario.getNombreCompleto());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql, paramMap);
		
		usuario.setFechaActualizacion(fecha);
		return usuario;
	}
	
	public void delete(Usuario usuario) {
		String sql = "DELETE FROM usuarios WHERE codigo=:codigo AND codigo!='admin.encuestas'";
		plantilla.update(sql, new MapSqlParameterSource("codigo", usuario.getCodigo()));
	}
	
	public void deleteByCodigo(String codigo) {
		String sql = "DELETE FROM usuarios WHERE codigo=:codigo";
		plantilla.update(sql, new MapSqlParameterSource("codigo", codigo));
	}
	
	public Usuario softDelete(Usuario centro) throws EmptyResultDataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", centro.getCodigo());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		paramMap.put("fecha_eliminacion", fecha);
		
		String sql = "UPDATE usuarios\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE codigo=:codigo";
		plantilla.update(sql, paramMap);
		
		centro.setFechaActualizacion(fecha);
		centro.setFechaEliminacion(fecha);
		return centro;
	}
	
	public Usuario softUndelete(Usuario centro) throws EmptyResultDataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", centro.getCodigo());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		
		String sql = "UPDATE usuarios\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE codigo=:codigo";
		plantilla.update(sql, paramMap);
		
		centro.setFechaActualizacion(fecha);
		centro.setFechaEliminacion(null);
		return centro;
	}
	
	public List<Rol> findRolesByCodigo(String codigo) {
		String sql = "SELECT B.*\n" +
					 "  FROM rol_usuario A\n" +
					 "  JOIN roles B\n" +
					 "    ON A.rol_id=B.id\n" + 
					 " WHERE A.usuario_codigo=:codigo";
		return plantilla.query(sql, new MapSqlParameterSource("codigo", codigo), new RolMapper());
	}
	
	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL matricula,\n" + 
				 	 "       NULL usuario_vida,\n" + 
				 	 "       NULL usuario_generales,\n" + 
				 	 "       NULL nombre_completo,\n" +
				 	 "       NULL rol,\n" +
				 	 "       NULL fecha_creacion,\n" +
				 	 "       NULL fecha_actualizacion\n" +
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo matricula,\n" + 
					 "       A.usuario_vida,\n" + 
					 "       A.usuario_generales,\n" + 
					 "       A.nombre_completo,\n" +
					 "       CASE WHEN (B.rol_id=1) THEN 'ADMINISTRADOR'\n" +
					 "            WHEN (B.rol_id=2) THEN 'USUARIO'\n" +
					 "            ELSE 'NO ENCONTRADO'\n" +
					 "       END ROL,\n" +
					 "       A.fecha_creacion,\n" +
					 "       A.fecha_actualizacion\n" +
					 "  FROM usuarios A\n" +
					 "  LEFT JOIN (SELECT usuario_codigo, min(rol_id) rol_id FROM rol_usuario GROUP BY usuario_codigo) B\n" +
					 "    ON B.usuario_codigo=A.codigo\n" + 
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 " ORDER BY A.fecha_creacion";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
}