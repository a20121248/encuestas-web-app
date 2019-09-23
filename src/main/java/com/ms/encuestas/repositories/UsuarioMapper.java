package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

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
			//posicion.setFechaCreacion(rs.getTimestamp("posicion_fecha_creacion").toLocalDateTime());
			//posicion.setFechaActualizacion(rs.getTimestamp("posicion_fecha_actualizacion").toLocalDateTime());
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
			centroTipo.setFechaCreacion(rs.getTimestamp("centro_tipo_fec_creacion").toLocalDateTime());
			centroTipo.setFechaActualizacion(rs.getTimestamp("centro_tipo_fec_actualizacion").toLocalDateTime());
			
			centro = new Centro();
			centro.setId(rs.getLong("centro_id"));
			centro.setCodigo(rs.getString("centro_codigo"));
			centro.setNombre(rs.getString("centro_nombre"));
			centro.setNivel(rs.getInt("centro_nivel"));
			centro.setTipo(centroTipo);
			centro.setGrupo(rs.getString("centro_grupo"));
			centro.setFechaCreacion(rs.getTimestamp("centro_fecha_creacion").toLocalDateTime());
			centro.setFechaActualizacion(rs.getTimestamp("centro_fecha_actualizacion").toLocalDateTime());
			if (posicion != null)
				posicion.setCentro(centro);
		} catch (java.sql.SQLException e) {
			centro = null;
		}

		Usuario usuario;
		try {
			usuario = new Usuario();
			usuario.setCodigo(rs.getString("usuario_codigo"));
			usuario.setUsuarioVida(rs.getString("usuario_vida"));
			usuario.setUsuarioGenerales(rs.getString("usuario_generales"));
			usuario.setContrasenha(rs.getString("usuario_contrasenha"));
			usuario.setNombreCompleto(rs.getString("usuario_nombre_completo"));
			usuario.setFechaCreacion(rs.getTimestamp("usuario_fecha_creacion").toLocalDateTime());
			usuario.setFechaActualizacion(rs.getTimestamp("usuario_fecha_actualizacion").toLocalDateTime());
			usuario.setPosicion(posicion);
		} catch (java.sql.SQLException e) {
			usuario = null;
		}
		try {
			if(rs.getBoolean("estado") == true)
				usuario.setEstado(false);
			else usuario.setEstado(true);
		} catch (java.sql.SQLException e) {
			usuario.setEstado(false);
		}
		
		return usuario;
	}
}
