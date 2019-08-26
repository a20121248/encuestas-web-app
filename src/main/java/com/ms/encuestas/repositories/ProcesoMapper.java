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
	private Logger logger = LoggerFactory.getLogger(ProcesoMapper.class);
	@Override
	public Proceso mapRow(ResultSet rs, int rowNum) throws SQLException {
		Proceso proceso = new Proceso();
		
		Usuario usuario;
		try {
			usuario = new Usuario();
			usuario.setCodigo(rs.getString("usuario_codigo"));
			usuario.setNombreCompleto(rs.getString("usuario_nombre_completo"));
			proceso.setUsuario(usuario);
		} catch (java.sql.SQLException e) {
			usuario = null;
		}
		
		proceso.setId(rs.getLong("proceso_id"));
		proceso.setCodigo(rs.getString("proceso_codigo"));
		proceso.setNombre(rs.getString("proceso_nombre"));
		proceso.setFechaCierre(rs.getDate("proceso_fecha_cierre"));
		proceso.setFechaCreacion(rs.getDate("proceso_fecha_creacion"));
		proceso.setFechaActualizacion(rs.getDate("proceso_fecha_actualizacion"));
		return proceso;
	}
}
