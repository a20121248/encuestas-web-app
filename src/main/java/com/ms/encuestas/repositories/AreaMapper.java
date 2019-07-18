package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Division;

public class AreaMapper implements RowMapper<Area> {
	@Override
	public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
		Division division;
		try {
			division = new Division();
			division.setId(rs.getLong("division_id"));
			division.setNombre(rs.getString("division_nombre"));
			division.setFechaCreacion(rs.getDate("division_fecha_creacion"));
		} catch (java.sql.SQLException e) {
			division = null;
		}
		
		Area area = new Area();
		area.setId(rs.getLong("area_id"));
		area.setNombre(rs.getString("area_nombre"));
		area.setFechaCreacion(rs.getDate("area_fecha_creacion"));		
		area.setDivision(division);
		return area;
	}
}