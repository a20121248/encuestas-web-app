package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Centro;

@Repository
public class UsuarioRepository {
	@Autowired
	private JdbcTemplate plantilla;

	public List<Centro> findAll() {
		String queryStr = String.format("SELECT * FROM centros");
	    return plantilla.query(queryStr, new CentroMapper());
	}

	public Centro findById(Long id) {
		String queryStr = String.format("" +
	            "SELECT *\n" +
	            "  FROM centros\n" +
	            " WHERE id=%s\n",
	            id);
		return plantilla.queryForObject(queryStr, new CentroMapper());
	}

	public Centro findByCodigo(String codigo) {
		String queryStr = String.format("" +
	            "SELECT *\n" +
	            "  FROM centros\n" +
	            " WHERE codigo='%s'\n",
	            codigo);
		return plantilla.queryForObject(queryStr, new CentroMapper());
	}

	public Centro save(Centro centro) {
		return null;
	}

	public void delete(Centro centro) {
		return;
	}
}
