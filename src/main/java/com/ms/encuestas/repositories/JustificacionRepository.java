package com.ms.encuestas.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Justificacion;

@Repository
public class JustificacionRepository {
	private Logger logger = LoggerFactory.getLogger(JustificacionRepository.class);
	
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM justificaciones WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Justificacion> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM justificaciones\n" +
				     " WHERE fecha_eliminacion IS NULL";
		return plantilla.query(sql, new JustificacionMapper());
	}

	public Justificacion findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM justificaciones\n" +
				     " WHERE id=:id\n" +
				     "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("id", id),
        		new JustificacionMapper());
	}
}
