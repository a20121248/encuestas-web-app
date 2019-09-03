package com.ms.encuestas.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.JustificacionRepository;
import com.ms.encuestas.repositories.TipoRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class CentroService implements CentroServiceI {
	private Logger logger = LoggerFactory.getLogger(CentroService.class);
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private CentroRepository centroRepository;
	@Autowired
	private TipoRepository tipoRepository;

	public Long count() {
		return centroRepository.count();
	}

	public List<Centro> findAll() {
		return centroRepository.findAll();
	}

	public Centro findById(Long id) {
		return centroRepository.findById(id);
	}

	public Centro save(Centro centro) {
		return null;
	}

	public void delete(Centro centro) {
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void processExcel(InputStream file) {
		List<Tipo> centroTipos = tipoRepository.getCentroTypes(); 
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("CENTROS");

        	Iterator<Row> filas = hoja.iterator();
           
           /*if (!menuControlador.navegador.validarFilaNormal(filas.next(), new ArrayList(Arrays.asList("CODIGO","NOMBRE","ATRIBUIBLE","TIPO GASTO","CLASE GASTO")))) {
               menuControlador.navegador.mensajeError(titulo,menuControlador.MENSAJE_UPLOAD_HEADER);
               return null;
           }*/
	   		int numFilasOmitir = 7;
	   		while (numFilasOmitir-- > 0) {
	   			filas.next();
	   		}
           
           DataFormatter dataFormatter = new DataFormatter();
           while (filas.hasNext()) {
        	   Iterator<Cell> celdas = filas.next().cellIterator();
        	   
               String codigo = dataFormatter.formatCellValue(celdas.next());
               String nombre = dataFormatter.formatCellValue(celdas.next());
               String tipoNombre = dataFormatter.formatCellValue(celdas.next());
               Tipo tipo = centroTipos.stream().filter(item -> tipoNombre.equals(item.getNombre())).findAny().orElse(centroTipos.get(0));
               String grupo = dataFormatter.formatCellValue(celdas.next());
               String nivel = dataFormatter.formatCellValue(celdas.next());
               String accion = dataFormatter.formatCellValue(celdas.next());
               
        	   Centro centro = new Centro();
        	   centro.setCodigo(codigo);
        	   centro.setNombre(nombre);
        	   centro.setTipo(tipo);
        	   centro.setNivel(1);
        	   centro.setGrupo(grupo);
               
               if (accion.equals("CREAR")) {
            	   centroRepository.insert(centro);
            	   logger.info(String.format("Se creó el centro de costos %s.", codigo));
               } else if (accion.equals("ACTUALIZAR")) {
            	   centroRepository.update(centro);
            	   logger.info(String.format("Se actualizó el centro de costos %s.", codigo));
               } else if (accion.equals("ELIMINAR")) {
            	   centroRepository.delete(centro);
            	   logger.info(String.format("Se eliminó el centro de costos %s.", codigo));
               } else {
            	   logger.info(String.format("No se realizó ninguna acción en el centro de costos %s porque la acción '%s' no existe.", codigo, accion));
               }
           }
           libro.close();
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
	}
	
	@Override
	public Resource downloadExcel() {		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Reportes.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("PERFILES");

        List<Map<String,Object>> data = centroRepository.findAllList();
        
        if (data == null || data.size() == 0) {
        	data = centroRepository.findAllListEmpty();
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
        	row.createCell(colNum).setCellValue((String) fila.get("NOMBRE"));sh.setColumnWidth(colNum++, 12000);
        	row.createCell(colNum).setCellValue((String) fila.get("TIPO"));sh.setColumnWidth(colNum++, 5000);
        	row.createCell(colNum).setCellValue(((BigDecimal) fila.get("NIVEL")).intValue());sh.setColumnWidth(colNum++, 3000);
        	row.createCell(colNum).setCellValue((String) fila.get("GRUPO"));sh.setColumnWidth(colNum++, 4000);
        	row.createCell(colNum).setCellValue((Date) fila.get("FECHA_CREACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        	row.createCell(colNum).setCellValue((Date) fila.get("FECHA_ACTUALIZACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
}
