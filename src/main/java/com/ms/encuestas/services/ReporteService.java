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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Filtro;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.ReporteRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

@Service
public class ReporteService implements ReporteServiceI {
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
		
		List<Tipo> estados = filtro.getEstados();
		if (estados != null) estados = (estados.isEmpty() || estados.get(0).getId() == null) ? null : estados;
				
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = reporteRepository.reporteControl(procesoId, areas, centros, estados);
        List<Integer> widths = Arrays.asList(3000, 3800, 3000, 8000, 3000, 8000, 8000, 3000, 8000, 4000, 4000, 3000, 8000, 4000, 4000, 4000, 4000, 4000, 3000);
        
        if (data == null || data.size() == 0) {
        	data = reporteRepository.reporteControlVacio();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_DESCARGA"), fechaEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PROCESO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("MATRICULA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("COLABORADOR"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NRO_POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("AREA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL_TIPO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("RESPONSABLE_MATRICULA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("RESPONSABLE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_1"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_2"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_3"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_4"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ESTADO_GLOBAL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("ULTIMA_MODIFICACION"), fechaEstilo);
        }
        return excelService.crearResource(wb);
	}

	@Override
	public Resource generarReporteEmpresas(Filtro filtro) {
		Long procesoId = filtro.getProceso().getId();
		
		List<Area> areas = filtro.getAreas();
		if (areas != null) areas = (areas.isEmpty() || areas.get(0).getId() == null) ? null : areas;			
		
		List<Centro> centros = filtro.getCentros();
		if (centros != null) centros = (centros.isEmpty() || centros.get(0).getId() == null) ? null : centros;
		
		List<Tipo> estados = filtro.getEstados();
		if (estados != null) estados = (estados.isEmpty() || estados.get(0).getId() == null) ? null : estados;
				
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = reporteRepository.reporteEmpresas(procesoId, areas, centros, estados);
        List<Integer> widths = Arrays.asList(3000, 3800, 3000, 8000, 3000, 8000, 8000, 3000, 8000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 3000, 4000, 3000, 4000, 3000, 3000);
        
        if (data == null || data.size() == 0) {
        	data = reporteRepository.reporteEmpresasVacio();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_DESCARGA"), fechaEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PROCESO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("MATRICULA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("COLABORADOR"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NRO_POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("AREA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL_TIPO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_1"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_2"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_3"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_4"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ESTADO_GLOBAL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("ULTIMA_MODIFICACION"), fechaEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("EMPRESA"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("EMPRESA_PORCENTAJE")).doubleValue(), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("LINEA_EPS"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("LINEA_EPS_PORCENTAJE")).doubleValue(), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PONDERADO")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
	}
	
	@Override
	public Resource generarReporteConsolidado(Filtro filtro) {
		Long procesoId = filtro.getProceso().getId();
		
		List<Area> areas = filtro.getAreas();
		if (areas != null) areas = (areas.isEmpty() || areas.get(0).getId() == null) ? null : areas;			
		
		List<Centro> centros = filtro.getCentros();
		if (centros != null) centros = (centros.isEmpty() || centros.get(0).getId() == null) ? null : centros;
		
		List<Tipo> estados = filtro.getEstados();
		if (estados != null) estados = (estados.isEmpty() || estados.get(0).getId() == null) ? null : estados;
				
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet();

        List<Map<String,Object>> data = reporteRepository.reporteConsolidado(procesoId, areas, centros, estados);
        List<Integer> widths = Arrays.asList(3000, 3800, 3000, 8000, 3000, 8000, 8000, 3000, 8000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 3000, 3000, 8000, 3000, 8000, 4000);
        
        if (data == null || data.size() == 0) {
        	data = reporteRepository.reporteConsolidadoVacio();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_DESCARGA"), fechaEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PROCESO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("MATRICULA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("COLABORADOR"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NRO_POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("POSICION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("AREA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("CECO_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("PERFIL_TIPO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_1"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_2"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_3"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ETAPA_4"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ESTADO_GLOBAL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("ULTIMA_MODIFICACION"), fechaEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION1_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION1"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION2_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION2"), bordeEstilo);
    		excelService.createCell(row, colNum++, ((BigDecimal) fila.get("PONDERADO")).doubleValue(), bordeEstilo);
        }
        return excelService.crearResource(wb);
	}	
}
