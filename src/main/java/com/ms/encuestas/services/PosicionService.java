package com.ms.encuestas.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.AreaRepository;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.PerfilRepository;
import com.ms.encuestas.repositories.PosicionRepository;

@Service
public class PosicionService implements PosicionServiceI {
	private Logger logger = LoggerFactory.getLogger(PosicionService.class);
	@Autowired
	private PosicionRepository posicionRepository;
	@Autowired
	private CentroRepository centroRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private PerfilRepository perfilRepository;

	public Long count() {
		return posicionRepository.count();
	}

	public List<Posicion> findAll() {
		return posicionRepository.findAll();
	}

	public Posicion findByCodigo(String codigo) {
		return posicionRepository.findByCodigo(codigo);
	}

	public Posicion findByCodigoWithAreaAndCentro(String codigo) {
		return posicionRepository.findByCodigoWithAreaAndCentro(codigo);
	}
	
	public Posicion findByCodigoWithArea(String codigo) {
		return posicionRepository.findByCodigoWithArea(codigo);
	}
	
	public Posicion findByCodigoWithCentro(String codigo) {
		return posicionRepository.findByCodigoWithCentro(codigo);
	}

	public Posicion save(Posicion posicion) {
		return null;
	}

	public void delete(Posicion posicion) {
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void processExcel(Long procesoId, InputStream file) {
		
	}
}
