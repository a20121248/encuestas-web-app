package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Empresa;

public class EncuestaMapper implements RowMapper<Empresa> {
	@Override
	public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs.getLong("id"));
		empresa.setNombre(rs.getString("nombre"));
		return empresa;
	}
}
