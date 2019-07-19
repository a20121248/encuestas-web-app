package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Division;

@Repository
public class DivisionRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM divisiones WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Division> findAll() {
		String sql = "SELECT *\n" +
				     "  FROM divisiones\n" +
				     " WHERE fecha_eliminacion IS NULL";
		return plantilla.query(sql, new DivisionMapper());
	}

	public Division findById(Long id) {
		String sql = "SELECT *\n" +
				     "  FROM divisiones\n" +
				     " WHERE id=:id\n" +
				     "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("id", id),
        		new DivisionMapper());
	}
}
