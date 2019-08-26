package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Objeto;

public class ObjetoMapper implements RowMapper<Objeto> {
	@Override
	public Objeto mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Objeto objeto = new Objeto();
		objeto.setId(rs.getLong("id"));
		objeto.setCodigo(rs.getString("codigo"));
		objeto.setNombre(rs.getString("nombre"));
		objeto.setFechaCreacion(rs.getDate("fecha_creacion"));
		objeto.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		
		try {
			double porcentaje = rs.getDouble("porcentaje");
			objeto.setPorcentaje(porcentaje);
		} catch (java.sql.SQLException e) { }
		
		try {
			Objeto objetoPadre = new Objeto();
			objetoPadre.setId(rs.getLong("padre_id"));
			objetoPadre.setCodigo(rs.getString("padre_codigo"));
			objetoPadre.setNombre(rs.getString("padre_nombre"));
			objetoPadre.setFechaCreacion(rs.getDate("padre_fecha_creacion"));
			objetoPadre.setFechaActualizacion(rs.getDate("padre_fecha_actualizacion"));
			objeto.setObjetoPadre(objetoPadre);
		} catch (java.sql.SQLException e) { }		
		
		return objeto;
	}
}
