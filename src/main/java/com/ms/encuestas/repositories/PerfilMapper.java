package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Proceso;

public class PerfilMapper implements RowMapper<Proceso> {
	private Logger logger = LoggerFactory.getLogger(PerfilMapper.class);
	@Override
	public Proceso mapRow(ResultSet rs, int rowNum) throws SQLException {
		Proceso proceso = new Proceso();
		proceso.setId(rs.getLong("proceso_id"));
		proceso.setNombre(rs.getString("proceso_nombre"));
		proceso.setFechaCierre(rs.getDate("proceso_fecha_cierre"));
		proceso.setFechaCreacion(rs.getDate("proceso_fecha_creacion"));
		proceso.setFechaActualizacion(rs.getDate("proceso_fecha_actualizacion"));
		return proceso;
	}
}
