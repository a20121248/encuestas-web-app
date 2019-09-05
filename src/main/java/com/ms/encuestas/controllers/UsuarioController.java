package com.ms.encuestas.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	@Autowired
	private UsuarioServiceI usuarioService;	
	
	@PostMapping("usuarios/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("procesoId") Long procesoId) {
		Map<String, Object> response = new HashMap<>();
		if (!archivo.isEmpty()) {
			String nombreArchivo = archivo.getOriginalFilename();
			Path rutaArchivo = Paths.get("storage").resolve(nombreArchivo).toAbsolutePath();
			log.info(rutaArchivo.toString());
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir imagen del cliente.");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/usuarios/cantidad")
	public Long count() throws Exception {
		return usuarioService.count();
	}

	@GetMapping("/usuarios")
	public List<Usuario> index() throws Exception {
		return usuarioService.findAll();
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
	
	@PostMapping("/usuarios/cargar")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			log.info(String.format("Leyendo el archivo ", file.getOriginalFilename()));
			this.usuarioService.processExcel(file.getInputStream());
		} catch (IOException e) {
			log.info(String.format("Error leyendo el archivo: %s - %s", e.getMessage(), e.getCause()));
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