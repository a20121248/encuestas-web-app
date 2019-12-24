package com.ms.encuestas.services;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import com.ms.encuestas.models.EncuestaCentro;
import com.ms.encuestas.models.EncuestaEmpresa;
import com.ms.encuestas.models.EncuestaObjeto;
import com.ms.encuestas.models.EncuestaObjetoObjetos;
import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.EncuestaRepository;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

@Service
public class EncuestaService implements EncuestaServiceI {
	private Logger logger = LoggerFactory.getLogger(EncuestaService.class);
	
    @Autowired
    private EncuestaRepository encuestaRepository;
    @Autowired
    private PosicionRepository posicionRepository;
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
	public Resource downloadEmpresaExcel(Long procesoId, String posicionCodigo) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet("EMPRESAS");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaEmpresaList(procesoId, posicionCodigo);
        List<Integer> widths = Arrays.asList(10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaEmpresaListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		excelService.createCell(row, colNum++, (String) fila.get("NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
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
	public Resource downloadCentroExcel(Long empresaId, Long procesoId, String posicionCodigo, int nivel, Long perfilId) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet(empresaId.equals(new Long(1)) ? "CENTROS" : "LINEAS EPS");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaCentroList(empresaId, procesoId, posicionCodigo, nivel, perfilId);
        List<Integer> widths = empresaId.equals(new Long(1)) ? Arrays.asList(3000, 10000, 2000, 5000, 4000) : Arrays.asList(10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaCentroListEmpty(empresaId);
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		if (empresaId.equals(new Long(1))) {
    			excelService.createCell(row, colNum++, (String) fila.get("CODIGO"), bordeEstilo);
    		}
    		excelService.createCell(row, colNum++, (String) fila.get("NOMBRE"), bordeEstilo);
    		if (empresaId.equals(new Long(1))) {
    			excelService.createCell(row, colNum++, ((BigDecimal) fila.get("NIVEL")).intValue(), bordeEstilo);
    			excelService.createCell(row, colNum++, (String) fila.get("GRUPO"), bordeEstilo);
    		}
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
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
	public Resource downloadLineaExcel(Long procesoId, String posicionCodigo, Long perfilId) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet("LINEAS");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaLineaList(procesoId, posicionCodigo, perfilId);
        List<Integer> widths = Arrays.asList(3000, 10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaLineaListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
			excelService.createCell(row, colNum++, (String) fila.get("CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
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
	public Resource downloadLineaCanalesExcel(Long procesoId, String posicionCodigo, Long perfilId) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet("LINEA CANAL");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaLineaCanalesList(procesoId, posicionCodigo, perfilId);
        List<Integer> widths = Arrays.asList(3000, 10000, 4000, 3000, 10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaLineaCanalesListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
			excelService.createCell(row, colNum++, (String) fila.get("LINEA_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("LINEA_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("LINEA_PORCENTAJE")).doubleValue(), bordeEstilo);
			excelService.createCell(row, colNum++, (String) fila.get("CANAL_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CANAL_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("CANAL_PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
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
	public Resource downloadProductoCanalesExcel(Long procesoId, String posicionCodigo, Long perfilId, Long lineaId) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet("PRODUCTO CANAL");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaProductoCanalesList(procesoId, posicionCodigo, perfilId, lineaId);
        List<Integer> widths = Arrays.asList(3000, 10000, 3000, 10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaProductoCanalesListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
			excelService.createCell(row, colNum++, (String) fila.get("PRODUCTO_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PRODUCTO_NOMBRE"), bordeEstilo);
			excelService.createCell(row, colNum++, (String) fila.get("CANAL_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CANAL_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
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
	
	@Override
	public Resource downloadProductoSubcanalesExcel(Long procesoId, String posicionCodigo, Long lineaId, Long canalId) {		
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        SXSSFSheet sh = wb.createSheet("PRODUCTO SUBCANAL");

        List<Map<String,Object>> data = encuestaRepository.getEncuestaProductoSubcanalesList(procesoId, posicionCodigo, lineaId, canalId);
        List<Integer> widths = Arrays.asList(3000, 10000, 3000, 10000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = encuestaRepository.getEncuestaProductoSubcanalesListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
			excelService.createCell(row, colNum++, (String) fila.get("PRODUCTO_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PRODUCTO_NOMBRE"), bordeEstilo);
			excelService.createCell(row, colNum++, (String) fila.get("SUBCANAL_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("SUBCANAL_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PORCENTAJE")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void replicarEncuestas(Long procesoId, String posicionOrigenCodigo, List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			String posicionDestinoCodigo = usuario.getPosicion().getCodigo();
			encuestaRepository.replicarEncuestaCabecera(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaEmpresa(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaCentro(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaLinea(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaLineaCanal(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaProductoCanal(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			encuestaRepository.replicarEncuestaProductoSubcanal(procesoId, posicionOrigenCodigo, posicionDestinoCodigo);
			logger.info(String.format("Se replicó la encuesta de la posición con código %s a la posición con código %s.", posicionOrigenCodigo, posicionDestinoCodigo));
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public void eliminarEncuestas(Long procesoId, String posicionCodigo) {
		encuestaRepository.eliminarEncuestaCabecera(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaEmpresa(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaCentro(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaLinea(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaLineaCanal(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaProductoCanal(procesoId, posicionCodigo);
		encuestaRepository.eliminarEncuestaProductoSubcanal(procesoId, posicionCodigo);
	}
}
