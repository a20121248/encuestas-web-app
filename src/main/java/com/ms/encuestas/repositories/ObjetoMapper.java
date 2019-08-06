package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Objeto;

public class ObjetoMapper implements RowMapper<Objeto> {
	@Override
	public Objeto mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Objeto objeto = new Objeto();
		objeto.setId(rs.getLong("id"));
		objeto.setCodigo(rs.getString("codigo"));
		objeto.setNombre(rs.getString("nombre"));
		objeto.setFechaCreacion(rs.getDate("fecha_creacion"));
		objeto.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		objeto.setPorcentaje(porcentaje);
		return objeto;
	}
}
