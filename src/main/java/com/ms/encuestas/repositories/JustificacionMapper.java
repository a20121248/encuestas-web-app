package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Justificacion;

public class JustificacionMapper implements RowMapper<Justificacion> {
	private Logger logger = LoggerFactory.getLogger(JustificacionMapper.class);
	
	@Override
	public Justificacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		Justificacion justificacion = new Justificacion();
		justificacion.setId(rs.getLong("id"));
		justificacion.setNombre(rs.getString("nombre"));
		justificacion.setFechaCreacion(rs.getDate("fecha_creacion"));
		justificacion.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		return justificacion;
	}
}
