package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Area;

public class AreaMapper implements RowMapper<Area> {
	@Override
	public Area mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Area area = new Area();
		area.setId(rs.getLong("id"));
		area.setCodigo(rs.getString("codigo"));
		area.setNombre(rs.getString("nombre"));
		area.setDivision(rs.getString("division"));
		area.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
		area.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
		try {
			Timestamp fechaEliminacion = rs.getTimestamp("fecha_eliminacion");
			if (fechaEliminacion != null)
				area.setFechaEliminacion(fechaEliminacion.toLocalDateTime());
		} catch (java.sql.SQLException e1) {
		}
		area.setAgrupador("TODAS");
		return area;
	}
}