package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Rol;

@Repository
public class RolRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) CNT FROM roles WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Rol> findAll() {
		String sql = "SELECT id,\n" +
					 "       nombre,\n" +
					 "       fecha_creacion\n" +
					 "  FROM roles";
		return plantilla.query(sql, new RolMapper());
	}
	
	public Rol findById(Long id) {
		String queryStr = "SELECT id,\n" +
                		  "       nombre,\n" +
                		  "       fecha_creacion\n" +
                		  "  FROM roles WHERE id=:id";
        return plantilla.queryForObject(queryStr,new MapSqlParameterSource("id", id),new RolMapper());
	}
	
	public List<Rol> findAllByUsuarioCodigo(String usuarioCodigo) {
		String sql = "SELECT B.id,\n" + 
			     	 "       B.nombre\n" + 
			     	 "  FROM rol_usuario A\n" + 
			     	 "  JOIN roles B ON A.rol_id=B.id\n" + 
				     " WHERE A.usuario_codigo=':usuarioCodigo'";		
		return plantilla.query(sql, new MapSqlParameterSource("usuarioCodigo", usuarioCodigo), new RolMapper());
	}
}
