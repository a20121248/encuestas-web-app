package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.List;

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
					 "		 B.fecha_actualizacion padre_fecha_actualizacion,\n" +
					 "		 B.fecha_eliminacion padre_fecha_eliminacion\n" +
					 "  FROM objetos A\n" +
					 "  LEFT JOIN objetos B\n" +
					 "    ON A.padre_objeto_id=B.id\n" +
					 " WHERE A.objeto_tipo_id=:objeto_tipo_id\n" + 
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
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("codigo", objeto.getCodigo());
		paramSource.addValue("nombre", objeto.getNombre());
		paramSource.addValue("objeto_tipo_id", objetoTipoId);
		paramSource.addValue("padre_objeto_id", objeto.getObjetoPadre().getId());
		Date fecha = new Date();
		paramSource.addValue("fecha_creacion", fecha, java.sql.Types.DATE);
		paramSource.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql,paramSource);
		
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
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", objeto.getId());
		paramSource.addValue("codigo", objeto.getCodigo());
		paramSource.addValue("nombre", objeto.getNombre());
		paramSource.addValue("objeto_tipo_id", objetoTipoId);
		paramSource.addValue("padre_objeto_id", objeto.getObjetoPadre() != null ? objeto.getObjetoPadre().getId() : 0);
		Date fecha = new Date();
		paramSource.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql,paramSource);
		
		objeto.setFechaActualizacion(fecha);
		return objeto;
	}
	
	public Objeto findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.id padre_id,\n" + 
					 "		 B.codigo padre_codigo,\n" + 
					 "		 B.nombre padre_nombre,\n" + 
					 "		 B.fecha_creacion padre_fecha_creacion,\n" + 
					 "		 B.fecha_actualizacion padre_fecha_actualizacion,\n" +
					 "		 B.fecha_actualizacion padre_fecha_eliminacion\n" +
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

	public Objeto softDelete(Objeto objeto) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", objeto.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_eliminacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE objetos\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		objeto.setFechaActualizacion(fecha);
		objeto.setFechaEliminacion(fecha);
		return objeto;
	}
	
	public Objeto softUndelete(Objeto objeto) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", objeto.getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE objetos\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE id=:id";
		plantilla.update(sql, paramMap);
		
		objeto.setFechaActualizacion(fecha);
		objeto.setFechaEliminacion(null);
		return objeto;
	}
}
