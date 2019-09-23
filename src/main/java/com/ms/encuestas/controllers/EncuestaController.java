package com.ms.encuestas.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
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
	public EncuestaEmpresa getEmpresa(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		Long encuestaTipoId = new Long(1);
		return encuestaService.getEmpresa(procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/empresas")
	@ResponseStatus(HttpStatus.CREATED)
	public void createEmpresa(Authentication authentication, @PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaEmpresa encuesta) {
		//User user = (User) authentication.getPrincipal();
		Long encuestaTipoId = new Long(1);
		this.encuestaService.saveEmpresa(encuesta, procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/eps")
	@Transactional(readOnly = true)
	public EncuestaCentro getEps(@PathVariable Long procesoId, @PathVariable String posicionCodigo) {
		Long encuestaTipoId = new Long(2);
		int nivel = 0;
		Long perfilId = new Long(1); // STAFF
		return encuestaService.getCentro(new Long(2), procesoId, posicionCodigo, encuestaTipoId, nivel, perfilId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/eps")
	@ResponseStatus(HttpStatus.CREATED)
	public void createEps(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaCentro encuesta) {
		this.encuestaService.saveCentro(encuesta, new Long(2), procesoId, posicionCodigo, new Long(2));
	}

	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/centro/{nivel}/{perfilId}")
	@Transactional(readOnly = true)
	public EncuestaCentro getCentro(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable int nivel, @PathVariable Long perfilId) {
		return encuestaService.getCentro(new Long(1), procesoId, posicionCodigo, new Long(3), nivel, perfilId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/centro")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCentro(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaCentro encuesta) {
		this.encuestaService.saveCentro(encuesta, new Long(1), procesoId, posicionCodigo, new Long(3));
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/centro/{nivel}/{perfilId}/descargar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> downloadCentroExcel(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable int nivel, @PathVariable Long perfilId) {
		Resource resource = this.encuestaService.downloadCentroExcel(new Long(1), procesoId, posicionCodigo, new Long(3), nivel, perfilId);
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea/{perfilId}")
	@Transactional(readOnly = true)
	public EncuestaObjeto getLinea(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long perfilId) {
		Long encuestaTipoId = new Long(7); // 7: Linea
		return encuestaService.getLinea(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea")
	@ResponseStatus(HttpStatus.CREATED) 
	public void createLinea(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaObjeto encuesta) {
		Long encuestaTipoId = new Long(7); // 7: Linea
		this.encuestaService.saveLinea(encuesta, procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea-canal/{perfilId}")
	@Transactional(readOnly = true)
	public EncuestaObjetoObjetos getLineaCanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long perfilId) {
		Long encuestaTipoId = new Long(4); // 4: Linea y Canal
		return encuestaService.getLineaCanales(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/linea-canal")
	@ResponseStatus(HttpStatus.CREATED)
	public void createLineaCanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @RequestBody EncuestaObjetoObjetos encuesta) {
		Long encuestaTipoId = new Long(4); // 4: Linea y Canal
		this.encuestaService.saveLineaCanales(encuesta, procesoId, posicionCodigo, encuestaTipoId);
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-subcanal/{lineaId}/{canalId}")
	@Transactional(readOnly = true)
	public EncuestaObjetoObjetos getProductoSubcanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long lineaId, @PathVariable Long canalId) {
		Long encuestaTipoId = new Long(5); // 5: Producto y Subcanal
		return encuestaService.getProductoSubcanales(procesoId, posicionCodigo, encuestaTipoId, lineaId, canalId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-subcanal/{lineaId}/{canalId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductoSubcanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long lineaId, @PathVariable Long canalId, @RequestBody EncuestaObjetoObjetos encuesta) {
		Long encuestaTipoId = new Long(5); // 5: Producto y Subcanal
		this.encuestaService.saveProductoSubcanales(encuesta, procesoId, posicionCodigo, encuestaTipoId, lineaId, canalId);
	}
	
	@GetMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-canal/{perfilId}/{lineaId}")
	@Transactional(readOnly = true)
	public EncuestaObjetoObjetos getProductoCanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long perfilId, @PathVariable Long lineaId) {
		Long encuestaTipoId = new Long(6); // 6: Producto y Canal
		return encuestaService.getProductoCanales(procesoId, posicionCodigo, encuestaTipoId, perfilId, lineaId);
	}
	
	@PostMapping("/procesos/{procesoId}/colaboradores/{posicionCodigo}/encuesta/producto-canal/{lineaId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProductoCanales(@PathVariable Long procesoId, @PathVariable String posicionCodigo, @PathVariable Long lineaId, @RequestBody EncuestaObjetoObjetos encuesta) {
		Long encuestaTipoId = new Long(6); // 6: Producto y Canal
		this.encuestaService.saveProductoCanales(encuesta, procesoId, posicionCodigo, encuestaTipoId, lineaId);
	}
}
