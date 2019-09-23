package com.ms.encuestas.services;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.repositories.EncuestaRepository;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class EncuestaService implements EncuestaServiceI {
	private Logger logger = LoggerFactory.getLogger(EncuestaService.class);	
    @Autowired
    private EncuestaRepository encuestaRepository;
    @Autowired
    private PosicionRepository posicionRepository;
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
    
    public Justificacion getJustificacionDefault() {
		Justificacion justificacion = new Justificacion();
		justificacion.setId(new Long(0));
		justificacion.setNombre(null);
		justificacion.setDetalle(null);
		Date fecha = new Date();
		justificacion.setFechaCreacion(fecha);
		justificacion.setFechaActualizacion(fecha);
		return justificacion;
    }
    
    public String getObservacionesDefault() {
    	return null;
    }
    
    @Override
    public EncuestaEmpresa getEmpresa(Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	return encuestaRepository.getEncuestaEmpresa(procesoId, posicionCodigo, encuestaTipoId);
    }

	@Override
	public void saveEmpresa(EncuestaEmpresa encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstEmpresas(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}
	
	@Override
	public EncuestaCentro getCentro(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaCentro(empresaId, procesoId, posicionCodigo, encuestaTipoId, nivel, perfilId);
	}
	
	@Override
	public void saveCentro(EncuestaCentro encuesta, Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstCentros(encuesta.getLstItems(), empresaId, procesoId, posicionCodigo);
	}
	
	@Override
	public Resource downloadCentroExcel(Long empresaId, Long procesoId, String posicionCodigo, Long encuestaTipoId, int nivel, Long perfilId) {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Encuesta centros de costos.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("CENTROS");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaCentroList(empresaId, procesoId, posicionCodigo, encuestaTipoId, nivel, perfilId);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaCentroListEmpty();
        	excelService.crearCabecera(sh, 0, data);
            excelService.crearArchivo(wb, result);
    		return fileService.loadFileAsResource(result);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data);
		CellStyle dateStyle = wb.createCellStyle();
	    dateStyle.setDataFormat((short)14);
        for (Map<String, Object> fila: data) {
    		Row row = sh.createRow(rowNum++);
    		int colNum = 0;
    		row.createCell(colNum).setCellValue((String) fila.get("CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("NOMBRE"));sh.setColumnWidth(colNum++, 10000);
    		row.createCell(colNum).setCellValue(((BigDecimal) fila.get("NIVEL")).intValue());sh.setColumnWidth(colNum++, 2000);
    		row.createCell(colNum).setCellValue((String) fila.get("GRUPO"));sh.setColumnWidth(colNum++, 5000);
    		row.createCell(colNum).setCellValue(((BigDecimal) fila.get("PORCENTAJE")).doubleValue());sh.setColumnWidth(colNum++, 3000);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}

	@Override
	public EncuestaObjeto getLinea(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaLinea(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@Override
	public void saveLinea(EncuestaObjeto encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstLinea(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}

	@Override
	public EncuestaObjetoObjetos getLineaCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaLineaCanales(procesoId, posicionCodigo, encuestaTipoId, perfilId);
	}
	
	@Override
	public void saveLineaCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstLineaCanales(encuesta.getLstItems(), procesoId, posicionCodigo);		
	}

	@Override
	public EncuestaObjetoObjetos getProductoCanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long perfilId, Long lineaId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo))
			return null;
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaProductoCanales(procesoId, posicionCodigo, encuestaTipoId, perfilId, lineaId);
	}

	@Override
	public void saveProductoCanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstProductoCanales(encuesta.getLstItems(), procesoId, posicionCodigo, lineaId);
	}

	@Override
	public EncuestaObjetoObjetos getProductoSubcanales(Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId) {
		if (!posicionRepository.exists(procesoId, posicionCodigo)) {
			logger.error("La posición " + procesoId + " no existe");
			return null;
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId)) {
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);    		
    	}
    	return encuestaRepository.getEncuestaProductoSubcanales(procesoId, posicionCodigo, encuestaTipoId, lineaId, canalId);
	}

	@Override
	public void saveProductoSubcanales(EncuestaObjetoObjetos encuesta, Long procesoId, String posicionCodigo, Long encuestaTipoId, Long lineaId, Long canalId) {
		if (encuesta.getJustificacion() == null) {
			encuesta.setJustificacion(getJustificacionDefault());
			encuesta.setObservaciones(getObservacionesDefault());
		}
    	if (!encuestaRepository.hasEncuesta(procesoId, posicionCodigo, encuestaTipoId))
			encuestaRepository.insertEncuestaCabecera(getJustificacionDefault(), getObservacionesDefault(), procesoId, posicionCodigo, encuestaTipoId);
    	else
    		encuestaRepository.updateEncuestaCabecera(encuesta.getJustificacion(), encuesta.getObservaciones(), procesoId, posicionCodigo, encuestaTipoId);
		encuestaRepository.insertLstProductoSubcanales(encuesta.getLstItems(), procesoId, posicionCodigo, lineaId, canalId);
	}
}
