package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Tipo;

public class PerfilMapper implements RowMapper<Perfil> {
	@Override
	public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
		Perfil perfil = new Perfil();
		perfil.setId(rs.getLong("id"));
		perfil.setCodigo(rs.getString("codigo"));
		perfil.setNombre(rs.getString("nombre"));
		
		try {
			Tipo tipo = new Tipo();
			tipo.setId(rs.getLong("tipo_id"));
			tipo.setNombre(rs.getString("tipo_nombre"));
			tipo.setFechaCreacion(rs.getTimestamp("tipo_fecha_creacion").toLocalDateTime());
			tipo.setFechaActualizacion(rs.getTimestamp("tipo_fecha_actualizacion").toLocalDateTime());			
			perfil.setTipo(tipo);
			
		} catch (java.sql.SQLException e) {
		}

		return perfil;
	}
}
