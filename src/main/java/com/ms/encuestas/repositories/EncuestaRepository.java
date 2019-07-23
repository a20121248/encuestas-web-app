package com.ms.encuestas.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ms.encuestas.models.Centro;
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
		String sql = "SELECT A.id,\n" + 
		             "       A.nombre,\n" +
		             "       A.fecha_creacion,\n" +
		             "       NVL(B.porcentaje,0) porcentaje\n" + 
		             "  FROM empresas A\n" + 
			         "  LEFT JOIN encuesta_empresa B\n" +
			         "    ON A.id=B.empresa_id\n" +
			         "   AND B.proceso_id=:proceso_id\n" +
			         "   AND B.posicion_codigo=:posicion_codigo\n" + 
		             " WHERE A.fecha_eliminacion IS NULL\n" +
		             " ORDER BY A.id";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		return plantilla.query(sql, paramMap, new EmpresaMapper());
	}
	
	public List<Centro> getEncuestaCentro(Long empresaId, Long procesoId, String posicionCodigo) {
		String sql = "SELECT A.id,\n" + 
					 "       A.codigo,\n" +
			         "       A.nombre,\n" +
			         "       A.nivel,\n" +
			         "       A.fecha_creacion,\n" +
			         "       NVL(B.porcentaje,0) porcentaje\n" + 
			         "  FROM centros A\n" + 
			         "  LEFT JOIN encuesta_centro B\n" +
			         "    ON A.id=B.centro_id\n" +
			         "   AND B.proceso_id=:proceso_id\n" +
			         "   AND B.posicion_codigo=:posicion_codigo\n" + 
			         " WHERE A.empresa_id=:empresa_id\n" + 
			         "   AND A.fecha_eliminacion IS NULL\n" +
			         " ORDER BY A.id";		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("empresa_id", empresaId);
		paramMap.put("proceso_id", procesoId);
		paramMap.put("posicion_codigo", posicionCodigo);
		
		return plantilla.query(sql, paramMap, new CentroMapper());
	}
	
	public void saveEncuestaEmpresa(List<Empresa> lstEmpresas, Long procesoId, String posicionCodigo) {
		String sql;
		for (Empresa empresa: lstEmpresas) {
			sql = "UPDATE encuesta_empresa\n"+
                  "   SET porcentaje=:porcentaje\n" +
			      " WHERE posicion_codigo=:posicion_codigo\n" + 
			      "   AND proceso_id=:proceso_id\n" + 
			      "   AND empresa_id=:empresa_id";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("proceso_id", procesoId);
	        paramMap.put("empresa_id", empresa.getId());
	        paramMap.put("porcentaje", empresa.getPorcentaje());
	        plantilla.update(sql,paramMap);
		}
	}
	
	public void saveEncuestaCentro(List<Centro> lstCentros, Long procesoId, String posicionCodigo) {
		String sql;
		for (Centro centro: lstCentros) {
			sql = "UPDATE encuesta_centro\n"+
                  "   SET porcentaje=:porcentaje\n" +
			      " WHERE posicion_codigo=:posicion_codigo\n" + 
			      "   AND proceso_id=:proceso_id\n" + 
			      "   AND centro_id=:centro_id";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("posicion_codigo", posicionCodigo);
			paramMap.put("proceso_id", procesoId);
	        paramMap.put("centro_id", centro.getId());
	        paramMap.put("porcentaje", centro.getPorcentaje());
	        plantilla.update(sql,paramMap);
		}
	}
}
