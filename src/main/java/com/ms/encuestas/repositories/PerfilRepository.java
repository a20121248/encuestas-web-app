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
	
	public List<Perfil> findAll() throws EmptyResultDataAccessException {
		return null;
	}
	
	public Perfil findById(Long procesoId) throws EmptyResultDataAccessException {
		return null;
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
				     " WHERE perfil_id=:perfil_id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("perfil_id", perfil.getId());
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
			return plantilla.update(sql,paramMap);
		}
		return 1;
	}
	
	public int delete(Perfil perfil) throws EmptyResultDataAccessException {
		return 0;
	}
}
