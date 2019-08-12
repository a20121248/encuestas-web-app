package com.ms.encuestas.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.repositories.ReporteRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

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

		System.out.println(result );
		
        SXSSFWorkbook wb = new SXSSFWorkbook(-1);
        SXSSFSheet sh = wb.createSheet();
        
        int rowNum = 0;
        List<Map<String,Object>> data = reporteRepository.reporteControl(procesoId);
        Map<String, String> campos = new HashMap<String, String>();
        //map.put("FECHA_DESCARGA","String");
        //map.put("PROCESO_ID","String");
        //map.put("PROCESO","String");
        //map.put("MATRICULA","String");
        campos.put("COLABORADOR","String");
        //map.put("NRO_POSICION","String");
        //map.put("POSICION","String");
        campos.put("AREA","String");
        //map.put("CENTRO_CODIGO","String");
        //map.put("CENTRO_NOMBRE","String");
        //map.put("PERFIL","String");
        for (Map<String, Object> fila : data) {
        	for (Entry<String, String> campo: campos.entrySet()) {
            	Object celda = fila.get("COLABORADOR");        	
                Row row = sh.createRow(rowNum++);
                int idxColumn = 0;
                row.createCell(idxColumn++).setCellValue((String) celda);
        		
        	}
        	/*campos.forEach((k,v) ->
        		System.out.println("Key: " + k + ": Value: " + v)
        	);*/
        
		}
        
        
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}

}
