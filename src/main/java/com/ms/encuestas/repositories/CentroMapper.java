package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Centro;

public class CentroMapper implements RowMapper<Centro> {
	@Override
	public Centro mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Centro centro = new Centro();
		centro.setId(rs.getLong("id"));
		centro.setCodigo(rs.getString("codigo"));
		centro.setNombre(rs.getString("nombre"));
		centro.setGrupo(rs.getString("grupo"));
		centro.setNivel(rs.getInt("nivel"));
		centro.setFechaCreacion(rs.getDate("fecha_creacion"));
		centro.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		centro.setPorcentaje(porcentaje);
		return centro;
	}
}
