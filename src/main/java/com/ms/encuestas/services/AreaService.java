package com.ms.encuestas.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.DatosPosicion;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.AreaRepository;
import com.ms.encuestas.repositories.JustificacionRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class AreaService implements AreaServiceI {
	private Logger logger = LoggerFactory.getLogger(AreaService.class);
	
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private AreaRepository areaRepository;

	public long count() {
		return areaRepository.count();
	}

	public List<Area> findAll() {
		return areaRepository.findAll();
	}
	
	public List<Area> findAllWithDivision() {
		return areaRepository.findAllWithDivision();
	}

	public Area findById(Long id) {
		return areaRepository.findById(id);
	}
	
	public Area findByCodigo(String codigo) {
		try {
			return areaRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No existe el área con código '%s' en la base de datos.", codigo));
			return null;
		}
	}

	public Area save(Area area) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Area area) {
		// TODO Auto-generated method stub

	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	public Area findByIdWithDivision(Long id) {
		return areaRepository.findByIdWithDivision(id);
	}
	
	@Override
	public List<String> findAllCodigos() {
		List<String> codigos = areaRepository.findAllCodigos();
		if (codigos == null) {
			logger.info("No existe ningún área registrada en la base de datos.");
			codigos = new ArrayList<String>();
		}
		return codigos; 
	}
	
	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE ÁREAS====================================");
		List<String> areaCodigos = findAllCodigos();
	
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("AREAS");
        	if (hoja == null) {
        		logger.error("No se pudo procesar el Excel porque la hoja AREAS no existe.");
        		return;
        	}
        	
        	Iterator<Row> filas = hoja.iterator();
           
        	/*if (!menuControlador.navegador.validarFilaNormal(filas.next(), new ArrayList(Arrays.asList("CODIGO","NOMBRE","ATRIBUIBLE","TIPO GASTO","CLASE GASTO")))) {
               menuControlador.navegador.mensajeError(titulo,menuControlador.MENSAJE_UPLOAD_HEADER);
               return null;
           	}*/
	   		int numFilasOmitir = 6;
	   		for (int i = 0; i < numFilasOmitir; ++i) filas.next();
	   		           
	   		DataFormatter dataFormatter = new DataFormatter();
	   		for (int numFila = numFilasOmitir+1; filas.hasNext(); ++numFila) {
	   			Iterator<Cell> celdas = filas.next().cellIterator();
	   			
	   			String codigo = dataFormatter.formatCellValue(celdas.next());
	   			String nombre = dataFormatter.formatCellValue(celdas.next());
	   			String division = dataFormatter.formatCellValue(celdas.next());
	   			String accion = dataFormatter.formatCellValue(celdas.next());
                       	   
	   			Area area = new Area();
	   			area.setCodigo(codigo);
	   			area.setNombre(nombre);
	   			area.setDivision(division);

	   			if (accion.equals("CREAR")) {
	   				if (!areaCodigos.contains(codigo)) {
	   					areaRepository.insert(area);
	   					logger.info(String.format("FILA %d: Se creó el área '%s'.", numFila, codigo));
	   				} else {
	   					logger.info(String.format("FILA %d: No se pudo crear el área '%s' porque el código '%s' ya fue usado.", numFila, nombre, codigo));
	   				}
	   			} else if (accion.equals("EDITAR")) {
	   				Area areaBuscada = findByCodigo(codigo);
	   				if (areaBuscada != null) {
	   					areaRepository.update(area);
	   					logger.info(String.format("FILA %d: Se editó el área con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar el área con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else if (accion.equals("ELIMINAR")) {
	   				Area areaBuscada = areaRepository.findByCodigo(codigo);
	   				if (areaBuscada != null) {
	   					areaRepository.delete(areaBuscada);
	   					logger.info(String.format("FILA %d: Se eliminó el área con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo eliminar el área con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else {
	   				logger.error(String.format("FILA %d: No se realizó ninguna acción al área con código '%s' porque la acción '%s' no existe.", numFila, codigo, accion));
	   			}
	   		}
	   		libro.close();
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }
        logger.info("======================FIN DE CARGA DE ÁREAS====================================");
	}
	
	@Override
	public Resource downloadExcel() {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Áreas.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("AREAS");

        List<Map<String,Object>> data = areaRepository.findAllList();
        
        if (data == null || data.size() == 0) {
        	data = areaRepository.findAllListEmpty();
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
    		row.createCell(colNum).setCellValue((String) fila.get("NOMBRE"));sh.setColumnWidth(colNum++, 8000);
    		row.createCell(colNum).setCellValue((String) fila.get("DIVISION"));sh.setColumnWidth(colNum++, 8000);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_CREACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_ACTUALIZACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
}
