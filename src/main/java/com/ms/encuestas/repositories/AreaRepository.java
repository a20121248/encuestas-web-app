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

import com.ms.encuestas.models.Area;

@Repository
public class AreaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM areas WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM areas";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}
	
	public List<Area> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
					 "  FROM areas\n" +
					 " WHERE fecha_eliminacion IS NULL\n" +
					 " ORDER BY codigo";
		return plantilla.query(sql, new AreaMapper());
	}

	public Area findById(Long id) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public Area findByCodigo(String codigo) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE codigo=:codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", codigo);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public Area insert(Area area) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO areas(codigo,nombre,division,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:division,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", area.getCodigo());
		paramMap.put("nombre", area.getNombre());
		paramMap.put("division", area.getDivision());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql,paramMap);
		
		sql = "SELECT areas_seq.currval FROM DUAL";
		area.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		area.setFechaCreacion(fecha);
		area.setFechaActualizacion(fecha);
		return area;
	}
	
	public Area update(Area area) throws EmptyResultDataAccessException {
		String sql = "UPDATE areas\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "		 division=:division,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
                     " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", area.getId());
		paramMap.put("codigo", area.getCodigo());
		paramMap.put("nombre", area.getNombre());
		paramMap.put("division", area.getDivision());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql, paramMap);
		
		area.setFechaActualizacion(fecha);
		return area;
	}

	public void deleteById(Long id) {
		String sql = "DELETE FROM areas WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
	
	public void delete(Area area) {
		String sql = "DELETE FROM areas WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", area.getId()));
	}
	
	public void deleteAll() {
		String sql = "DELETE FROM areas";
		plantilla.update(sql, (MapSqlParameterSource) null);
	}
	
	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL codigo,\n" + 
				 	 "       NULL nombre,\n" + 
				 	 "       NULL division,\n" + 
				 	 "       NULL fecha_creacion,\n" +
				 	 "       NULL fecha_actualizacion\n" +
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo,\n" + 
					 "       nombre,\n" + 
					 "       division,\n" +  
					 "       fecha_creacion,\n" +
					 "       fecha_actualizacion\n" +
					 "  FROM areas\n" + 
					 " WHERE fecha_eliminacion IS NULL\n" + 
					 " ORDER BY fecha_creacion";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
}
