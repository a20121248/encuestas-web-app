package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Usuario;

@Repository
public class UsuarioRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public Long count() {
		String sql = "SELECT COUNT(1) FROM usuarios WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<Usuario> findAll() {
		String sql = "SELECT * FROM usuarios";
		return plantilla.query(sql, new UsuarioMapper());
	}

	public Usuario findByCodigo(String codigo) {
		String sql = "SELECT *\n" +
					 "  FROM usuarios\n" +
				     " WHERE codigo=:codigo" +
					 "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("codigo", codigo),
        		new UsuarioMapper());
	}
	
	public Usuario findByCodigoWithPosicion(String codigo) {
		String sql = "SELECT A.*,\n" + 
					 "       B.posicion_codigo,\n" + 
					 "       C.nombre posicion_nombre,\n" + 
					 "       C.fecha_creacion posicion_fecha_creacion\n" +
					 "  FROM usuarios A\n" + 
					 "  JOIN posicion_usuario B ON A.codigo=B.usuario_codigo\n" + 
					 "  JOIN posiciones C ON B.posicion_codigo=C.codigo\n" + 
					 " WHERE A.codigo=:codigo\n" + 
					 "   AND A.fecha_eliminacion IS NULL\n" + 
					 "   AND B.fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("codigo", codigo),
        		new UsuarioMapper());
	}

	public Usuario save(Usuario usuario) {
		return null;
	}

	public void delete(Usuario usuario) {
		return;
	}
}
