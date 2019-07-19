package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Division;

public class DivisionMapper implements RowMapper<Division> {
	@Override
	public Division mapRow(ResultSet rs, int rowNum) throws SQLException {
		Division division = new Division();
		division.setId(rs.getLong("id"));
		division.setNombre(rs.getString("nombre"));
		division.setFechaCreacion(rs.getDate("fecha_creacion"));
		return division;
	}
}
