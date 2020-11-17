package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.services.ObjetoServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class LineaController {
    private static final Logger logger = LoggerFactory.getLogger(LineaController.class);
    
	@Autowired
	private ObjetoServiceI objetoService;
	
	@GetMapping("/lineas/cantidad")
	public Long count() {		
		return objetoService.countLineas();
	}
	
	@GetMapping("/lineas")
	public List<Objeto> index() {
		return objetoService.findAllLineas();
	}
	
    @PostMapping("/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto create(@RequestBody Objeto linea) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        linea = objetoService.insertLinea(linea);
        logger.info(String.format("El usuario '%s' creó el linea con código '%s'.", user.getName(), linea.getCodigo()));
        return linea;
    }
  
    @PutMapping("/lineas")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto update(@RequestBody Objeto linea) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Objeto lineaBuscada = objetoService.findById(linea.getId());
        if (lineaBuscada != null) {
            linea = objetoService.updateLinea(linea);
            logger.info(String.format("El usuario '%s' actualizó el linea con código '%s'.", user.getName(), linea.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo actualizar el linea con ID=%d porque no se encontró en la base de datos.", linea.getId()));
        }
        return linea;
    }
  
    @DeleteMapping("/lineas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto lineaBuscada = objetoService.findById(id);
        if (lineaBuscada != null) {
            objetoService.deleteById(id);
            logger.info(String.format("El usuario '%s' eliminó la línea con código '%s'.", user.getName(), lineaBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo eliminar la línea con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/lineas/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softDelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto lineaBuscada = objetoService.findById(id);
        if (lineaBuscada != null) {
        	lineaBuscada = objetoService.softDelete(lineaBuscada);
            logger.info(String.format("El usuario '%s' deshabilitó la línea con código '%s'.", user.getName(), lineaBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar la línea con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return lineaBuscada;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/lineas/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softUndelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto lineaBuscada = objetoService.findById(id);
        if (lineaBuscada != null) {
        	lineaBuscada = objetoService.softUndelete(lineaBuscada);
            logger.info(String.format("El usuario '%s' habilitó la línea con código '%s'.", user.getName(), lineaBuscada.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar la línea con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return lineaBuscada;
    }
    
    @PostMapping("/lineas/eliminar-todos")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        objetoService.deleteAllLineas();
        logger.info(String.format("El usuario '%s' eliminó todas las líneas de la base de datos.", user.getName()));
    }
}
