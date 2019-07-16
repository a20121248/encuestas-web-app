package com.ms.encuestas.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ms.encuestas.models.Empresa;

@Repository
public class EmpresaRepository {
	@Autowired
	private JdbcTemplate plantilla;
	
	public List<Empresa> findAll() {
		String queryStr = String.format("SELECT * FROM empresas");
	    return plantilla.query(queryStr, new EmpresaMapper());
	}

	public Empresa findById(Long id) {
		String queryStr = plantilla.queryForObject( 
		        "select last_name from t_actor where id = ?", 
		        String.class, 1212L); 

		
		String queryStr = String.format("" +
	            "SELECT *\n" +
	            "  FROM empresas\n" +
	            " WHERE id=%s\n",
	            id);
		return plantilla.queryForObject(queryStr, new EmpresaMapper());
	}

	public Empresa findByCodigo(String codigo) {		
		String queryStr = String.format("" +
	            "SELECT *\n" +
	            "  FROM centros\n" +
	            " WHERE codigo='%s'\n",
	            codigo);
		return plantilla.queryForObject(queryStr, new EmpresaMapper());
	}

	public Empresa save(Empresa centro) {
		return null;
	}

	public void delete(Empresa centro) {
		return;
	}	
}
