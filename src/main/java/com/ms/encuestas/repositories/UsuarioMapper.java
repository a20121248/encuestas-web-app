package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.models.Usuario;

public class UsuarioMapper implements RowMapper<Usuario> {
	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Perfil perfil;
		try {
			perfil = new Perfil();
			perfil.setId(rs.getLong("perfil_id"));
			perfil.setNombre(rs.getString("perfil_nombre"));
			
			Tipo perfilTipo = new Tipo();
			perfilTipo.setId(rs.getLong("perfil_tipo_id"));
			perfilTipo.setNombre(rs.getString("perfil_tipo_nombre"));
			
			perfil.setTipo(perfilTipo);
			
		} catch (java.sql.SQLException e) {
			perfil = null;
		}
		
		Posicion posicion;
		try {
			posicion = new Posicion();
			posicion.setCodigo(rs.getString("posicion_codigo"));
			posicion.setNombre(rs.getString("posicion_nombre"));
			//posicion.setFechaCreacion(rs.getDate("posicion_fecha_creacion"));
			//posicion.setFechaActualizacion(rs.getDate("posicion_fecha_actualizacion"));
			posicion.setPerfil(perfil);
		} catch (java.sql.SQLException e) {
			posicion = null;
		}

		Area area;
		try {
			area = new Area();
			area.setId(rs.getLong("area_id"));
			area.setNombre(rs.getString("area_nombre"));
			area.setDivision(rs.getString("area_division"));
			//area.setFechaCreacion(rs.getTimestamp("area_fecha_creacion").toLocalDateTime());
			//area.setFechaActualizacion(rs.getTimestamp("area_fecha_actualizacion").toLocalDateTime());
			if (posicion != null)
				posicion.setArea(area);
		} catch (java.sql.SQLException e) {
			area = null;
		}
		
		Centro centro;
		try {
			Tipo centroTipo = new Tipo();
			centroTipo.setId(rs.getLong("centro_tipo_id"));
			centroTipo.setNombre(rs.getString("centro_tipo_nombre"));
			centroTipo.setFechaCreacion(rs.getDate("centro_tipo_fec_creacion"));
			centroTipo.setFechaActualizacion(rs.getDate("centro_tipo_fec_actualizacion"));
			
			centro = new Centro();
			centro.setId(rs.getLong("centro_id"));
			centro.setCodigo(rs.getString("centro_codigo"));
			centro.setNombre(rs.getString("centro_nombre"));
			centro.setNivel(rs.getInt("centro_nivel"));
			centro.setTipo(centroTipo);
			centro.setGrupo(rs.getString("centro_grupo"));
			centro.setFechaCreacion(rs.getDate("centro_fecha_creacion"));
			centro.setFechaActualizacion(rs.getDate("centro_fecha_actualizacion"));
			if (posicion != null)
				posicion.setCentro(centro);
		} catch (java.sql.SQLException e) {
			centro = null;
		}

		Usuario usuario;
		try {
			usuario = new Usuario();
			usuario.setCodigo(rs.getString("usuario_codigo"));
			try {
				usuario.setUsuarioVida(rs.getString("usuario_vida"));
			} catch (java.sql.SQLException e1) {			
			}
			try {
				usuario.setUsuarioGenerales(rs.getString("usuario_generales"));
			} catch (java.sql.SQLException e1) {			
			}
			try {
				usuario.setContrasenha(rs.getString("usuario_contrasenha"));
			} catch (java.sql.SQLException e1) {			
			}
			usuario.setNombreCompleto(rs.getString("usuario_nombre_completo"));
			usuario.setFechaCreacion(rs.getDate("usuario_fecha_creacion"));
			usuario.setFechaActualizacion(rs.getDate("usuario_fecha_actualizacion"));
			try {
				Date usuarioFechaEliminacion = rs.getDate("usuario_fecha_eliminacion");
				if (usuarioFechaEliminacion != null)
					usuario.setFechaEliminacion(usuarioFechaEliminacion);
			} catch (java.sql.SQLException e1) {			
			}
			usuario.setPosicion(posicion);
		} catch (java.sql.SQLException e) {
			usuario = null;
		}
		
		try {
			if(rs.getBoolean("estado") == true)
				usuario.setEstado(false);
			else
				usuario.setEstado(true);
		} catch (java.sql.SQLException e) {
			usuario.setEstado(false);
		}
		
		return usuario;
	}
}
