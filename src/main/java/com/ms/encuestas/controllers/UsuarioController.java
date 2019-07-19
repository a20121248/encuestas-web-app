package com.ms.encuestas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.models.custom.UsuarioDatos;
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

	@GetMapping("/usuarios/{codigo}")
	public Usuario show(@PathVariable String codigo) {
		return this.usuarioService.findByCodigo(codigo);
	}
	
	@GetMapping("/usuarios-con-posicion/{codigo}")
	public Usuario showWithPosicion(@PathVariable String codigo) {
		return this.usuarioService.findByCodigoWithPosicion(codigo);
	}
	
	@GetMapping("/usuarios-con-posicion-completo/{codigo}")
	public ResponseEntity<Usuario> showWithPosicionFull(@PathVariable String codigo) {
		Usuario usuario = this.usuarioService.findByCodigoWithPosicionFull(codigo);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		//return (ResponseEntity<Usuario>)this.usuarioService.findByCodigoWithPosicionFull(codigo);
	}
	
	@GetMapping("/usuario-datos/{codigo}")
	public UsuarioDatos showDatosEncuesta(@PathVariable String codigo) {
		Usuario usuario = this.usuarioService.findByCodigoWithPosicionFull(codigo);
		UsuarioDatos usuarioDatos = new UsuarioDatos();
		usuarioDatos.setCodigo(usuario.getCodigo());
		usuarioDatos.setNombreCompleto(usuario.getNombreCompleto());
		usuarioDatos.setPosicionCodigo(usuario.getPosicion().getCodigo());
		usuarioDatos.setPosicionNombre(usuario.getPosicion().getNombre());
		usuarioDatos.setAreaNombre(usuario.getPosicion().getArea().getNombre());
		usuarioDatos.setCentroCodigo(usuario.getPosicion().getCentro().getCodigo());
		usuarioDatos.setCentroNombre(usuario.getPosicion().getCentro().getNombre());
		usuarioDatos.setCentroNivel(usuario.getPosicion().getCentro().getNivel());
		return usuarioDatos;
	}
}
