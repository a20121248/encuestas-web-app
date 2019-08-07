package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Usuario;

@Repository
public class UsuarioRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String posicionCodigo) {
		String sql = "SELECT A.usuario_codigo,\n" +
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
					 "  FROM posicion_datos A\n" + 
					 "  JOIN usuarios B ON A.usuario_codigo=B.codigo\n" + 
					 "  JOIN areas C ON A.area_id=C.id\n" +
					 "  JOIN posiciones D ON A.posicion_codigo=D.codigo\n" +
					 " WHERE proceso_id=:procesoId\n" + 
					 "   AND responsable_posicion_codigo=:posicionCodigo";
		//System.out.println(sql);
		//System.out.println(procesoId);
		//System.out.println(posicionCodigo);
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

	public Usuario findByCodigo(String codigo, Long procesoId) throws EmptyResultDataAccessException {
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
				     "       E.fecha_creacion centro_fecha_creacion,\n" +
				     "       E.fecha_actualizacion centro_fecha_actualizacion,\n" +
				     "       E.centro_tipo_id,\n" +
				     "       F.nombre centro_tipo_nombre,\n" +
				     "       F.fecha_creacion centro_tipo_fec_creacion,\n" +
				     "       F.fecha_actualizacion centro_tipo_fec_actualizacion,\n" +
				     "       E.centro_grupo_id,\n" +
				     "       G.nombre centro_grupo_nombre,\n" +
				     "       G.fecha_creacion centro_grupo_fec_creacion,\n" +
				     "       G.fecha_actualizacion centro_grupo_fec_actualizacion\n" +
					 "  FROM usuarios A\n" +
					 "  LEFT JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" +
					 "  LEFT JOIN posiciones C ON B.posicion_codigo=C.codigo\n" +
					 "  LEFT JOIN areas D ON B.area_id=D.id\n" +
					 "  LEFT JOIN centros E ON B.centro_id=E.id\n" +
					 "  LEFT JOIN centro_tipos F ON E.centro_tipo_id=F.id\n" +
					 "  LEFT JOIN centro_grupos G ON E.centro_grupo_id=G.id\n" +
				     " WHERE A.codigo=:codigo\n" +
					 "   AND B.proceso_id=:proceso_id" +
					 "   AND A.fecha_eliminacion IS NULL";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("codigo", codigo);
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
				     "       E.fecha_creacion centro_fecha_creacion,\n" +
				     "       E.fecha_actualizacion centro_fecha_actualizacion,\n" +
				     "       E.centro_tipo_id,\n" +
				     "       F.nombre centro_tipo_nombre,\n" +
				     "       F.fecha_creacion centro_tipo_fec_creacion,\n" +
				     "       F.fecha_actualizacion centro_tipo_fec_actualizacion,\n" +
				     "       E.centro_grupo_id,\n" +
				     "       G.nombre centro_grupo_nombre,\n" +
				     "       G.fecha_creacion centro_grupo_fec_creacion,\n" +
				     "       G.fecha_actualizacion centro_grupo_fec_actualizacion,\n" +
				     "       H.id perfil_id,\n" +
				     "       H.nombre perfil_nombre,\n" +
				     "       H.descripcion perfil_descripcion,\n" +
				     "       I.id perfil_tipo_id,\n" +
				     "       I.nombre perfil_tipo_nombre\n" +
					 "  FROM usuarios A\n" +
					 "  LEFT JOIN posicion_datos B ON A.codigo=B.usuario_codigo\n" +
					 "  LEFT JOIN posiciones C ON B.posicion_codigo=C.codigo\n" +
					 "  LEFT JOIN areas D ON B.area_id=D.id\n" +
					 "  LEFT JOIN centros E ON B.centro_id=E.id\n" +
					 "  LEFT JOIN centro_tipos F ON E.centro_tipo_id=F.id\n" +
					 "  LEFT JOIN centro_grupos G ON E.centro_grupo_id=G.id\n" +
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
}