package com.ms.encuestas.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Empresa;
import com.ms.encuestas.models.Encuesta;
import com.ms.encuestas.services.EncuestaServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EncuestaController {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

	@Autowired
	private EncuestaServiceI encuestaService;
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/empresas")
	@Transactional(readOnly = true)
	public Encuesta getEncuestaEmpresa(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		Long encuestaTipoId = new Long(1);
		return encuestaService.getEncuestaEmpresa(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(Authentication authentication, @PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody Encuesta encuesta) {
		//User user = (User) authentication.getPrincipal();
		//System.out.println("VAMOSSSSS POST " + user.getUsername());
		Long encuestaTipoId = new Long(1);
		this.encuestaService.saveEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		this.encuestaService.saveEncuestaEmpresaDetalle(encuesta.getLstItems(), procesoId, posicionCodigo);
	}
}
