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
import org.springframework.security.access.annotation.Secured;
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

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios/cantidad")
	public Long count(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de colaboradores en la base de datos.", user.getUsername()));
		return usuarioService.count();
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios")
	public List<Usuario> index(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó todos los colaboradores en la base de datos.", user.getUsername()));
		return usuarioService.findAll();
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/usuarios/eliminar-todos")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAll(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' eliminó a todos los colaboradores de la base de datos.", user.getUsername()));
		usuarioService.deleteAll();
	}
	
	@Secured({"ROLE_ADMIN"})
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

	@Secured({"ROLE_USER"})
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario create(Authentication authentication, @RequestBody Usuario usuario) {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' creó al colaborador con código '%s'.", user.getUsername(), usuario.getCodigo()));
		return usuarioService.insert(usuario);
	}

	@Secured({"ROLE_USER"})
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
	
	@Secured({"ROLE_USER"})
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
	
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/usuarios/{codigo}/soft-delete")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario softDelete(Authentication authentication, @PathVariable String codigo) {
        User user = (User) authentication.getPrincipal();   
        Usuario usuarioBuscado = usuarioService.findByCodigo(codigo);
        if (usuarioBuscado != null) {
        	usuarioBuscado = usuarioService.softDelete(usuarioBuscado);
            logger.info(String.format("El usuario '%s' deshabilitó la posición con código '%s'.", user.getUsername(), usuarioBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo deshabilitar la posición con código '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
        }
        return usuarioBuscado;
    }
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/usuarios/{codigo}/soft-undelete")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario softUndelete(Authentication authentication, @PathVariable String codigo) {
        User user = (User) authentication.getPrincipal();   
        Usuario usuarioBuscado = usuarioService.findByCodigo(codigo);
        if (usuarioBuscado != null) {
        	usuarioBuscado = usuarioService.softUndelete(usuarioBuscado);
            logger.info(String.format("El usuario '%s' habilitó al colaborador con matrícula '%s'.", user.getUsername(), usuarioBuscado.getCodigo()));
        } else {
            logger.error(String.format("El usuario '%s' no pudo habilitar al colaborador con matrícula '%s' porque no se encontró en la base de datos.", user.getUsername(), codigo));
        }
        return usuarioBuscado;
    }
	
	@Secured({"ROLE_USER"})
	@GetMapping("/procesos/{procesoId}/usuarios-dependientes/{posicionCodigo}")
	public List<Usuario> findUsuariosDependientes(Authentication authentication, @PathVariable Long procesoId, @PathVariable String posicionCodigo) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de usuarios dependientes.", user.getUsername()));
		return usuarioService.findUsuariosDependientes(procesoId, posicionCodigo);
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/procesos/{procesoId}/usuarios-dependientes-completados/{posicionCodigo}")
	public List<Usuario> findUsuariosDependientesCompletados(Authentication authentication, @PathVariable Long procesoId, @PathVariable String posicionCodigo) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de usuarios dependientes completados.", user.getUsername()));
		return usuarioService.findUsuariosDependientesCompletados(procesoId, posicionCodigo);
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/procesos/{procesoId}/usuarios-dependientes-replicar/{posicionCodigo}/{responsablePosicionCodigo}/{perfilId}")
	public List<Usuario> findUsuariosReplicar(Authentication authentication, @PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable String responsablePosicionCodigo, @PathVariable Long perfilId) throws Exception {
		User user = (User) authentication.getPrincipal();
		logger.info(String.format("El usuario '%s' consultó la cantidad de usuarios a replicar.", user.getUsername()));
		return usuarioService.findUsuariosDependientesReplicar(procesoId, posicionCodigo, responsablePosicionCodigo, perfilId);
	}

	@Secured({"ROLE_USER"})
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
	
	@Secured({"ROLE_USER"})
	@GetMapping("procesos/{procesoId}/usuarios/posicion/{posicionCodigo}")
	public Usuario showByPosicionCodigo(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		return this.usuarioService.findByPosicionCodigo(posicionCodigo, procesoId);
	}

	@Secured({"ROLE_USER"})
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

	@Secured({"ROLE_USER"})
	@GetMapping("/usuarios-con-posicion/{codigo}")
	public Usuario showWithPosicion(@PathVariable String codigo) {
		return this.usuarioService.findByCodigoWithPosicion(codigo);
	}
	
	@Secured({"ROLE_ADMIN"})
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
	
	@Secured({"ROLE_ADMIN"})
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