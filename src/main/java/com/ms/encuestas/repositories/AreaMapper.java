package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
		area.setFechaCreacion(rs.getDate("fecha_creacion"));
		area.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		try {
			Date fechaEliminacion = rs.getDate("fecha_eliminacion");
			if (fechaEliminacion != null)
				area.setFechaEliminacion(fechaEliminacion);
		} catch (java.sql.SQLException e1) {
		}
		area.setAgrupador("TODAS");
		return area;
	}
}