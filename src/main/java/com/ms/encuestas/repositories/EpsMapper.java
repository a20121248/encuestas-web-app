package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Eps;

public class EpsMapper implements RowMapper<Eps> {
	@Override
	public Eps mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Eps eps = new Eps();
		eps.setId(rs.getLong("id"));
		eps.setNombre(rs.getString("nombre"));
		eps.setFechaCreacion(rs.getDate("fecha_creacion"));
		eps.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		eps.setPorcentaje(porcentaje);
		return eps;
	}
}
