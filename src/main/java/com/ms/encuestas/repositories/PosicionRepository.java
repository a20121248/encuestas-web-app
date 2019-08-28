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

import com.ms.encuestas.models.DatosPosicion;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;

@Repository
public class PosicionRepository {
	private Logger logger = LoggerFactory.getLogger(PosicionRepository.class);
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM posiciones WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public boolean exists(Long procesoId, String posicionCodigo) {
		String sql = "SELECT COUNT(1)\n" +
					 "  FROM posicion_datos\n" +
					 " WHERE proceso_id=:proceso_id\n" +
					 "   AND posicion_codigo=:posicion_codigo";
		Map<String, Object> paramMap = new HashMap<String, Object>();		
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		return plantilla.queryForObject(sql, paramMap, Integer.class)==1;
	}
	
	public List<Posicion> findAll() {
		String sql = "SELECT *\n" +
				     "  FROM posiciones\n" +
				     " WHERE fecha_eliminacion IS NULL";
		return plantilla.query(sql, new PosicionMapper());
	}

	public Posicion findByCodigo(String codigo) {
		String sql = "SELECT *\n" +
				     "  FROM posiciones\n" +
				     " WHERE codigo=:codigo\n" +
				     "   AND fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("codigo", codigo),
        		new PosicionMapper());
	}
	
	public Posicion findByCodigoWithArea(String codigo) {
		String sql = "SELECT A.codigo,\n" + 
					 "       A.nombre,\n" + 
					 "       A.fecha_creacion,\n" + 
					 "       B.id area_id,\n" + 
					 "       B.nombre area_nombre,\n" + 
					 "       B.fecha_creacion area_fecha_creacion," +
					 "       C.id division_id,\n" + 
					 "       C.nombre division_nombre,\n" + 
					 "       C.fecha_creacion division_fecha_creacion" +
					 "  FROM posiciones A\n" + 
					 "  JOIN areas B ON A.area_id=B.id\n" +
					 "  JOIN divisiones C ON B.division_id=C.id\n" +
					 " WHERE A.codigo=:codigo\n" +
				     "   AND A.fecha_eliminacion IS NULL";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("codigo", codigo),
        		new PosicionMapper());
	}
	
	public Posicion findByCodigoWithCentro(String codigo) {
		String sql = "SELECT A.codigo,\n" + 
				 	 "       A.nombre,\n" + 
				 	 "       A.fecha_creacion,\n" + 
				 	 "       B.id centro_id,\n" + 
				 	 "       B.nombre centro_nombre,\n" + 
				 	 "       B.fecha_creacion centro_fecha_creacion\n" + 
				 	 "  FROM posiciones A\n" + 
				 	 "  JOIN centros B ON A.centro_id=B.id\n" + 
				 	 " WHERE A.codigo=:codigo\n" +
				 	 "   AND A.fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql,
				new MapSqlParameterSource("codigo", codigo),
				new PosicionMapper());
	}
	
	public Posicion findByCodigoWithAreaAndCentro(String codigo) {
		String sql = "SELECT A.codigo,\n" + 
				 	 "       A.nombre,\n" + 
				 	 "       A.fecha_creacion,\n" + 
				 	 "       B.id area_id,\n" + 
				 	 "       B.nombre area_nombre,\n" + 
				 	 "       B.fecha_creacion area_fecha_creacion," +
				 	 "       C.id division_id,\n" + 
				 	 "       C.nombre division_nombre,\n" + 
				 	 "       C.fecha_creacion division_fecha_creacion," +
				 	 "       D.id centro_id,\n" +
				 	 "       D.codigo centro_codigo,\n" +
				 	 "       D.nombre centro_nombre,\n" +
				 	 "       D.nivel centro_nivel,\n" +
				 	 "       D.fecha_creacion centro_fecha_creacion" +
				 	 "  FROM posiciones A\n" + 
				 	 "  JOIN areas B ON A.area_id=B.id\n" +
				 	 "  JOIN divisiones C ON B.division_id=C.id\n" +
				 	 "  JOIN centros D ON A.centro_id=D.id\n" +
				 	 " WHERE A.codigo=:codigo\n" +
				 	 "   AND A.fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql,
				new MapSqlParameterSource("codigo", codigo),
				new PosicionMapper());
	}
	
	public int deleteDatosProceso(Long procesoId) {
		String sql = "DELETE FROM posicion_datos WHERE proceso_id=:proceso_id";
		return plantilla.update(sql, new MapSqlParameterSource("proceso_id", procesoId));		
	}
	
	public int insertDatos(Proceso proceso, DatosPosicion datos) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO posicion_datos(proceso_id,area_id,centro_id,posicion_codigo,usuario_codigo,responsable_posicion_codigo,perfil_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:proceso_id,:area_id,:centro_id,:posicion_codigo,:usuario_codigo,:responsable_posicion_codigo,:perfil_id,:fecha_creacion,:fecha_actualizacion)";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", proceso.getId());
		paramMap.put("area_id", datos.getArea().getId());
		paramMap.put("centro_id", datos.getCentro().getId());
		paramMap.put("posicion_codigo", datos.getPosicion().getCodigo());
		paramMap.put("usuario_codigo", datos.getUsuario().getCodigo());
		paramMap.put("responsable_posicion_codigo", datos.getResponsablePosicion().getCodigo());
		paramMap.put("perfil_id", datos.getPerfil().getId());
		Date fecha = new Date();
		paramMap.put("fecha_creacion", fecha);
		paramMap.put("fecha_actualizacion", fecha);
		return plantilla.update(sql, paramMap);
	}
}
