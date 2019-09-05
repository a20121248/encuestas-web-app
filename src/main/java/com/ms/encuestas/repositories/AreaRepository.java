package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Usuario;

@Repository
public class AreaRepository {
	private Logger logger = LoggerFactory.getLogger(AreaRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM areas WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM areas";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}

	public Area findById(Long id) throws EmptyResultDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public Area findByCodigo(String codigo) throws EmptyResultDataAccessException {
		String queryStr = "SELECT *\n" +
						  "  FROM areas\n" +
						  " WHERE codigo=:codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", codigo);
        return plantilla.queryForObject(queryStr, paramMap, new AreaMapper());
	}
	
	public List<Area> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT id,\n" +
					 "       codigo,\n" +
					 "       nombre,\n" +
					 "       division,\n" +
					 "       fecha_creacion,\n" +
					 "       fecha_actualizacion\n" +
					 "  FROM areas\n" +
					 " WHERE fecha_eliminacion IS NULL\n" +
					 " ORDER BY codigo";
		return plantilla.query(sql, new AreaMapper());
	}
	
	public List<Area> findAllWithDivision() throws EmptyResultDataAccessException {
		String sql = "" +
				"SELECT A.id area_id,\n" + 
				"       A.nombre area_nombre,\n" + 
				"       A.fecha_creacion area_fecha_creacion,\n" + 
				"       B.id division_id,\n" + 
				"       B.nombre division_nombre,\n" + 
				"       B.fecha_creacion division_fecha_creacion\n" + 
				"  FROM areas A\n" + 
				"  JOIN divisiones B ON A.division_id=B.id\n" + 
				" WHERE A.fecha_eliminacion IS NULL\n" + 
				"   AND B.fecha_eliminacion IS NULL";
		return plantilla.query(sql, new AreaMapper());
	}
	
	public Area findByIdWithDivision(Long id) throws EmptyResultDataAccessException {
		String sql = "" +
				"SELECT A.id area_id,\n" + 
				"       A.nombre area_nombre,\n" + 
				"       A.fecha_creacion area_fecha_creacion,\n" + 
				"       B.id division_id,\n" + 
				"       B.nombre division_nombre,\n" + 
				"       B.fecha_creacion division_fecha_creacion\n" + 
				"  FROM areas A\n" + 
				"  JOIN divisiones B ON A.division_id=B.id\n" + 
				" WHERE A.id=:area_id\n" +
				"   AND A.fecha_eliminacion IS NULL\n" + 
				"   AND B.fecha_eliminacion IS NULL";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("area_id", id);
		
		return plantilla.queryForObject(sql, paramMap, new AreaMapper());
	}
	
	public Area insert(Area area) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO areas(codigo,nombre,division,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:division,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", area.getCodigo());
		paramMap.put("nombre", area.getNombre());
		paramMap.put("division", area.getDivision());
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql,paramMap);
		
		sql = "SELECT areas_seq.currval FROM DUAL";
		area.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		return area;
	}
	
	public Area update(Area area) throws EmptyResultDataAccessException {
		String sql = "UPDATE areas\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "		 division=:division,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
                     " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", area.getId());
		paramMap.put("codigo", area.getCodigo());
		paramMap.put("nombre", area.getNombre());
		paramMap.put("division", area.getDivision());
		paramMap.put("fecha_actualizacion", new Date());
		plantilla.update(sql,paramMap);
		
		return area;
	}
	
	public void delete(Area area) {
		String sql = "DELETE FROM areas WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", area.getCodigo()));
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
}
