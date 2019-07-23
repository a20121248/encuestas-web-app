package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Centro;

@Repository
public class CentroRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM centros WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}

	public List<Centro> findAll() {
		String queryStr = String.format("SELECT * FROM centros WHERE fecha_eliminacion IS NULL");
	    return plantilla.query(queryStr, new CentroMapper());
	}

	public Centro findById(Long id) {
		String queryStr = "SELECT *\n" +
		                  "  FROM centros\n" +
                          " WHERE id=:id\n" +
		                  "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(queryStr,
        		new MapSqlParameterSource("id", id),
        		new CentroMapper());
	}

	public Centro save(Centro centro) {
		return null;
	}

	public void delete(Centro centro) {
		return;
	}
}
