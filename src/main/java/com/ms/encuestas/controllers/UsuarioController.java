package com.ms.encuestas.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioController {
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	@Autowired
	private UsuarioServiceI usuarioService;	

	@GetMapping("/usuarios/cantidad")
	public Long count(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de colaboradores en la base de datos.", user.getUsername()));
		return usuarioService.count();
	}

	@GetMapping("/usuarios")
	public List<Usuario> index(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó todos los colaboradores en la base de datos.", user.getUsername()));
		return usuarioService.findAll();
	}
	
	@PostMapping("/usuarios/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' eliminó a todos los colaboradores de la base de datos.", user.getUsername()));
		usuarioService.deleteAll();
	}
	
	@GetMapping("/usuarios/{codigo}")
	public ResponseEntity<?> show(Authentication authentication, @PathVariable String codigo) {
		User user = (User) authentication.getPrincipal();
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info(String.format("El usuario '%s' buscó al colaborador con código '%s'.", user.getUsername(), codigo));
			usuario = usuarioService.findByCodigo(codigo);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El colaborador con código '%s' no existe en la base de datos.", codigo));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario create(Authentication authentication, @RequestBody Usuario usuario) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' creó al colaborador con código '%s'.", user.getUsername(), usuario.getCodigo()));
		return usuarioService.insert(usuario);
	}

	@PutMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario update(Authentication authentication, @RequestBody Usuario usuario) {
		User user = (User) authentication.getPrincipal();		
		Usuario usuarioBuscado = usuarioService.findByCodigo(usuario.getCodigo());
		if (usuarioBuscado != null) {
			logger.info(String.format("El usuario '%s' actualizó al colaborador con código '%s'.", user.getUsername(), usuario.getCodigo()));
			return usuarioService.update(usuario);
		} else {
			logger.error(String.format("El usuario '%s' no pudo actualizar al colaborador con código '%s' porque no se encontró en la base de datos.", user.getUsername(), usuario.getCodigo()));
			return null;
		}
	}

	@DeleteMapping("/usuarios/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Authentication authentication, @PathVariable String codigo) {
		User user = (User) authentication.getPrincipal();		
		Usuario usuarioBuscado = usuarioService.findByCodigo(codigo);
		if (usuarioBuscado != null) {
			logger.info(String.format("El usuario '%s' eliminó al colaborador con código '%s'.", user.getUsername(), usuarioBuscado.getCodigo()));
			usuarioService.deleteByCodigo(codigo);
		} else {
			logger.error(String.format("El usuario '%s' no pudo eliminar al colaborador con código '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
		}
	}
	
	@GetMapping("/procesos/{procesoId}/usuarios-dependientes/{posicionCodigo}")
	public List<Usuario> findUsuariosDependientes(@PathVariable Long procesoId, @PathVariable String posicionCodigo) throws Exception {
		return usuarioService.findUsuariosDependientesByCodigo(procesoId, posicionCodigo);
	}

	@GetMapping("/procesos/{procesoId}/usuarios/{codigo}")
	public Usuario show(@PathVariable Long procesoId, @PathVariable String codigo) {
		return this.usuarioService.findByCodigoAndProceso(codigo, procesoId);
		/*Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = this.usuarioService.findByCodigo(codigo);
		} catch (EmptyResultDataAccessException er) {
			response.put("mensaje", String.format("El usuario %s no existe en la base de datos.", codigo));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", String.format("%s. %s", dae.getMessage(), dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		response.put("codigo",usuario.getCodigo());
		response.put("nombreCompleto",usuario.getNombreCompleto());
		response.put("fechaCompleto",usuario.getFechaCreacion());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);*/
	}
	
	@GetMapping("procesos/{procesoId}/usuarios/posicion/{posicionCodigo}")
	public Usuario showByPosicionCodigo(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return this.usuarioService.findByPosicionCodigo(posicionCodigo, procesoId);
	}

	@PostMapping("procesos/{procesoId}/usuarios")
	public ResponseEntity<?> create(@PathVariable Long procesoId, @PathVariable String codigo) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuario = this.usuarioService.findByCodigoAndProceso(codigo, procesoId);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado.");
		response.put("usuario", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/usuarios-con-posicion/{codigo}")
	public Usuario showWithPosicion(@PathVariable String codigo) {
		return this.usuarioService.findByCodigoWithPosicion(codigo);
	}
	
	@PostMapping("/usuarios/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			logger.info(String.format("Leyendo el archivo '%s'.", file.getOriginalFilename()));
			this.usuarioService.processExcel(file.getInputStream());
		} catch (IOException e) {
			logger.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
		}
	}
	
	@PostMapping("/usuarios/descargar")
	@Transactional(readOnly = true)
	public ResponseEntity<?> downloadPerfiles() {
		Resource resource = usuarioService.downloadExcel();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}	
}