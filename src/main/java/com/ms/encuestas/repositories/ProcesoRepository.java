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

import com.ms.encuestas.models.Division;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Proceso;

@Repository
public class ProcesoRepository {
	private Logger logger = LoggerFactory.getLogger(ProcesoRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public Proceso getCurrentProceso() {
		String sql = "SELECT A.id proceso_id,\n" +
					 "       A.codigo proceso_codigo,\n" +
			         "       A.nombre proceso_nombre,\n" + 
			         "       A.fecha_cierre proceso_fecha_cierre,\n" + 
			         "       A.fecha_creacion proceso_fecha_creacion,\n" + 
			         "       A.fecha_actualizacion proceso_fecha_actualizacion,\n" + 
			         "       B.codigo usuario_codigo,\n" +
			         "       B.nombre_completo usuario_nombre_completo,\n" + 
			         "       B.fecha_creacion usuario_fecha_creacion,\n" + 
			         "       B.fecha_actualizacion usuario_fecha_actualizacion\n" + 
			         "  FROM procesos A\n" + 
			         "  JOIN usuarios B ON A.usuario_codigo=B.codigo\n" + 
			         " WHERE A.fecha_eliminacion IS NULL\n" + 
			         "   AND A.fecha_cierre IS NULL";
        return plantilla.queryForObject(sql, (MapSqlParameterSource) null, new ProcesoMapper());
	}
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM procesos WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Proceso> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT A.id proceso_id,\n" +
					 "       A.codigo proceso_codigo,\n" +
					 "       A.nombre proceso_nombre,\n" +
					 "       B.codigo usuario_codigo,\n" +
					 "       B.nombre_completo usuario_nombre_completo,\n" +
					 "       A.fecha_cierre proceso_fecha_cierre,\n" +
					 "       A.fecha_creacion proceso_fecha_creacion,\n" +
					 "       A.fecha_actualizacion proceso_fecha_actualizacion\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 " ORDER BY A.fecha_creacion";
		return plantilla.query(sql, new ProcesoMapper());
	}
	
	public Proceso findById(Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT A.id proceso_id,\n" +
					 "       A.codigo proceso_codigo,\n" +
					 "       A.nombre proceso_nombre,\n" +
					 "       B.codigo usuario_codigo,\n" +
					 "       B.nombre_completo usuario_nombre_completo,\n" +
					 "       A.fecha_cierre proceso_fecha_cierre,\n" + 
					 "       A.fecha_creacion proceso_fecha_creacion,\n" + 
					 "       A.fecha_actualizacion proceso_fecha_actualizacion\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.id=:proceso_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		return plantilla.queryForObject(sql, paramMap, new ProcesoMapper());
	}
	
	public Proceso findByCodigo(String procesoCodigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.id proceso_id,\n" +
				 	 "       A.codigo proceso_codigo,\n" +
				 	 "       A.nombre proceso_nombre,\n" +
				 	 "       B.codigo usuario_codigo,\n" +
				 	 "       B.nombre_completo usuario_nombre_completo,\n" +
				 	 "       A.fecha_cierre proceso_fecha_cierre,\n" +
				 	 "       A.fecha_creacion proceso_fecha_creacion,\n" +
				 	 "       A.fecha_actualizacion proceso_fecha_actualizacion\n" + 
					 "  FROM procesos A\n" +
					 "  LEFT JOIN usuarios B\n" +
					 "    ON A.usuario_codigo=B.codigo\n" +
					 " WHERE A.codigo=:proceso_codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_codigo", procesoCodigo);
		return plantilla.queryForObject(sql, paramMap, new ProcesoMapper());
	}
	
	public int insert(Proceso proceso) {
		String sql = "INSERT INTO procesos(codigo,nombre,usuario_codigo,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:usuario_codigo,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", proceso.getCodigo());
		paramMap.put("nombre", proceso.getNombre());
		paramMap.put("usuario_codigo", proceso.getUsuario().getCodigo());
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);        
		return plantilla.update(sql, paramMap);
	}
	
	public int update(Proceso proceso) {
		String sql = "UPDATE procesos\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "       fecha_cierre=:fecha_cierre,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				     " WHERE id=:id";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", proceso.getId());
		paramMap.put("codigo", proceso.getCodigo());
		paramMap.put("nombre", proceso.getNombre());
		paramMap.put("fecha_cierre", proceso.getFechaCierre());
		paramMap.put("fecha_actualizacion", new Date());        
		return plantilla.update(sql, paramMap);
	}

}
