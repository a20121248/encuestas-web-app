package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Posicion;

public class PosicionMapper implements RowMapper<Posicion> {
	private Logger logger = LoggerFactory.getLogger(PosicionMapper.class);
	@Override
	public Posicion mapRow(ResultSet rs, int rowNum) throws SQLException {
		Posicion posicion = new Posicion();
		
		Area area = new Area();
		try {
			area.setId(rs.getLong("area_id"));
			area.setNombre(rs.getString("area_nombre"));
			area.setFechaCreacion(rs.getDate("area_fecha_creacion"));
			area.setFechaActualizacion(rs.getDate("area_fecha_actualizacion"));
			area.setDivision(rs.getString("area_division"));
			posicion.setArea(area);
		} catch (java.sql.SQLException e) {
			area = null;
		}
		
		Centro centro = new Centro();
		try {
			centro.setNivel(rs.getInt("centro_nivel"));
			centro.setId(rs.getLong("centro_id"));
			centro.setCodigo(rs.getString("centro_codigo"));
			centro.setNombre(rs.getString("centro_nombre"));
			centro.setNivel(rs.getInt("centro_nivel"));
			centro.setFechaCreacion(rs.getDate("centro_fecha_creacion"));
			posicion.setCentro(centro);
		} catch (java.sql.SQLException e) {
			centro = null;
		}
		
		posicion.setCodigo(rs.getString("codigo"));
		posicion.setNombre(rs.getString("nombre"));
		posicion.setFechaCreacion(rs.getDate("fecha_creacion"));
		posicion.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		
		if (posicion.getCodigo() == null)
			return null;
		return posicion;
	}
}
