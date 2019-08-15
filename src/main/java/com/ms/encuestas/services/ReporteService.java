package com.ms.encuestas.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Filtro;
import com.ms.encuestas.repositories.ReporteRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class ReporteService implements ReporteServiceI {
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private ReporteRepository reporteRepository;
	
	@Override
	public Resource generarReporteControl(Filtro filtro) {
		Long procesoId = filtro.getProceso().getId();
		
		List<Area> areas = filtro.getAreas();
		if (areas != null) areas = (areas.isEmpty() || areas.get(0).getId() == null) ? null : areas;			
		
		List<Centro> centros = filtro.getCentros();
		if (centros != null) centros = (centros.isEmpty() || centros.get(0).getId() == null) ? null : centros;
		
		List<Integer> estadoIds = Arrays.asList(0,1,2);
		System.out.println(estadoIds.size());
		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Reporte de control.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = reporteRepository.reporteControl(procesoId, areas, centros, estadoIds);        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data);
		CellStyle dateStyle = wb.createCellStyle();
	    dateStyle.setDataFormat((short)14);
        for (Map<String, Object> fila: data) {
    		Row row = sh.createRow(rowNum++);
    		int colNum = 0;    		
    		//row.createCell(colNum++).setCellValue(((BigDecimal) fila.get("PROCESO_ID")).longValue());
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_DESCARGA"));
    		sh.setColumnWidth(colNum, 3000);
            row.getCell(colNum++).setCellStyle(dateStyle);
    		row.createCell(colNum).setCellValue((String) fila.get("PROCESO"));
    		sh.setColumnWidth(colNum++, 4000);
    		row.createCell(colNum++).setCellValue((String) fila.get("MATRICULA"));
    		row.createCell(colNum++).setCellValue((String) fila.get("COLABORADOR"));
    		row.createCell(colNum++).setCellValue((String) fila.get("NRO_POSICION"));
    		row.createCell(colNum++).setCellValue((String) fila.get("POSICION"));
        	row.createCell(colNum++).setCellValue((String) fila.get("AREA"));
        	row.createCell(colNum++).setCellValue((String) fila.get("CECO_CODIGO"));
        	row.createCell(colNum).setCellValue((String) fila.get("CECO_NOMBRE"));
        	sh.setColumnWidth(colNum++, 6000);
        	row.createCell(colNum++).setCellValue((String) fila.get("PERFIL"));
        	row.createCell(colNum++).setCellValue((String) fila.get("PERFIL_TIPO"));
        	row.createCell(colNum++).setCellValue((String) fila.get("ETAPA_1"));
        	row.createCell(colNum++).setCellValue((String) fila.get("ETAPA_2"));
        	row.createCell(colNum++).setCellValue((String) fila.get("ETAPA_3"));
        	row.createCell(colNum++).setCellValue((String) fila.get("ETAPA_4"));
        	row.createCell(colNum++).setCellValue((String) fila.get("ESTADO_GLOBAL"));
        	row.createCell(colNum).setCellValue((Date) fila.get("ULTIMA_MODIFICACION"));
        	sh.setColumnWidth(colNum, 3000);
        	row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}

	@Override
	public Resource generarReporteEmpresas(Filtro filtro) {
		Long procesoId = filtro.getProceso().getId();
		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Reporte de control.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = null;//reporteRepository.reporteControl(procesoId);        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data);
        for (Map<String, Object> fila: data) {
    		Row row = sh.createRow(rowNum++);
    		int colNum = 0;    		
    		row.createCell(colNum++).setCellValue((Date) fila.get("FECHA_DESCARGA"));
    		row.createCell(colNum++).setCellValue(((BigDecimal) fila.get("PROCESO_ID")).longValue());
    		row.createCell(colNum++).setCellValue((String) fila.get("PROCESO"));
    		row.createCell(colNum++).setCellValue((String) fila.get("MATRICULA"));
    		row.createCell(colNum++).setCellValue((String) fila.get("COLABORADOR"));
    		row.createCell(colNum++).setCellValue((String) fila.get("NRO_POSICION"));
    		row.createCell(colNum++).setCellValue((String) fila.get("POSICION"));
        	row.createCell(colNum++).setCellValue((String) fila.get("AREA"));
        	row.createCell(colNum++).setCellValue((String) fila.get("CENTRO_CODIGO"));
        	row.createCell(colNum++).setCellValue((String) fila.get("CENTRO_NOMBRE"));
        	row.createCell(colNum++).setCellValue((String) fila.get("PERFIL"));
        }        
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}

	@Override
	public Resource generarReporteConsolidado(Filtro filtro) {
		Long procesoId = filtro.getProceso().getId();
		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Reporte de control.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = null;//reporteRepository.reporteControl(procesoId);        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data);
        for (Map<String, Object> fila: data) {
    		Row row = sh.createRow(rowNum++);
    		int colNum = 0;    		
    		row.createCell(colNum++).setCellValue((Date) fila.get("FECHA_DESCARGA"));
    		row.createCell(colNum++).setCellValue(((BigDecimal) fila.get("PROCESO_ID")).longValue());
    		row.createCell(colNum++).setCellValue((String) fila.get("PROCESO"));
    		row.createCell(colNum++).setCellValue((String) fila.get("MATRICULA"));
    		row.createCell(colNum++).setCellValue((String) fila.get("COLABORADOR"));
    		row.createCell(colNum++).setCellValue((String) fila.get("NRO_POSICION"));
    		row.createCell(colNum++).setCellValue((String) fila.get("POSICION"));
        	row.createCell(colNum++).setCellValue((String) fila.get("AREA"));
        	row.createCell(colNum++).setCellValue((String) fila.get("CENTRO_CODIGO"));
        	row.createCell(colNum++).setCellValue((String) fila.get("CENTRO_NOMBRE"));
        	row.createCell(colNum++).setCellValue((String) fila.get("PERFIL"));
        }        
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}	
}
