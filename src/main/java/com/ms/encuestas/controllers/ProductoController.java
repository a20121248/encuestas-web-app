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
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    
	@Autowired
	private ObjetoServiceI objetoService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/productos/cantidad")
	public Long count() {		
		return objetoService.countProductos();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/productos")
	public List<Objeto> index() {
		return objetoService.findAllProductos();
	}
	
	@Secured("ROLE_ADMIN")
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto create(@RequestBody Objeto producto) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
        producto = objetoService.insertProducto(producto);
        logger.info(String.format("El usuario '%s' creó el producto con código '%s'.", user.getName(), producto.getCodigo()));
        return producto;
    }
  
    @Secured("ROLE_ADMIN")
    @PutMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto update(@RequestBody Objeto producto) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto productoBuscado = objetoService.findById(producto.getId());
        if (productoBuscado != null) {
            producto = objetoService.updateProducto(producto);
            logger.info(String.format("El usuario '%s' actualizó el producto con código '%s'.", user.getName(), producto.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo actualizar el producto con ID=%d porque no se encontró en la base de datos.", producto.getId()));
        }
        return producto;
    }
  
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();   
        Objeto productoBuscado = objetoService.findById(id);
        if (productoBuscado != null) {
            objetoService.deleteById(id);
            logger.info(String.format("El usuario '%s' eliminó el producto con código '%s'.", user.getName(), productoBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo eliminar el producto con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/productos/{id}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softDelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Objeto productoBuscado = objetoService.findById(id);
        if (productoBuscado != null) {
        	productoBuscado = objetoService.softDelete(productoBuscado);
            logger.info(String.format("El usuario '%s' deshabilitó el producto con código '%s'.", user.getName(), productoBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar el producto con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return productoBuscado;
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/productos/{id}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto softUndelete(@PathVariable Long id) {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Objeto productoBuscado = objetoService.findById(id);
        if (productoBuscado != null) {
        	productoBuscado = objetoService.softUndelete(productoBuscado);
            logger.info(String.format("El usuario '%s' habilitó el producto con código '%s'.", user.getName(), productoBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar el producto con ID=%d porque no se encontró en la base de datos.", user.getName(), id));
        }
        return productoBuscado;
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping("/productos/eliminar-todos")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
    	Authentication user = SecurityContextHolder.getContext().getAuthentication();
        objetoService.deleteAllProductos();
        logger.info(String.format("El usuario '%s' eliminó todos los productos de la base de datos.", user.getName()));
    }
}
