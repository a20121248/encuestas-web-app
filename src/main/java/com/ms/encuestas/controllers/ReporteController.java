package com.ms.encuestas.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaCanal;
import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaLinea;
import com.ms.encuestas.models.EncuestaLineaCanal;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.EncuestaProductoCanal;
import com.ms.encuestas.models.EncuestaProductoSubcanal;
import com.ms.encuestas.properties.FileProperties;
import com.ms.encuestas.services.EncuestaServiceI;
import com.ms.encuestas.services.FileServiceI;
import com.ms.encuestas.services.ReporteServiceI;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ReporteController {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@Autowired
	private ReporteServiceI reporteService;
	
	private final Logger log = LoggerFactory.getLogger(ReporteController.class);
	
	@RequestMapping(value = "/convertFlatFileToExcel.do", method = RequestMethod.POST)
	public HttpEntity<byte[]> convertFlatFileToExcel() throws IOException {
	        ByteArrayOutputStream archivo = new ByteArrayOutputStream();
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        workbook.write(archivo);
	        if(null!=workbook && null!=archivo) {
	            workbook.close();
	                        archivo.close();
	        }
	    byte[] documentContent = archivo.toByteArray();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	    headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"myexcelfile.xls\"");
	    headers.setContentLength(documentContent.length);
	    return new ResponseEntity<byte[]>(documentContent, headers, HttpStatus.OK);
	}


	@GetMapping("/procesos/{procesoId}/reportes/control")
	@Transactional(readOnly = true)
	public ResponseEntity<?> getControl(@PathVariable Long procesoId, HttpServletRequest request) {
		System.out.println("exitos"+ procesoId);

		Resource resource = reporteService.generarReporteControl(procesoId);
	

        // Try to determine file's content type
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al subir imagen del cliente.");
                
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	/*@GetMapping("/procesos/{procesoId}/reportes/control")
	@Transactional(readOnly = true)
	public ResponseEntity<Resource> getControl(@PathVariable Long procesoId, HttpServletRequest request) {
		System.out.println("exitos"+ procesoId);

		// Load file as Resource
		Resource resource = reporteService.generarReporteControl(procesoId);
		if(resource==null) {
			System.out.println("nulo");
		} else {
			System.out.println("ok");
		}
		

        // Try to determine file's content type
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al subir imagen del cliente.");
                
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}*/
	
	

}
