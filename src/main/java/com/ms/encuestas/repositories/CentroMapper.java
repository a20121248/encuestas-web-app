package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Tipo;

public class CentroMapper implements RowMapper<Centro> {
	@Override
	public Centro mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Centro centro = new Centro();
		centro.setId(rs.getLong("centro_id"));
		centro.setCodigo(rs.getString("centro_codigo"));
		centro.setNombre(rs.getString("centro_nombre"));
		centro.setNivel(rs.getInt("centro_nivel"));
		
		Tipo centroTipo;
		try {
			centroTipo = new Tipo();
			centroTipo.setId(rs.getLong("centro_tipo_id"));
			centroTipo.setNombre(rs.getString("centro_tipo_nombre"));
			centroTipo.setFechaCreacion(rs.getDate("centro_tipo_fec_creacion"));
			centroTipo.setFechaActualizacion(rs.getDate("centro_tipo_fec_actualizacion"));
			centro.setTipo(centroTipo);
		} catch (java.sql.SQLException e) {
			centroTipo = null;
		}

		
		centro.setGrupo(rs.getString("centro_grupo"));
		centro.setFechaCreacion(rs.getDate("centro_fecha_creacion"));
		centro.setFechaActualizacion(rs.getDate("centro_fecha_actualizacion"));
		try {
			Date fechaEliminacion = rs.getDate("centro_fecha_eliminacion");
			if (fechaEliminacion != null)
				centro.setFechaEliminacion(fechaEliminacion);
		} catch (java.sql.SQLException e1) {			
		}
		centro.setPorcentaje(porcentaje);
		centro.setAgrupador("TODOS");
		return centro;
	}
}
