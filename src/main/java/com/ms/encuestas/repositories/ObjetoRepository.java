package com.ms.encuestas.repositories;

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

import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Proceso;

@Repository
public class ObjetoRepository {
	private Logger logger = LoggerFactory.getLogger(ObjetoRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count(Long objetoTipoId) {
		String sql = "SELECT COUNT(1) FROM objetos WHERE fecha_eliminacion IS NULL AND objeto_tipo_id=:objeto_tipo_id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("objeto_tipo_id", objetoTipoId), Long.class);
	}
	
	public List<Objeto> findAll(Long objetoTipoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.id,\n" +
					 "       A.codigo,\n" +
					 "       A.nombre,\n" +
					 "       A.fecha_creacion,\n" +
					 "       A.fecha_actualizacion,\n" +
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
					 " ORDER BY A.nombre";
		return plantilla.query(sql, new MapSqlParameterSource("objeto_tipo_id", objetoTipoId), new ObjetoMapper());
	}
	
	public Proceso findById(Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT id proceso_id,\n" + 
					 "       nombre proceso_nombre,\n" + 
					 "       fecha_cierre proceso_fecha_cierre,\n" + 
					 "       fecha_creacion proceso_fecha_creacion,\n" + 
					 "       fecha_actualizacion proceso_fecha_actualizacion\n" + 
					 "  FROM procesos\n" + 
					 " WHERE id=:proceso_id\n" + 
					 " ORDER BY ID";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		return plantilla.queryForObject(sql, paramMap, new ProcesoMapper());
	}

}
