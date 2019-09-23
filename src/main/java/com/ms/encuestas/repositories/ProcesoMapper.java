package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;

public class ProcesoMapper implements RowMapper<Proceso> {
	@Override
	public Proceso mapRow(ResultSet rs, int rowNum) throws SQLException {
		Proceso proceso = new Proceso();
		proceso.setId(rs.getLong("id"));
		proceso.setCodigo(rs.getString("codigo"));
		proceso.setNombre(rs.getString("nombre"));
		proceso.setActivo(rs.getInt("activo")==1);
		Timestamp fechaInicio = rs.getTimestamp("fecha_inicio");
		if (fechaInicio != null) proceso.setFechaInicio(fechaInicio.toLocalDateTime());
		Timestamp fechaCierre = rs.getTimestamp("fecha_cierre");
		if (fechaCierre != null) proceso.setFechaCierre(fechaCierre.toLocalDateTime());
		proceso.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
		proceso.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
		
		Usuario usuario = new Usuario();
		usuario.setCodigo(rs.getString("usuario_codigo"));
		usuario.setNombreCompleto(rs.getString("usuario_nombre_completo"));
		proceso.setUsuario(usuario);
		
		return proceso;
	}
}
