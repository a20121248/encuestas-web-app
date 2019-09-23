package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Objeto;

public class ObjetoMapper implements RowMapper<Objeto> {
	@Override
	public Objeto mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Objeto objeto = new Objeto();
		objeto.setId(rs.getLong("id"));
		objeto.setCodigo(rs.getString("codigo"));
		objeto.setNombre(rs.getString("nombre"));
		objeto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
		objeto.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
		
		try {
			double porcentaje = rs.getDouble("porcentaje");
			objeto.setPorcentaje(porcentaje);
		} catch (java.sql.SQLException e) { }
		
		try {
			Long objetoPadreId = rs.getLong("padre_id");
			if (!objetoPadreId.equals(new Long(0))) {
				Objeto objetoPadre = new Objeto();
				objetoPadre.setId(objetoPadreId);
				objetoPadre.setCodigo(rs.getString("padre_codigo"));
				objetoPadre.setNombre(rs.getString("padre_nombre"));
				objetoPadre.setFechaCreacion(rs.getTimestamp("padre_fecha_creacion").toLocalDateTime());
				objetoPadre.setFechaActualizacion(rs.getTimestamp("padre_fecha_actualizacion").toLocalDateTime());
				objeto.setObjetoPadre(objetoPadre);
			}
		} catch (java.sql.SQLException e) { }		
		
		return objeto;
	}
}
