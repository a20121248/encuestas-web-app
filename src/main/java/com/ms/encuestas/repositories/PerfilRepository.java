package com.ms.encuestas.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Proceso;

@Repository
public class PerfilRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public Proceso getCurrentProceso() {
		String sql = "SELECT A.id proceso_id,\n" + 
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
}
