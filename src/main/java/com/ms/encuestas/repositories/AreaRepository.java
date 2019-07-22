package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Area;

@Repository
public class AreaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM areas WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Area> findAll() throws EmptyResultDataAccessException {
		String sql = "" +
				"SELECT id area_id,\n" +
				"       nombre area_nombre,\n" +
				"       fecha_creacion area_fecha_creacion\n" +
				"  FROM areas";
		return plantilla.query(sql, new AreaMapper());
	}
	
	public List<Area> findAllWithDivision() throws EmptyResultDataAccessException {
		String sql = "" +
				"SELECT A.id area_id,\n" + 
				"       A.nombre area_nombre,\n" + 
				"       A.fecha_creacion area_fecha_creacion,\n" + 
				"       B.id division_id,\n" + 
				"       B.nombre division_nombre,\n" + 
				"       B.fecha_creacion division_fecha_creacion\n" + 
				"  FROM areas A\n" + 
				"  JOIN divisiones B ON A.division_id=B.id\n" + 
				" WHERE A.fecha_eliminacion IS NULL\n" + 
				"   AND B.fecha_eliminacion IS NULL";
		return plantilla.query(sql, new AreaMapper());
	}

	public Area findById(Long id) throws EmptyResultDataAccessException {
		String queryStr = "" +
				"SELECT id area_id,\n" +
				"       nombre area_nombre,\n" +
				"       fecha_creacion area_fecha_creacion\n" +
				"  FROM areas WHERE id=:id";
        return plantilla.queryForObject(queryStr,
        		new MapSqlParameterSource("id", id),
        		new AreaMapper());
	}
	
	public Area findByIdWithDivision(Long id) throws EmptyResultDataAccessException {
		String sql = "" +
				"SELECT A.id area_id,\n" + 
				"       A.nombre area_nombre,\n" + 
				"       A.fecha_creacion area_fecha_creacion,\n" + 
				"       B.id division_id,\n" + 
				"       B.nombre division_nombre,\n" + 
				"       B.fecha_creacion division_fecha_creacion\n" + 
				"  FROM areas A\n" + 
				"  JOIN divisiones B ON A.division_id=B.id\n" + 
				" WHERE A.id=:area_id\n" +
				"   AND A.fecha_eliminacion IS NULL\n" + 
				"   AND B.fecha_eliminacion IS NULL";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("area_id", id);
		
		return plantilla.queryForObject(sql, paramMap, new AreaMapper());
	}
}
