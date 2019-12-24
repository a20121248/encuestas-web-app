package com.ms.encuestas.services.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

public interface ExcelServiceI {
	public SXSSFWorkbook crearLibro();
	public int crearArchivo(SXSSFWorkbook wb, String rutaArchivo);
	public int crearCabecera(Sheet sh, int rowNum, List<Map<String,Object>> data, List<Integer> widths, CellStyle style);
	public CellStyle cabeceraEstilo(SXSSFWorkbook wb);
	public CellStyle bordeEstilo(SXSSFWorkbook wb);
	public CellStyle fechaEstilo(SXSSFWorkbook wb);
	public ByteArrayResource crearResource(SXSSFWorkbook wb);
	public void createCell(Row row, int colNum, String value, CellStyle style);
	public void createCell(Row row, int colNum, Date value, CellStyle style);
	public void createCell(Row row, int colNum, double value, CellStyle style);
}
