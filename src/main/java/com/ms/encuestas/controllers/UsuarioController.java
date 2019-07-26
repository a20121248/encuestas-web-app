package com.ms.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.services.UsuarioServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioController {
	@Autowired
	private UsuarioServiceI usuarioService;

	@GetMapping("/usuarios/cantidad")
	public Long count() throws Exception {
		return usuarioService.count();
	}

	@GetMapping("/usuarios")
	public List<Usuario> index() throws Exception {
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuarios-dependientes")
	public List<Usuario> findUsuariosDependientes() throws Exception {
		Long procesoId = new Long(2);
		String posicionCodigo = "208829";
		return usuarioService.findUsuariosDependientesByCodigo(procesoId, posicionCodigo);
	}

	@GetMapping("/usuarios/{codigo}")
	public ResponseEntity<?> show(@PathVariable String codigo) {
		Usuario usuario = null;
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
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@PathVariable String codigo) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuario = this.usuarioService.findByCodigo(codigo);
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

	@GetMapping("/usuarios-con-posicion-completo/{codigo}")
	public ResponseEntity<Usuario> showWithPosicionFull(@PathVariable String codigo) {
		Usuario usuario = this.usuarioService.findByCodigoWithPosicionFull(codigo);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		// return
		// (ResponseEntity<Usuario>)this.usuarioService.findByCodigoWithPosicionFull(codigo);
	}

	@GetMapping("/usuario-datos/{codigo}")
	public ResponseEntity<?> showDatosEncuesta(@PathVariable String codigo) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = this.usuarioService.findByCodigoWithPosicionFull(codigo);
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
		response.put("posicionCodigo",usuario.getPosicion().getCodigo());
		response.put("posicionNombre",usuario.getPosicion().getNombre());
		response.put("areaNombre",usuario.getPosicion().getArea().getNombre());
		response.put("centroCodigo",usuario.getPosicion().getCentro().getCodigo());
		response.put("centroNombre",usuario.getPosicion().getCentro().getNombre());
		response.put("centroNivel",usuario.getPosicion().getCentro().getNivel());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
