package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.models.Usuario;

public class ProcesoMapper implements RowMapper<Proceso> {
	@Override
	public Proceso mapRow(ResultSet rs, int rowNum) throws SQLException {
		Proceso proceso = new Proceso();
		proceso.setId(rs.getLong("id"));
		proceso.setCodigo(rs.getString("codigo"));
		proceso.setNombre(rs.getString("nombre"));
		proceso.setFechaInicio(rs.getDate("fecha_inicio"));
		proceso.setFechaCierre(rs.getDate("fecha_cierre"));
		proceso.setFechaCreacion(rs.getDate("fecha_creacion"));
		proceso.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		
		Usuario usuario = new Usuario();
		usuario.setCodigo(rs.getString("usuario_codigo"));
		usuario.setNombreCompleto(rs.getString("usuario_nombre_completo"));
		proceso.setUsuario(usuario);
		
		return proceso;
	}
}
