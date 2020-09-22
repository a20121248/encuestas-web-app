package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

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
			Date fechaEliminacion = rs.getDate("fecha_eliminacion");
			if (fechaEliminacion != null)
				objeto.setFechaEliminacion(fechaEliminacion);
		} catch (java.sql.SQLException e1) {
		}
		
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
				objetoPadre.setFechaCreacion(rs.getDate("padre_fecha_creacion"));
				objetoPadre.setFechaActualizacion(rs.getDate("padre_fecha_actualizacion"));
				Date padreFechaEliminacion = rs.getDate("padre_fecha_eliminacion");
				if (padreFechaEliminacion != null)
					objetoPadre.setFechaEliminacion(padreFechaEliminacion);
				objeto.setObjetoPadre(objetoPadre);
			}
		} catch (java.sql.SQLException e) { }		
		
		return objeto;
	}
}
