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

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;
import com.ms.encuestas.models.Perfil;

@Repository
public class PerfilRepository {
	private Logger logger = LoggerFactory.getLogger(PerfilRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;

	public Long count() {
		String sql = "SELECT COUNT(1) FROM perfiles WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM perfiles";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}
	
	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL codigo,\n" + 
				 	 "       NULL nombre,\n" + 
				 	 "       NULL tipo,\n" + 
				 	 "       NULL dimension1_codigo,\n" + 
				 	 "       NULL dimension1_nombre,\n" + 
				 	 "       NULL dimension2_codigo,\n" + 
				 	 "       NULL dimension2_nombre,\n" +
				 	 "       NULL fecha_creacion,\n" +
				 	 "       NULL fecha_actualizacion\n" +
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT A.codigo,\n" + 
					 "       A.nombre,\n" + 
					 "       B.nombre tipo,\n" + 
					 "       NVL(C1.codigo,D1.codigo) dimension1_codigo,\n" + 
					 "       NVL(C1.nombre,D1.nombre) dimension1_nombre,\n" + 
					 "       D2.codigo dimension2_codigo,\n" + 
					 "       D2.nombre dimension2_nombre,\n" +
					 "       A.fecha_creacion,\n" +
					 "       A.fecha_actualizacion\n" +
					 "  FROM perfiles A\n" +
					 "  JOIN perfil_tipos B ON A.perfil_tipo_id=B.id\n" + 
					 "  LEFT JOIN perfil_centro C ON A.id=C.perfil_id\n" + 
					 "  LEFT JOIN perfil_linea_canal D ON A.id=D.perfil_id\n" + 
					 "  LEFT JOIN centros C1 ON C.centro_id=C1.id\n" + 
					 "  LEFT JOIN objetos D1 ON D.linea_id=D1.id\n" + 
					 "  LEFT JOIN objetos D2 ON D.canal_id=D2.id\n" + 
					 " WHERE A.fecha_eliminacion IS NULL\n" + 
					 " ORDER BY A.codigo,C1.codigo,D1.codigo,D2.codigo";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
	
	public List<Perfil> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
				 	 "       B.id tipo_id,\n" +
				 	 "       B.nombre tipo_nombre,\n" +
				 	 "       B.fecha_creacion tipo_fecha_creacion,\n" +
				 	 "       B.fecha_actualizacion tipo_fecha_actualizacion\n" +
				 	 "  FROM perfiles A\n" +
				 	 "  JOIN perfil_tipos B ON A.perfil_tipo_id=B.id\n" + 
				 	 " WHERE A.fecha_eliminacion IS NULL";
		return plantilla.query(sql, (MapSqlParameterSource) null, new PerfilMapper());
	}
	
	public Perfil findById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.id tipo_id,\n" +
				 	 "       B.nombre tipo_nombre,\n" +
				 	 "       B.fecha_creacion tipo_fecha_creacion,\n" +
				 	 "       B.fecha_actualizacion tipo_fecha_actualizacion\n" +
				 	 "  FROM perfiles A\n" +
				 	 "  JOIN perfil_tipos B ON A.perfil_tipo_id=B.id\n" + 
				 	 " WHERE A.id=:id";
	   return plantilla.queryForObject(sql, new MapSqlParameterSource("id", id), new PerfilMapper());
	}
	
	public Perfil findByCodigo(String codigo) throws EmptyResultDataAccessException {
		String sql = "SELECT A.*,\n" +
					 "       B.id tipo_id,\n" +
				 	 "       B.nombre tipo_nombre,\n" +
				 	 "       B.fecha_creacion tipo_fecha_creacion,\n" +
				 	 "       B.fecha_actualizacion tipo_fecha_actualizacion\n" +
				 	 "  FROM perfiles A\n" +
				 	 "  JOIN perfil_tipos B ON A.perfil_tipo_id=B.id\n" + 
				 	 " WHERE A.codigo=:codigo";
        return plantilla.queryForObject(sql, new MapSqlParameterSource("codigo", codigo), new PerfilMapper());
	}
	
	public Long insert(Perfil perfil) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO perfiles(codigo,nombre,perfil_tipo_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:perfil_tipo_id,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codigo", perfil.getCodigo());
		paramMap.put("nombre", perfil.getNombre());
		paramMap.put("perfil_tipo_id", perfil.getTipo().getId());
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		plantilla.update(sql,paramMap);
		
		sql = "SELECT perfiles_seq.currval FROM DUAL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
		
	public Long update(Perfil perfil) throws EmptyResultDataAccessException {
		String sql = "UPDATE perfiles\n" +
					 "   SET codigo=:codigo,\n" +
					 "       nombre=:nombre,\n" +
					 "       perfil_tipo_id=:perfil_tipo_id,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
				     " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", perfil.getId());
		paramMap.put("codigo", perfil.getCodigo());
		paramMap.put("nombre", perfil.getNombre());
		paramMap.put("perfil_tipo_id", perfil.getTipo().getId());
		paramMap.put("fecha_actualizacion", new Date());
		plantilla.update(sql,paramMap);
		return perfil.getId();
	}
	
	public int insertLstCentros(Long perfilId, List<Centro> lstCentros) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO perfil_centro(perfil_id,centro_id,fecha_creacion,fecha_actualizacion)\n" +
					 "VALUES(:perfil_id,:centro_id,:fecha_creacion,:fecha_actualizacion)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("perfil_id", perfilId);
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		
		for (Centro centro : lstCentros) {
			paramMap.put("centro_id", centro.getId());
			plantilla.update(sql,paramMap);
		}
		return 1;
	}

	public void deleteDetalleCentros(Long perfilId) {
		String sql = "DELETE FROM perfil_centro WHERE perfil_id=:perfil_id";
		plantilla.update(sql, new MapSqlParameterSource("perfil_id", perfilId));
	}
	
	public void deleteDetalleLineasCanales(Long perfilId) {
		String sql = "DELETE FROM perfil_linea_canal WHERE perfil_id=:perfil_id";
		plantilla.update(sql, new MapSqlParameterSource("perfil_id", perfilId));
	}
	
	public int insertLstLineasCanales(Long perfilId, List<LineaCanal> lstLineasCanales) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO perfil_linea_canal(perfil_id,linea_id,canal_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:perfil_id,:linea_id,:canal_id,:fecha_creacion,:fecha_actualizacion)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("perfil_id", perfilId);
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);		
		for (LineaCanal lineaCanal : lstLineasCanales) {
			paramMap.put("linea_id", lineaCanal.getLinea().getId());			
			paramMap.put("canal_id", lineaCanal.getCanal().getId());
			plantilla.update(sql,paramMap);
		}
		return 1;
	}
	
	public int delete(Perfil perfil) throws EmptyResultDataAccessException {
		String sql = "UPDATE perfiles\n" +
				 	 "   SET fecha_eliminacion=:fecha_eliminacion" +
				 	 " WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", perfil.getId());
		paramMap.put("fecha_eliminacion", new Date());
		return plantilla.update(sql,paramMap);
	}
}
