package com.ms.encuestas.services.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ooxml.POIXMLProperties.CoreProperties;
import org.apache.poi.ooxml.POIXMLProperties.ExtendedProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcelService implements ExcelServiceI {
	private Logger logger = LoggerFactory.getLogger(ExcelService.class);
	private DataFormatter dataFormatter;
	
	@Autowired
	public ExcelService() {
		dataFormatter = new DataFormatter();
	}
	
	@Override
	@Transactional(readOnly = true)
	public int crearArchivo(SXSSFWorkbook wb, String rutaArchivo) {		
        try {
	        FileOutputStream out = new FileOutputStream(rutaArchivo);
	        wb.write(out);
	        out.close();
	        wb.dispose();
	        return 1;
        } catch (IOException ex) {
        	logger.info(ex.getMessage());
        	return 0;
        }
	}
	
	@Override
	@Transactional(readOnly = true)
    public int crearCabecera(Sheet sh, int rowNum, List<Map<String,Object>> data) {
		Map<String,Object> fila = data.get(0);
        Row row = sh.createRow(rowNum);
        int colNum = 0;        
        for (Entry<String, Object> campo: fila.entrySet()) {
        	Cell cell = row.createCell(colNum++);
        	cell.setCellValue(campo.getKey());
    	}
        return 1;
    }
	
	@Override
	@Transactional(readOnly = true)
	public SXSSFWorkbook crearLibro() {
		SXSSFWorkbook wb = new SXSSFWorkbook(-1);
		CoreProperties coreProperties = wb.getXSSFWorkbook().getProperties().getCoreProperties();
		coreProperties.setCreator("Administrator");
		
		ExtendedProperties extendedProperties = wb.getXSSFWorkbook().getProperties().getExtendedProperties();
		extendedProperties.getUnderlyingProperties().setApplication("Encuestas Planeamiento");
		extendedProperties.getUnderlyingProperties().setCompany("Pacífico Seguros");
        return wb;
	}
}
