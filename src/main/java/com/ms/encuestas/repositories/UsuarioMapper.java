package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Usuario;

public class UsuarioMapper implements RowMapper<Usuario> {
	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Posicion posicion;
		try {
			posicion = new Posicion();
			posicion.setCodigo(rs.getString("posicion_codigo"));
			posicion.setNombre(rs.getString("posicion_nombre"));
			posicion.setFechaCreacion(rs.getDate("posicion_fecha_creacion"));
		} catch (java.sql.SQLException e) {
			posicion = null;
		}
		
		Usuario usuario;
		try {
			usuario = new Usuario();
			usuario.setCodigo(rs.getString("codigo"));
			usuario.setContrasenha(rs.getString("contrasenha"));
			usuario.setNombre(rs.getString("nombre"));
			usuario.setNombreCompleto(rs.getString("nombre_completo"));
			usuario.setFechaCreacion(rs.getDate("fecha_creacion"));
			usuario.setPosicion(posicion);
		} catch (java.sql.SQLException e) {
			usuario = null;
		}		

		return usuario;
	}
}
