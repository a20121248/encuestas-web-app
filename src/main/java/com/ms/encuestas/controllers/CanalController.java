package com.ms.encuestas.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CanalController {
    private static final Logger logger = LoggerFactory.getLogger(CanalController.class);
    
	@Autowired
	private ObjetoServiceI objetoService;
	
    @GetMapping("/sesion-prueba")
    public String index(HttpSession session) {
        return session.getId();
    }
	
	@GetMapping("/canales/cantidad")
	public Long count() {
		return objetoService.countCanales();
	}
	
	@GetMapping("/canales")
	public List<Objeto> index() {
		return objetoService.findAllCanales();
	}
	
    @PostMapping("/canales")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto create(@RequestBody Objeto canal) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        canal = objetoService.insertCanal(canal);
        logger.info(String.format("El usuario '%s' creó el canal con código '%s'.", user.getName(), canal.getCodigo()));
        return canal;
    }
  
    @PutMapping("/canales")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto update(@RequestBody Objeto canal) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Objeto canalBuscado = objetoService.findById(canal.getId());
        if (canalBuscado != null) {
            canal = objetoService.updateCanal(canal);
            logger.info(String.format("El usuario '%s' actualizó el canal con código '%s'.", user.getName(), canal.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo actualizar el canal con ID=%d porque no se encontró en la base de datos.", canal.getId()));
        }
        return canal;
    }
  
    @DeleteMapping("/canales/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto canalBuscado = objetoService.findById(id);
        if (canalBuscado != null) {
            objetoService.deleteById(id);
            logger.info(String.format("El usuario '%s' eliminó el canal con código '%s'.", user.getName(), canalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo eliminar el canal con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/canales/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softDelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto canalBuscado = objetoService.findById(id);
        if (canalBuscado != null) {
        	canalBuscado = objetoService.softDelete(canalBuscado);
            logger.info(String.format("El usuario '%s' deshabilitó el canal con código '%s'.", user.getName(), canalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el canal con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return canalBuscado;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/canales/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softUndelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto canalBuscado = objetoService.findById(id);
        if (canalBuscado != null) {
        	canalBuscado = objetoService.softUndelete(canalBuscado);
            logger.info(String.format("El usuario '%s' habilitó el canal con código '%s'.", user.getName(), canalBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el canal con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return canalBuscado;
    }
    
    @PostMapping("/canales/eliminar-todos")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        objetoService.deleteAllCanales();
        logger.info(String.format("El usuario '%s' eliminó todos los canales de la base de datos.", user.getName()));
    }
}
