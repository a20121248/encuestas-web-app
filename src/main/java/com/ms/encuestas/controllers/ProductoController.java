package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
	
	@GetMapping("/productos/cantidad")
	public Long count() {		
		return objetoService.countProductos();
	}
	
	@GetMapping("/productos")
	public List<Objeto> index() {
		return objetoService.findAllProductos();
	}
	
    @PostMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto create(Authentication authentication, @RequestBody Objeto producto) {
        User user = (User) authentication.getPrincipal();
        producto = objetoService.insertProducto(producto);
        logger.info(String.format("El usuario '%s' creó el producto con código '%s'.", user.getUsername(), producto.getCodigo()));
        return producto;
    }
  
    @PutMapping("/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto update(Authentication authentication, @RequestBody Objeto producto) {
        User user = (User) authentication.getPrincipal();   
        Objeto productoBuscado = objetoService.findById(producto.getId());
        if (productoBuscado != null) {
            producto = objetoService.updateProducto(producto);
            logger.info(String.format("El usuario '%s' actualizó el producto con código '%s'.", user.getUsername(), producto.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo actualizar el producto con ID=%d porque no se encontró en la base de datos.", producto.getId()));
        }
        return producto;
    }
  
    @DeleteMapping("/productos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();   
        Objeto productoBuscado = objetoService.findById(id);
        if (productoBuscado != null) {
            objetoService.deleteById(id);
            logger.info(String.format("El usuario '%s' eliminó el producto con código '%s'.", user.getUsername(), productoBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo eliminar el producto con ID=%d porque no se encontró en la base de datos.", user.getUsername(), id));
        }
    }
    
    @PostMapping("/productos/eliminar-todos")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll(Authentication authentication) {
    	User user = (User) authentication.getPrincipal();
        objetoService.deleteAllProductos();
        logger.info(String.format("El usuario '%s' eliminó todos los productos de la base de datos.", user.getUsername()));
    }
}
