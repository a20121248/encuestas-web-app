package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;

public class ObjetoObjetosMapper implements RowMapper<ObjetoObjetos> {
	@Override
	public ObjetoObjetos mapRow(ResultSet rs, int rowNum) throws SQLException {
		double porcentaje;
		try {
			porcentaje = rs.getDouble("porcentaje");
		} catch (java.sql.SQLException e) {
			porcentaje = -1;
		}
		
		Objeto objeto = new Objeto(); 
		objeto.setId(rs.getLong("id"));
		objeto.setCodigo(rs.getString("codigo"));
		objeto.setNombre(rs.getString("nombre"));
		objeto.setPorcentaje(porcentaje);
		objeto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
		objeto.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion").toLocalDateTime());
		
		ObjetoObjetos objetoObjetos = new ObjetoObjetos();
		objetoObjetos.setObjeto(objeto);
		return objetoObjetos;
	}
}
