package com.ms.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.services.ObjetoServiceI;
import com.ms.encuestas.services.ProcesoServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/lineas")
public class LineaController {
    private static final Logger logger = LoggerFactory.getLogger(LineaController.class);
    private final Long objetoTipoId = new Long(1);
    
	@Autowired
	private ObjetoServiceI objetoService;
	
	@GetMapping("/cantidad")
	public Long count() {		
		return objetoService.count(objetoTipoId);
	}
	
	@GetMapping("")
	public List<Objeto> index() {
		return objetoService.findAll(objetoTipoId);
	}
}
