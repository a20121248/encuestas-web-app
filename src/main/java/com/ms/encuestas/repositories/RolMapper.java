package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Rol;

public class RolMapper implements RowMapper<Rol> {
	@Override
	public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Rol rol = new Rol();
		rol.setId(rs.getLong("id"));
		rol.setNombre(rs.getString("nombre"));
		rol.setFechaCreacion(rs.getDate("fecha_creacion"));		
		return rol;
	}
}