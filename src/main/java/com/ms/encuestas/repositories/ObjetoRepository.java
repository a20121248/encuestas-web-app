package com.ms.encuestas.repositories;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Objeto;

@Repository
public class ObjetoRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count(Long objetoTipoId) {
		String sql = "SELECT COUNT(1) FROM objetos WHERE fecha_eliminacion IS NULL AND objeto_tipo_id=:objeto_tipo_id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("objeto_tipo_id", objetoTipoId), Long.class);
	}
	
	public List<Objeto> findAll(Long objetoTipoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.id padre_id,\n" + 
					 "		 B.codigo padre_codigo,\n" + 
					 "		 B.nombre padre_nombre,\n" + 
					 "		 B.fecha_creacion padre_fecha_creacion,\n" + 
					 "		 B.fecha_actualizacion padre_fecha_actualizacion\n" + 
					 "  FROM objetos A\n" +
					 "  LEFT JOIN objetos B\n" +
					 "    ON A.padre_objeto_id=B.id\n" +
					 " WHERE A.fecha_eliminacion IS NULL\n" +
					 "   AND A.objeto_tipo_id=:objeto_tipo_id\n" + 
					 " ORDER BY B.nombre,A.nombre";
		return plantilla.query(sql, new MapSqlParameterSource("objeto_tipo_id", objetoTipoId), new ObjetoMapper());
	}
	
	public void deleteAll(Long objetoTipoId) {
		String sql = "DELETE FROM objetos WHERE objeto_tipo_id=:objeto_tipo_id";
		plantilla.update(sql, new MapSqlParameterSource("objeto_tipo_id", objetoTipoId));
	}
	
	public Objeto insert(Objeto objeto, Long objetoTipoId) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO objetos(codigo,nombre,objeto_tipo_id,padre_objeto_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:objeto_tipo_id,:padre_objeto_id,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", objeto.getCodigo());
		paramMap.put("nombre", objeto.getNombre());
		paramMap.put("objeto_tipo_id", objetoTipoId);
		paramMap.put("padre_objeto_id", objeto.getObjetoPadre().getId());
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);        
		plantilla.update(sql,paramMap);
		
		sql = "SELECT objetos_seq.currval FROM DUAL";
		objeto.setId(plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class));
		objeto.setFechaCreacion(fecha);
		objeto.setFechaActualizacion(fecha);
		return objeto;
	}
	
	public Objeto update(Objeto objeto, Long objetoTipoId) throws EmptyResultDataAccessException {
		String sql = "UPDATE objetos\n" +
				 	 "   SET codigo=:codigo,\n" +
				 	 "       nombre=:nombre,\n" +
				 	 "		 objeto_tipo_id=:objeto_tipo_id,\n" +
				 	 "		 padre_objeto_id=:padre_objeto_id,\n" +
				 	 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				 	 " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", objeto.getId());
		paramMap.put("codigo", objeto.getCodigo());
		paramMap.put("nombre", objeto.getNombre());
		paramMap.put("objeto_tipo_id", objetoTipoId);
		paramMap.put("padre_objeto_id", objeto.getObjetoPadre() != null ? objeto.getObjetoPadre().getId() : 0);
		LocalDateTime fecha = LocalDateTime.now();
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql, paramMap);
		
		objeto.setFechaActualizacion(fecha);
		return objeto;
	}
	
	public Objeto findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.id padre_id,\n" + 
					 "		 B.codigo padre_codigo,\n" + 
					 "		 B.nombre padre_nombre,\n" + 
					 "		 B.fecha_creacion padre_fecha_creacion,\n" + 
					 "		 B.fecha_actualizacion padre_fecha_actualizacion\n" + 
					 "  FROM objetos A\n" +
				     "  LEFT JOIN objetos B\n" +
					 "    ON A.padre_objeto_id=B.id\n" + 
					 " WHERE A.id=:id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("id", id), new ObjetoMapper());
	}
	
	public void deleteById(Long id) {
		String sql = "DELETE FROM objetos WHERE id=:id";
		plantilla.update(sql, new MapSqlParameterSource("id", id));
	}
}
