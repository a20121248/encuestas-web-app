package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Centro;

@Repository
public class CentroRepository {
	private Logger logger = LoggerFactory.getLogger(CentroRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM centros WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}

	public List<Centro> findAll() {
		String sql = "SELECT A.id centro_id,\n" + 
					 "       A.codigo centro_codigo,\n" + 
					 "       A.nombre centro_nombre,\n" + 
					 "       A.nivel centro_nivel,\n" + 
					 "       B.id centro_tipo_id,\n" + 
					 "       B.nombre centro_tipo_nombre,\n" + 
					 "       A.grupo centro_grupo,\n" + 
					 "       A.fecha_creacion centro_fecha_creacion,\n" + 
					 "       A.fecha_actualizacion centro_fecha_actualizacion\n" + 
					 "  FROM centros A\n" + 
					 "  JOIN centro_tipos B\n" + 
					 "    ON A.centro_tipo_id=B.id\n" + 
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 "   AND A.empresa_id=1\n" + 
					 " ORDER BY A.id";
	    return plantilla.query(sql, new CentroMapper());
	}

	public Centro findById(Long centroId) {
		String sql = "SELECT A.id centro_id,\n" + 
				 	 "       A.codigo centro_codigo,\n" + 
				 	 "       A.nombre centro_nombre,\n" + 
				 	 "       A.nivel centro_nivel,\n" + 
				 	 "       B.id centro_tipo_id,\n" + 
				 	 "       B.nombre centro_tipo_nombre,\n" + 
				 	 "       A.grupo centro_grupo,\n" + 
				 	 "       A.fecha_creacion centro_fecha_creacion,\n" + 
				 	 "       A.fecha_actualizacion centro_fecha_actualizacion\n" + 
				 	 "  FROM centros A\n" + 
				 	 "  JOIN centro_tipos B\n" + 
				 	 "    ON A.centro_tipo_id=B.id\n" + 
				 	 " WHERE A.fecha_eliminacion IS NULL\n" +
				 	 "   AND A.id=:centro_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("centro_id", centroId);
        return plantilla.queryForObject(sql, paramMap, new CentroMapper());
	}

	public Centro save(Centro centro) {
		return null;
	}

	public void delete(Centro centro) {
		return;
	}
}
