package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Area;

public class AreaMapper implements RowMapper<Area> {
	@Override
	public Area mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Area area = new Area();
		area.setId(rs.getLong("area_id"));
		area.setNombre(rs.getString("area_nombre"));
		area.setDivision(rs.getString("area_division"));
		area.setFechaCreacion(rs.getDate("area_fecha_creacion"));
		area.setFechaActualizacion(rs.getDate("area_fecha_actualizacion"));
		return area;
	}
}