package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.Justificacion;

public class EncuestaCentroMapper implements RowMapper<EncuestaCentro> {	
	@Override
	public EncuestaCentro mapRow(ResultSet rs, int rowNum) throws SQLException {
		Justificacion justificacion;
		try {
			justificacion = new Justificacion();
			justificacion.setId(rs.getLong("justificacion_id"));
			justificacion.setNombre(rs.getString("justificacion_nombre"));
			justificacion.setDetalle(rs.getString("justificacion_detalle"));
			justificacion.setFechaCreacion(rs.getDate("justificacion_fecha_cre"));
			justificacion.setFechaActualizacion(rs.getDate("justificacion_fecha_act"));
		} catch (java.sql.SQLException e) {
			justificacion = null;
		}		
		
		EncuestaCentro encuesta;
		try {
			encuesta = new EncuestaCentro();
			encuesta.setObservaciones(rs.getString("observaciones"));
			encuesta.setJustificacion(justificacion);
		} catch (java.sql.SQLException e) {
			encuesta = null;
		}
		
		return encuesta;
	}
}
