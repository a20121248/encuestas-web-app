package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.Justificacion;

public class EncuestaMapper implements RowMapper<EncuestaEmpresa> {
	@Override
	public EncuestaEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Justificacion justificacion;
		try {
			justificacion = new Justificacion();
			justificacion.setId(rs.getLong("justificacion_id"));
			String justificacionNombre = rs.getString("justificacion_nombre");
			justificacion.setNombre(justificacionNombre=="NULL" ? null : justificacionNombre);
			String justificacionDetalle = rs.getString("justificacion_detalle");
			justificacion.setDetalle(justificacionDetalle.compareTo("NULL")==0 ? null : justificacionDetalle);
			justificacion.setFechaCreacion(rs.getDate("justificacion_fecha_cre"));
			justificacion.setFechaActualizacion(rs.getDate("justificacion_fecha_act"));
		} catch (java.sql.SQLException e) {
			justificacion = null;
		}		
		
		EncuestaEmpresa encuesta;
		try {
			encuesta = new EncuestaEmpresa();
			String observaciones = rs.getString("observaciones");
			System.out.println(observaciones.compareTo("NULL")==0);
			encuesta.setObservaciones(observaciones.compareTo("NULL")==0 ? null : observaciones);
			encuesta.setJustificacion(justificacion);
		} catch (java.sql.SQLException e) {
			encuesta = null;
		}
		
		return encuesta;
	}
}
