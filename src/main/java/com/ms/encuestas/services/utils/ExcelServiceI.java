package com.ms.encuestas.services.utils;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface ExcelServiceI {
	public int crearArchivo(SXSSFWorkbook wb, String rutaArchivo);
	public int crearCabecera(Sheet sh, int rowNum, List<Map<String,Object>> data);
}
