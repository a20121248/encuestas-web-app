package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Proceso;

@Repository
public class ProcesoRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public Proceso getCurrentProceso() throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       A.usuario_codigo,\n" +
					 "       B.nombre_completo usuario_nombre_completo\n" +
			         "  FROM procesos A\n" + 
			         "  LEFT JOIN usuarios B\n" +
			         "    ON A.usuario_codigo=B.codigo\n" + 
			         " WHERE A.fecha_eliminacion IS NULL\n" +
			         "   AND A.activo=1";
        return plantilla.queryForObject(sql, (MapSqlParameterSource) null, new ProcesoMapper());
	}
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM procesos WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Proceso> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.codigo usuario_codigo,\n" +
					 "       B.nombre_completo usuario_nombre_completo\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 " ORDER BY A.fecha_creacion";
		return plantilla.query(sql, new ProcesoMapper());
	}
	
	public Proceso findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.codigo usuario_codigo,\n" +
					 "       B.nombre_completo usuario_nombre_completo\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.id=:id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("id", id), new ProcesoMapper());
	}
	
	public Proceso findByCodigo(String codigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
				 	 "       B.codigo usuario_codigo,\n" +
				 	 "       B.nombre_completo usuario_nombre_completo\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.codigo=:codigo";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("codigo", codigo), new ProcesoMapper());
	}
	
	public Proceso insert(Proceso proceso) {
		String sql = "INSERT INTO procesos(codigo,nombre,activo,usuario_codigo,fecha_inicio,fecha_cierre,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:activo,:usuario_codigo,:fecha_inicio,:fecha_cierre,:fecha_creacion,:fecha_actualizacion)";		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", proceso.getCodigo());
		paramMap.addValue("nombre", proceso.getNombre());
		paramMap.addValue("activo", proceso.isActivo() ? 1 : 0);
		paramMap.addValue("usuario_codigo", proceso.getUsuario().getCodigo());
		paramMap.addValue("fecha_inicio", proceso.getFechaInicio());
		paramMap.addValue("fecha_cierre", proceso.getFechaCierre());
		Date fecha = new Date();
		paramMap.addValue("fecha_creacion", fecha);
		paramMap.addValue("fecha_actualizacion", fecha);        
		plantilla.update(sql, paramMap);
		
		sql = "SELECT procesos_seq.currval FROM DUAL";
		proceso.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		proceso.setFechaCreacion(fecha);
		proceso.setFechaActualizacion(fecha);
		return proceso;
	}
	
	public Proceso update(Proceso proceso) {
		String sql = "UPDATE procesos\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "       activo=:activo,\n" +
					 "       usuario_codigo=:usuario_codigo,\n" +
					 "       fecha_inicio=:fecha_inicio,\n" +
					 "       fecha_cierre=:fecha_cierre,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				     " WHERE id=:id";		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", proceso.getId());
		paramMap.addValue("codigo", proceso.getCodigo());
		paramMap.addValue("nombre", proceso.getNombre());
		paramMap.addValue("activo", proceso.isActivo() ? 1 : 0);
		paramMap.addValue("usuario_codigo", proceso.getUsuario().getCodigo());
		paramMap.addValue("fecha_inicio", proceso.getFechaInicio());
		paramMap.addValue("fecha_cierre", proceso.getFechaCierre());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql, paramMap);
		
		proceso.setFechaActualizacion(fecha);
		return proceso;
	}
	
	public void deleteById(Long id) {
		String sql = "DELETE FROM procesos WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
}
