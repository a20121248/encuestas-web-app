package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Centro;

public class CentroMapper implements RowMapper<Centro> {
	@Override
	public Centro mapRow(ResultSet rs, int rowNum) throws SQLException {
		Centro centro = new Centro();
		centro.setId(rs.getLong("id"));
		centro.setCodigo(rs.getString("codigo"));
		centro.setNombre(rs.getString("nombre"));
		centro.setAbreviatura(rs.getString("abreviatura"));
		centro.setNivel(rs.getInt("nivel"));
		return centro;
	}

}
