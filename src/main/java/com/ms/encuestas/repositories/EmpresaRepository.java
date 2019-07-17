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
public class EmpresaRepository {
	
	//private NamedParameterJdbcTemplate plantilla;
	@Autowired
	private NamedParameterJdbcTemplate plantilla;
	
	public List<Empresa> findAll() {
		String sql = "SELECT * FROM empresas";
		return plantilla.query(sql, new EmpresaMapper());
	}

	public Empresa findById(Long id) {
		String sql = "SELECT * FROM empresas WHERE id=:id";
        return plantilla.queryForObject(sql,
        		new MapSqlParameterSource("id", id),
        		new EmpresaMapper());
	}

	public int save(Empresa empresa) {
		String sql = "INSERT INTO empresas(nombre) VALUES (:nombre)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("nombre", empresa.getNombre());
        //paramMap.put("fecha_creacion", new Date());
	
		return plantilla.update(sql,paramMap);
	}

	public void delete(Empresa centro) {
		return;
	}	
}
