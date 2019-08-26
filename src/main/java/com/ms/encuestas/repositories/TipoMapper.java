package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Tipo;

public class TipoMapper implements RowMapper<Tipo> {
	@Override
	public Tipo mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Tipo tipo = new Tipo();
		tipo.setId(rs.getLong("id"));
		tipo.setNombre(rs.getString("nombre"));
		tipo.setFechaCreacion(rs.getDate("fecha_creacion"));
		tipo.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		return tipo;
	}
}