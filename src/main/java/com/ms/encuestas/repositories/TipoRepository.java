package com.ms.encuestas.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Tipo;

@Repository
public class TipoRepository {
	private Logger logger = LoggerFactory.getLogger(TipoRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public List<Tipo> getCentroTypes() {
		return getTableTypes("centro_tipos");
	}
	
	public List<Tipo> getPerfilTypes() {
		return getTableTypes("perfil_tipos");
	}
	
	public List<Tipo> getEncuestaTypes() {
		return getTableTypes("encuesta_tipos");
	}
	
	public List<Tipo> getTableTypes(String tableName) {
		String sql = "SELECT * FROM " + tableName + " WHERE fecha_eliminacion IS NULL";
		return plantilla.query(sql, new MapSqlParameterSource("table_name", tableName), new TipoMapper());
	}
}
