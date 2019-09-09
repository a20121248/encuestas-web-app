package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Empresa;

@CrossOrigin(origins={})
@Repository
public class EmpresaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM empresas WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Empresa> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM empresas WHERE fecha_eliminacion IS NULL ORDER BY ID";
		return plantilla.query(sql, new EmpresaMapper());
	}

	public Empresa findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM empresas WHERE id=:id";
        return plantilla.queryForObject(sql, new MapSqlParameterSource("id", id), new EmpresaMapper());
	}
}
