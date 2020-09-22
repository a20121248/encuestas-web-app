package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Area;

@Repository
public class AreaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM areas WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM areas";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}
	
	public List<Area> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
					 "  FROM areas\n" +
					 " ORDER BY codigo";
		return plantilla.query(sql, new AreaMapper());
	}

	public Area findById(Long id) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE id=:id";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public Area findByCodigo(String codigo) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE codigo=:codigo";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", codigo);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public Area insert(Area area) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO areas(codigo,nombre,division,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:division,:fecha_creacion,:fecha_actualizacion)";		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", area.getCodigo());
		paramMap.addValue("nombre", area.getNombre());
		paramMap.addValue("division", area.getDivision());
		Date fecha = new Date();
		paramMap.addValue("fecha_creacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql,paramMap);
		
		sql = "SELECT areas_seq.currval FROM DUAL";
		area.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		area.setFechaCreacion(fecha);
		area.setFechaActualizacion(fecha);
		return area;
	}
	
	public Area update(Area area) throws EmptyResultDataAccessException {
		String sql = "UPDATE areas\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "		 division=:division,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
                     " WHERE id=:id";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", area.getId());
		paramMap.addValue("codigo", area.getCodigo());
		paramMap.addValue("nombre", area.getNombre());
		paramMap.addValue("division", area.getDivision());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql, paramMap);
		
		area.setFechaActualizacion(fecha);
		return area;
	}

	public void deleteById(Long id) {
		String sql = "DELETE FROM areas WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
	
	public void delete(Area area) {
		String sql = "DELETE FROM areas WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", area.getId()));
	}
	
	public void deleteAll() {
		String sql = "DELETE FROM areas";
		plantilla.update(sql, (MapSqlParameterSource) null);
	}
	
	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL codigo,\n" + 
				 	 "       NULL nombre,\n" + 
				 	 "       NULL division,\n" + 
				 	 "       NULL fecha_creacion,\n" +
				 	 "       NULL fecha_actualizacion\n" +
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo,\n" + 
					 "       nombre,\n" + 
					 "       division,\n" +  
					 "       fecha_creacion,\n" +
					 "       fecha_actualizacion\n" +
					 "  FROM areas\n" + 
					 " WHERE fecha_eliminacion IS NULL\n" + 
					 " ORDER BY fecha_creacion";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
	
	public Area softDelete(Area area) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", area.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_eliminacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE areas\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		area.setFechaActualizacion(fecha);
		area.setFechaEliminacion(fecha);
		return area;
	}
	
	public Area softUndelete(Area area) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", area.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE areas\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		area.setFechaActualizacion(fecha);
		area.setFechaEliminacion(null);
		return area;
	}
}
