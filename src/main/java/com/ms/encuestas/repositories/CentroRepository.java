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

import com.ms.encuestas.models.Centro;

@Repository
public class CentroRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count(Long empresaId) {
		String sql = "SELECT COUNT(1) FROM centros WHERE fecha_eliminacion IS NULL AND empresa_id=:empresa_id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("empresa_id", empresaId), Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM centros";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}

	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL codigo,\n" + 
				 	 "       NULL nombre,\n" + 
				 	 "       NULL tipo,\n" + 
				 	 "       NULL nivel,\n" + 
				 	 "       NULL grupo,\n" + 
				 	 "       NULL fecha_creacion,\n" + 
				 	 "       NULL fecha_actualizacion\n" + 
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo,\n" + 
					 "       A.nombre,\n" + 
					 "       B.nombre tipo,\n" + 
					 "       A.nivel,\n" + 
					 "       A.grupo,\n" + 
					 "       A.fecha_creacion,\n" + 
					 "       A.fecha_actualizacion\n" + 
					 "  FROM centros A\n" + 
					 "  JOIN centro_tipos B ON A.centro_tipo_id=B.id\n" + 
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 "   AND A.empresa_id=1\n" + 
					 " ORDER BY A.codigo";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
	
	public List<Centro> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT A.id centro_id,\n" + 
					 "       A.codigo centro_codigo,\n" + 
					 "       A.nombre centro_nombre,\n" + 
					 "       A.nivel centro_nivel,\n" + 
					 "       B.id centro_tipo_id,\n" + 
					 "       B.nombre centro_tipo_nombre,\n" +
					 "       B.fecha_creacion centro_tipo_fecha_creacion,\n" +
					 "       B.fecha_actualizacion centro_tipo_fecha_actualizacion,\n" +
					 "       A.grupo centro_grupo,\n" + 
				 	 "       A.fecha_creacion centro_fecha_creacion,\n" +
				 	 "       A.fecha_actualizacion centro_fecha_actualizacion,\n" +
				 	 "       A.fecha_eliminacion centro_fecha_eliminacion\n" + 
					 "  FROM centros A\n" + 
					 "  JOIN centro_tipos B\n" + 
					 "    ON A.centro_tipo_id=B.id\n" + 
					 " WHERE A.empresa_id=1\n" + 
					 " ORDER BY A.codigo";
	    return plantilla.query(sql, new CentroMapper());
	}

	public Centro findById(Long centroId) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT A.id centro_id,\n" + 
				 	 "       A.codigo centro_codigo,\n" + 
				 	 "       A.nombre centro_nombre,\n" + 
				 	 "       A.nivel centro_nivel,\n" + 
				 	 "       B.id centro_tipo_id,\n" + 
				 	 "       B.nombre centro_tipo_nombre,\n" +
				 	 "       B.fecha_creacion centro_tipo_fecha_creacion,\n" +
				 	 "       B.fecha_actualizacion centro_tipo_fecha_actualizacion,\n" +
				 	 "       A.grupo centro_grupo,\n" + 
				 	 "       A.fecha_creacion centro_fecha_creacion,\n" +
				 	 "       A.fecha_actualizacion centro_fecha_actualizacion,\n" +
				 	 "       A.fecha_eliminacion centro_fecha_eliminacion\n" +
				 	 "  FROM centros A\n" + 
				 	 "  JOIN centro_tipos B\n" + 
				 	 "    ON A.centro_tipo_id=B.id\n" + 
				 	 " WHERE A.id=:centro_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("centro_id", centroId);
        return plantilla.queryForObject(sql, paramMap, new CentroMapper());
	}
	
	public Centro findByCodigo(String codigo) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT A.id centro_id,\n" + 
				 	 "       A.codigo centro_codigo,\n" + 
				 	 "       A.nombre centro_nombre,\n" + 
				 	 "       A.nivel centro_nivel,\n" + 
				 	 "       B.id centro_tipo_id,\n" + 
				 	 "       B.nombre centro_tipo_nombre,\n" +
				 	 "       B.fecha_creacion centro_tipo_fecha_creacion,\n" +
				 	 "       B.fecha_actualizacion centro_tipo_fecha_actualizacion,\n" +
				 	 "       A.grupo centro_grupo,\n" + 
				 	 "       A.fecha_creacion centro_fecha_creacion,\n" +
				 	 "       A.fecha_actualizacion centro_fecha_actualizacion,\n" +
				 	 "       A.fecha_eliminacion centro_fecha_eliminacion\n" + 
				 	 "  FROM centros A\n" + 
				 	 "  JOIN centro_tipos B\n" + 
				 	 "    ON A.centro_tipo_id=B.id\n" + 
				 	 " WHERE A.codigo=:codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", codigo);
        return plantilla.queryForObject(sql, paramMap, new CentroMapper());
	}

	public Centro insert(Centro centro, Long empresaId) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO centros(codigo,nombre,nivel,centro_tipo_id,grupo,empresa_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:nivel,:centro_tipo_id,:grupo,:empresa_id,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", centro.getCodigo());
		paramMap.put("nombre", centro.getNombre());
		paramMap.put("nivel", centro.getNivel());
		paramMap.put("centro_tipo_id", centro.getTipo().getId());
		paramMap.put("grupo", centro.getGrupo());
		paramMap.put("empresa_id", empresaId);
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);        
		plantilla.update(sql,paramMap);
		
		sql = "SELECT centros_seq.currval FROM DUAL";
		centro.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		centro.setFechaCreacion(fecha);
		centro.setFechaActualizacion(fecha);
		return centro;
	}
	
	public Centro update(Centro centro, Long empresaId) throws EmptyResultDataAccessException {
		String sql = "UPDATE centros\n" +
				 	 "   SET codigo=:codigo,\n" +
				 	 "       nombre=:nombre,\n" +
				 	 "		 nivel=:nivel,\n" +
				 	 "		 centro_tipo_id=:centro_tipo_id,\n" +
				 	 "		 grupo=:grupo,\n" +
				 	 "		 empresa_id=:empresa_id,\n" +
				 	 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				 	 " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", centro.getId());
		paramMap.put("codigo", centro.getCodigo());
		paramMap.put("nombre", centro.getNombre());
		paramMap.put("nivel", centro.getNivel());
		paramMap.put("centro_tipo_id", centro.getTipo().getId());
		paramMap.put("grupo", centro.getGrupo());
		paramMap.put("empresa_id", empresaId);
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql, paramMap);
		
		centro.setFechaActualizacion(fecha);
		return centro;
	}

	public void delete(Centro centro) {
		String sql = "DELETE FROM centros WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", centro.getId()));
	}
	
	public void deleteById(Long id) {
		String sql = "DELETE FROM centros WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
	
	public Centro softDelete(Centro centro) throws EmptyResultDataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", centro.getId());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		paramMap.put("fecha_eliminacion", fecha);
		
		String sql = "UPDATE centros\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		centro.setFechaActualizacion(fecha);
		centro.setFechaEliminacion(fecha);
		return centro;
	}
	
	public Centro softUndelete(Centro centro) throws EmptyResultDataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", centro.getId());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		
		String sql = "UPDATE centros\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		centro.setFechaActualizacion(fecha);
		centro.setFechaEliminacion(null);
		return centro;
	}
	
	public void deleteAll(Long empresaId) {
		String sql = "DELETE FROM centros WHERE empresa_id=:empresa_id";
		plantilla.update(sql, new MapSqlParameterSource("id", empresaId));
	}
}
