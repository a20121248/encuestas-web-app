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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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
	public Resource generarReporteControl(Long procesoId) {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Reporte de control.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = reporteRepository.reporteControl(procesoId);        
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
