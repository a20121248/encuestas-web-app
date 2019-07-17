package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Area;

@Repository
public class AreaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public List<Area> findAll() {
		String queryStr = "SELECT * FROM areas";
		return plantilla.query(queryStr, new AreaMapper());
	}

	public Area findById(Long id) {
		String queryStr = "SELECT * FROM areas WHERE id=:id";
        return plantilla.queryForObject(queryStr,
        		new MapSqlParameterSource("id", id),
        		new AreaMapper());
	}
}
