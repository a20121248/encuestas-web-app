package com.ms.encuestas.services.utils;

import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ms.encuestas.models.Justificacion;

public interface ExcelServiceI {
	public int crearArchivo(SXSSFWorkbook wb, String rutaArchivo);
}
