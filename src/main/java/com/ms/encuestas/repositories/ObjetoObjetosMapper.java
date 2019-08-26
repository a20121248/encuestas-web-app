package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;

public class ObjetoObjetosMapper implements RowMapper<ObjetoObjetos> {
	private Logger logger = LoggerFactory.getLogger(ObjetoObjetosMapper.class);
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
		objeto.setFechaCreacion(rs.getDate("fecha_creacion"));
		objeto.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
		
		ObjetoObjetos objetoObjetos = new ObjetoObjetos();
		objetoObjetos.setObjeto(objeto);
		return objetoObjetos;
	}
}
