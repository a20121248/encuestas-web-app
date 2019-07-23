package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Empresa;

public class EmpresaMapper implements RowMapper<Empresa> {
	@Override
	public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Empresa empresa = new Empresa();
		empresa.setId(rs.getLong("id"));
		empresa.setNombre(rs.getString("nombre"));
		empresa.setFechaCreacion(rs.getDate("fecha_creacion"));
		empresa.setPorcentaje(porcentaje);
		return empresa;
	}
}
