package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String sql = "SELECT * FROM roles";
		return plantilla.query(sql, new RolMapper());
	}
	
	public Rol findById(Long id) {
		String queryStr = "SELECT * FROM roles WHERE id=:id";
        return plantilla.queryForObject(queryStr,new MapSqlParameterSource("id", id), new RolMapper());
	}
	
	public List<Rol> findAllByUsuarioCodigo(String usuarioCodigo) {
		String sql = "SELECT B.id,\n" + 
			     	 "       B.nombre\n" + 
			     	 "  FROM rol_usuario A\n" + 
			     	 "  JOIN roles B ON A.rol_id=B.id\n" + 
				     " WHERE A.usuario_codigo=':usuarioCodigo'";		
		return plantilla.query(sql, new MapSqlParameterSource("usuario_codigo", usuarioCodigo), new RolMapper());
	}

	public void deletesRolesUsuario(String usuarioCodigo) {
		String sql = "DELETE FROM rol_usuario WHERE usuario_codigo=:usuario_codigo";
		plantilla.update(sql, new MapSqlParameterSource("usuario_codigo", usuarioCodigo));
	}
	
	public void insertRolUsuario(Long rolId, String usuarioCodigo) {
		String sql = "INSERT INTO rol_usuario(usuario_codigo,rol_id)\n" +
                     "VALUES(:usuario_codigo,:rol_id)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rol_id", rolId);
		paramMap.put("usuario_codigo", usuarioCodigo);        
		plantilla.update(sql, paramMap);
	}
}
