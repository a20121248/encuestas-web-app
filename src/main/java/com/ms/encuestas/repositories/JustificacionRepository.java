package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Justificacion;

@Repository
public class JustificacionRepository {	
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM justificaciones WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Justificacion> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM justificaciones\n" +
				     " WHERE fecha_eliminacion IS NULL";
		return plantilla.query(sql, new JustificacionMapper());
	}

	public Justificacion findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM justificaciones\n" +
				     " WHERE id=:id\n" +
				     "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("id", id),
        		new JustificacionMapper());
	}
	
	public void deleteById(Long id) {
		String sql = "DELETE FROM justificaciones WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
	
	public void delete(Justificacion justificacion) {
		String sql = "DELETE FROM justificaciones WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", justificacion.getId()));
	}
	
	public Justificacion softDelete(Justificacion justificacion) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", justificacion.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_eliminacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE justificaciones\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		justificacion.setFechaActualizacion(fecha);
		justificacion.setFechaEliminacion(fecha);
		return justificacion;
	}
	
	public Justificacion softUndelete(Justificacion justificacion) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", justificacion.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE justificaciones\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		justificacion.setFechaActualizacion(fecha);
		justificacion.setFechaEliminacion(null);
		return justificacion;
	}
}
