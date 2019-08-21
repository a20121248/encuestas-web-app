package com.ms.encuestas.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;

public class EncuestaLineaCanalesExtractor implements ResultSetExtractor<List<ObjetoObjetos>> {
	private Logger logger = LoggerFactory.getLogger(EncuestaLineaCanalesExtractor.class);
	
	@Override
	public List<ObjetoObjetos> extractData(ResultSet rs) throws SQLException, DataAccessException {
	  List<ObjetoObjetos> list = new ArrayList<ObjetoObjetos>();
	  Long lineaIdAnt = new Long(-1);
	  Long lineaIdAct;
	  ObjetoObjetos lineaCanales = null;
	  Objeto linea, canal;
	  while(rs.next()) {
		  lineaIdAct = rs.getLong("linea_id");
		  if (!lineaIdAct.equals(lineaIdAnt)) {
			  lineaCanales = new ObjetoObjetos();
			  lineaCanales.setLstObjetos(new ArrayList<Objeto>());
			  
			  linea = new Objeto();
			  linea.setId(rs.getLong("linea_id"));
			  linea.setCodigo(rs.getString("linea_codigo"));
			  linea.setNombre(rs.getString("linea_nombre"));
			  linea.setPorcentaje(rs.getDouble("linea_porcentaje"));			  
			  lineaCanales.setObjeto(linea);
			  
			  list.add(lineaCanales);
		  }
		  
		  canal = new Objeto();
		  canal.setId(rs.getLong("canal_id"));
		  canal.setCodigo(rs.getString("canal_codigo"));
		  canal.setNombre(rs.getString("canal_nombre"));
		  canal.setPorcentaje(rs.getDouble("canal_porcentaje"));
		  lineaCanales.getLstObjetos().add(canal);
		  
		  lineaIdAnt = lineaIdAct;
	  } 
	  return list;
	}
}
