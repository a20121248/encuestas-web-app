package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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
public class SubcanalController {
    private static final Logger logger = LoggerFactory.getLogger(SubcanalController.class);
    
	@Autowired
	private ObjetoServiceI objetoService;
	
	@GetMapping("/subcanales/cantidad")
	public Long count() {		
		return objetoService.countSubcanales();
	}
	
	@GetMapping("/subcanales")
	public List<Objeto> index() {
		return objetoService.findAllSubcanales();
	}
	
    @PostMapping("/subcanales")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto create(@RequestBody Objeto subcanal) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        subcanal = objetoService.insertSubcanal(subcanal);
        logger.info(String.format("El usuario '%s' creó el subcanal con código '%s'.", user.getName(), subcanal.getCodigo()));
        return subcanal;
    }
  
    @PutMapping("/subcanales")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto update(@RequestBody Objeto subcanal) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto subcanalBuscado = objetoService.findById(subcanal.getId());
        if (subcanalBuscado != null) {
            subcanal = objetoService.updateSubcanal(subcanal);
            logger.info(String.format("El usuario '%s' actualizó el subcanal con código '%s'.", user.getName(), subcanal.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo actualizar el subcanal con ID=%d porque no se encontró en la base de datos.", subcanal.getId()));
        }
        return subcanal;
    }
  
    @DeleteMapping("/subcanales/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto subcanalBuscado = objetoService.findById(id);
        if (subcanalBuscado != null) {
            objetoService.deleteById(id);
            logger.info(String.format("El usuario '%s' eliminó el subcanal con código '%s'.", user.getName(), subcanalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo eliminar el subcanal con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
    }
    
    @PutMapping("/subcanales/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softDelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto subcanalBuscado = objetoService.findById(id);
        if (subcanalBuscado != null) {
        	subcanalBuscado = objetoService.softDelete(subcanalBuscado);
            logger.info(String.format("El usuario '%s' deshabilitó el producto con código '%s'.", user.getName(), subcanalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el producto con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return subcanalBuscado;
    }
    
    @PutMapping("/subcanales/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softUndelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto subcanalBuscado = objetoService.findById(id);
        if (subcanalBuscado != null) {
        	subcanalBuscado = objetoService.softUndelete(subcanalBuscado);
            logger.info(String.format("El usuario '%s' habilitó el subcanal con código '%s'.", user.getName(), subcanalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el subcanal con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return subcanalBuscado;
    }
    
    @PostMapping("/subcanales/eliminar-todos")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(Authentication authentication) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        objetoService.deleteAllSubcanales();
        logger.info(String.format("El usuario '%s' eliminó todos los subcanales de la base de datos.", user.getName()));
    }
}
