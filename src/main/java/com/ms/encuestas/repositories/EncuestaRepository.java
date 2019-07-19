package com.ms.encuestas.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Empresa;

@CrossOrigin(origins={})
@Repository
public class EncuestaRepository {
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public List<Empresa> findAll() {
		String sql = "SELECT * FROM empresas";
		return plantilla.query(sql, new EmpresaMapper());
	}

	public List<Empresa> getEncuestaEmpresa(Long procesoId, String posicionCodigo) {
		String sql = "SELECT B.id,\n" + 
			         "       B.nombre,\n" +
			         "       B.fecha_creacion,\n" +
			         "       A.porcentaje\n" + 
			         "  FROM encuesta_empresa A\n" + 
			         "  JOIN empresas B ON A.empresa_id=B.id\n" + 
			         " WHERE proceso_id=:proceso_id\n" + 
			         "   AND posicion_codigo=:posicion_codigo\n" + 
			         "   AND B.fecha_eliminacion IS NULL";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		return plantilla.query(sql, paramMap, new EmpresaMapper());
	}
	
	public int saveEncuestaEmpresa(Empresa empresa, long procesoId, String posicionCodigo) {		
		String sql = "UPDATE encuesta_empresa\n"+
	                 "   SET porcentaje=:porcentaje\n" +
				     " WHERE posicion_codigo=:posicion_codigo\n" + 
				     "   AND proceso_id=:proceso_id\n" + 
				     "   AND empresa_id=:empresa_id";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("posicion_codigo", posicionCodigo);
        paramMap.put("proceso_id", procesoId);
        paramMap.put("empresa_id", empresa.getId());
        paramMap.put("porcentaje", empresa.getPorcentaje());
	
		return plantilla.update(sql,paramMap);
	}
}
