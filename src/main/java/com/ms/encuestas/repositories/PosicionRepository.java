package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.DatosPosicion;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;

@Repository
public class PosicionRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public Long count() {
		String sql = "SELECT COUNT(1) FROM posiciones WHERE fecha_eliminacion IS NULL";
		return plantilla.queryForObject(sql, (MapSqlParameterSource) null, Long.class);
	}
	
	public Long countDatos(Long procesoId) {
		String sql = "SELECT COUNT(1) FROM posicion_datos WHERE proceso_id=:proceso_id";
		return plantilla.queryForObject(sql, new MapSqlParameterSource("proceso_id", procesoId), Long.class);
	}
	
	public List<String> findAllCodigos() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo FROM posiciones";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null, String.class);
	}
	
	public boolean exists(Long procesoId, String posicionCodigo) {
		String sql = "SELECT COUNT(1)\n" +
					 "  FROM posicion_datos\n" +
					 " WHERE proceso_id=:proceso_id\n" +
					 "   AND posicion_codigo=:posicion_codigo";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();		
		paramMap.addValue("proceso_id", procesoId);
		paramMap.addValue("posicion_codigo", posicionCodigo);
		return plantilla.queryForObject(sql, paramMap, Integer.class)==1;
	}
	
	public List<Posicion> findAll() throws EmptyResultDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM posiciones\n" +
				     " ORDER BY nombre";
		return plantilla.query(sql, new PosicionMapper());
	}

	public Posicion findByCodigo(String codigo) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException {
		String sql = "SELECT *\n" +
				     "  FROM posiciones\n" +
				     " WHERE codigo=:codigo";
        return plantilla.queryForObject(sql, new MapSqlParameterSource("codigo", codigo), new PosicionMapper());
	}
	
	public void deleteDatosProceso(Long procesoId) {
		String sql = "DELETE FROM posicion_datos WHERE proceso_id=:proceso_id";
		plantilla.update(sql, new MapSqlParameterSource("proceso_id", procesoId));
	}
	
	public void deleteDatosColaborador(Long procesoId, String usuarioCodigo) {
		String sql = "DELETE FROM posicion_datos\n" +
                     " WHERE proceso_id=:proceso_id\n" +
                     "   AND usuario_codigo=:usuario_codigo";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("proceso_id", procesoId);
		paramMap.addValue("usuario_codigo", usuarioCodigo);
		plantilla.update(sql, paramMap);
	}
	
	public int insertDatos(Proceso proceso, DatosPosicion datos) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO posicion_datos(proceso_id,area_id,centro_id,posicion_codigo,usuario_codigo,responsable_posicion_codigo,perfil_id,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:proceso_id,:area_id,:centro_id,:posicion_codigo,:usuario_codigo,:responsable_posicion_codigo,:perfil_id,:fecha_creacion,:fecha_actualizacion)";		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("proceso_id", proceso.getId());
		paramMap.addValue("area_id", datos.getArea().getId());
		paramMap.addValue("centro_id", datos.getCentro().getId());
		paramMap.addValue("posicion_codigo", datos.getPosicion().getCodigo());
		paramMap.addValue("usuario_codigo", datos.getUsuario().getCodigo());
		paramMap.addValue("responsable_posicion_codigo", datos.getResponsablePosicion().getCodigo());
		paramMap.addValue("perfil_id", datos.getPerfil().getId());
		Date fecha = new Date();
		paramMap.addValue("fecha_creacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		return plantilla.update(sql, paramMap);
	}

	public Posicion findByProcesoIdAndUsuarioCodigo(Long procesoId, String usuarioCodigo) throws EmptyResultDataAccessException {
		String sql = "SELECT B.*\n" +
					 "  FROM posicion_datos A\n" + 
					 "  LEFT JOIN posiciones B\n" + 
					 "    ON A.posicion_codigo=B.codigo\n" + 
					 " WHERE A.proceso_id=:proceso_id\n" +
					 "   AND A.usuario_codigo=:usuario_codigo\n" +
					 "   AND B.fecha_eliminacion IS NULL";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("proceso_id", procesoId);
		paramMap.addValue("usuario_codigo", usuarioCodigo);
		return plantilla.queryForObject(sql, paramMap, new PosicionMapper());
	}

	public List<Map<String, Object>> findAllDatosListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL posicion_codigo,\n" + 
					 "       NULL posicion_nombre,\n" + 
					 "       NULL matricula,\n" + 
					 "       NULL nombre_completo,\n" + 
					 "       NULL area_codigo,\n" + 
					 "       NULL area_nombre,\n" + 
					 "       NULL centro_codigo,\n" + 
					 "       NULL centro_nombre,\n" + 
					 "       NULL perfil_codigo,\n" + 
					 "       NULL perfil_nombre,\n" + 
					 "       NULL responsable_matricula,\n" + 
					 "       NULL responsable_nombre_completo,\n" + 
					 "       NULL responsable_posicion_codigo,\n" + 
					 "       NULL responsable_posicion_nombre,\n" + 
					 "       NULL fecha_creacion,\n" + 
					 "       NULL fecha_actualizacion" + 
					 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}
	
	public List<Map<String, Object>> findAllDatosList(Long procesoId) throws EmptyResultDataAccessException {
		String sql = "SELECT B.codigo posicion_codigo,\n" + 
					 "       B.nombre posicion_nombre,\n" + 
					 "       C.codigo matricula,\n" + 
					 "       C.nombre_completo,\n" + 
					 "       D.codigo area_codigo,\n" + 
					 "       D.nombre area_nombre,\n" + 
					 "       E.codigo centro_codigo,\n" + 
					 "       E.nombre centro_nombre,\n" + 
					 "       F.codigo perfil_codigo,\n" + 
					 "       F.nombre perfil_nombre,\n" + 
					 "       I.codigo responsable_matricula,\n" + 
					 "       I.nombre_completo responsable_nombre_completo,\n" + 
					 "       G.codigo responsable_posicion_codigo,\n" + 
					 "       G.nombre responsable_posicion_nombre,\n" + 
					 "       A.fecha_creacion,\n" + 
					 "       A.fecha_actualizacion\n" + 
					 "  FROM posicion_datos A\n" + 
					 "  JOIN posiciones B ON A.posicion_codigo=B.codigo\n" + 
					 "  LEFT JOIN usuarios C ON A.usuario_codigo=C.codigo\n" + 
					 "  LEFT JOIN areas D ON A.area_id=D.id\n" + 
					 "  LEFT JOIN centros E ON A.centro_id=E.id\n" + 
					 "  LEFT JOIN perfiles F ON A.perfil_id=F.id\n" + 
					 "  LEFT JOIN posiciones G ON A.responsable_posicion_codigo=G.codigo\n" + 
					 "  LEFT JOIN posicion_datos H ON H.posicion_codigo=G.codigo AND A.proceso_id=H.proceso_id\n" + 
					 "  LEFT JOIN usuarios I ON H.usuario_codigo=I.codigo\n" + 
					 " WHERE A.proceso_id=:proceso_id\n" + 
					 " ORDER BY A.fecha_creacion";
		return plantilla.queryForList(sql, new MapSqlParameterSource("proceso_id", procesoId));
	}
	
	public void deleteByCodigo(String codigo) {
		String sql = "DELETE FROM posiciones WHERE codigo=:codigo";
		plantilla.update(sql, new MapSqlParameterSource("codigo", codigo));
	}
	
	public Posicion insert(Posicion posicion) throws EmptyResultDataAccessException {
		String sql = "INSERT INTO posiciones(codigo,nombre,fecha_creacion,fecha_actualizacion)\n" +
                     "VALUES(:codigo,:nombre,:fecha_creacion,:fecha_actualizacion)";		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", posicion.getCodigo());
		paramMap.addValue("nombre", posicion.getNombre());
		Date fecha = new Date();
		paramMap.addValue("fecha_creacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql, paramMap);
		
		posicion.setFechaCreacion(fecha);
		posicion.setFechaActualizacion(fecha);
		return posicion;
	}
	
	public Posicion update(Posicion posicion) throws EmptyResultDataAccessException {
		String sql = "UPDATE posiciones\n" +
					 "   SET nombre=:nombre,\n" +
					 "		 fecha_actualizacion=:fecha_actualizacion\n" +
                     " WHERE codigo=:codigo";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", posicion.getCodigo());
		paramMap.addValue("nombre", posicion.getNombre());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		plantilla.update(sql, paramMap);
		
		posicion.setFechaActualizacion(fecha);
		return posicion;
	}
	
	public void delete(Posicion posicion) {
		String sql = "DELETE FROM posiciones WHERE codigo=:codigo";
		plantilla.update(sql, new MapSqlParameterSource("codigo", posicion.getCodigo()));
	}
	
	public Posicion softDelete(Posicion posicion) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", posicion.getCodigo());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		paramMap.addValue("fecha_eliminacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE posiciones\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=:fecha_eliminacion\n" +
			 	     " WHERE codigo=:codigo";
		plantilla.update(sql, paramMap);
		
		posicion.setFechaActualizacion(fecha);
		posicion.setFechaEliminacion(fecha);
		return posicion;
	}
	
	public Posicion softUndelete(Posicion posicion) throws EmptyResultDataAccessException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("codigo", posicion.getCodigo());
		Date fecha = new Date();
		paramMap.addValue("fecha_actualizacion", fecha, java.sql.Types.DATE);
		
		String sql = "UPDATE posiciones\n" +
			 	     "   SET fecha_actualizacion=:fecha_actualizacion,\n" +
			 	     "		 fecha_eliminacion=NULL\n" +
			 	     " WHERE codigo=:codigo";
		plantilla.update(sql, paramMap);
		
		posicion.setFechaActualizacion(fecha);
		posicion.setFechaEliminacion(null);
		return posicion;
	}
	
	public void deleteAll() {
		String sql = "DELETE FROM posiciones";
		plantilla.update(sql, (MapSqlParameterSource) null);
	}
	
	public List<Map<String,Object>> findAllListEmpty() throws EmptyResultDataAccessException {
		String sql = "SELECT NULL codigo,\n" +
				 	 "       NULL nombre,\n" + 
				 	 "       NULL fecha_creacion,\n" +
				 	 "       NULL fecha_actualizacion\n" +
				 	 "  FROM DUAL";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);		
	}
	
	public List<Map<String,Object>> findAllList() throws EmptyResultDataAccessException {
		String sql = "SELECT codigo,\n" + 
					 "       nombre,\n" +  
					 "       fecha_creacion,\n" +
					 "       fecha_actualizacion\n" +
					 "  FROM posiciones\n" + 
					 " WHERE fecha_eliminacion IS NULL\n" + 
					 " ORDER BY fecha_creacion";
		return plantilla.queryForList(sql, (MapSqlParameterSource) null);
	}

	public List<String> findAllUsuarioCodigosByProcesoId(Long procesoId) {
		String sql = "SELECT usuario_codigo FROM posicion_datos WHERE proceso_id=:proceso_id";
		return plantilla.queryForList(sql, new MapSqlParameterSource("proceso_id", procesoId), String.class);
	}

	public List<String> findAllPosicionCodigosByProcesoId(Long procesoId) {
		String sql = "SELECT posicion_codigo FROM posicion_datos WHERE proceso_id=:proceso_id";
		return plantilla.queryForList(sql, new MapSqlParameterSource("proceso_id", procesoId), String.class);
	}
}
