package com.ms.encuestas.services;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.repositories.AreaRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

@Service
public class AreaService implements AreaServiceI {
	private Logger logger = LoggerFactory.getLogger(AreaService.class);

	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private AreaRepository areaRepository;

	public Long count() {
		return areaRepository.count();
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
	
	public List<Area> findAll() {
		try {
			return areaRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ninguna área registrada en la base de datos.");
			return new ArrayList<Area>();
		}
	}	

	public Area findById(Long id) {
		try {
			return areaRepository.findById(id);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el área con ID='%d' porque está repetida en la base de datos.", id));
			return null;
		}
	}
	
	public Area findByCodigo(String codigo) {
		try {
			return areaRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el área con código '%s' porque está repetida en la base de datos.", codigo));
			return null;
		}
	}

	@Override
	public Area insert(Area area) {
		return areaRepository.insert(area);
	}
	
	@Override
	public Area update(Area area) {
		return areaRepository.update(area);
	}
	
	@Override
	public void deleteById(Long id) {
		areaRepository.deleteById(id);
	}
	
	public void deleteAll() {
		areaRepository.deleteAll();
		logger.info("Se eliminaron todas las áreas.");
	}
	
	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE ÁREAS====================================");
		List<String> areaCodigos = findAllCodigos();
		List<String> areaCodigosLeidos = new ArrayList<String>();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("AREAS");
        	if (hoja == null) {
        		logger.error("No se pudo procesar el Excel porque la hoja AREAS no existe.");
        		logger.info("======================FIN DE CARGA DE ÁREAS====================================");
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
	   				if (areaCodigosLeidos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: La posición con código '%s' ya fue procesada para crearse en este Excel. Corregir el archivo y probar una nueva carga.", numFila, codigo));
	   				} else if (areaCodigos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: No se pudo crear el área '%s' porque el código '%s' ya fue usado.", numFila, nombre, codigo));
	   				} else {
	   					areaRepository.insert(area);
	   					areaCodigosLeidos.add(codigo);
	   					logger.info(String.format("FILA %d: Se creó el área '%s'.", numFila, codigo));
	   				}
	   			} else if (accion.equals("EDITAR")) {
	   				Area areaBuscada = findByCodigo(codigo);
	   				if (areaBuscada != null) {
	   					area.setId(areaBuscada.getId());
	   					areaRepository.update(area);
	   					logger.info(String.format("FILA %d: Se editó el área con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar el área con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else if (accion.equals("ELIMINAR")) {
	   				Area areaBuscada = findByCodigo(codigo);
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
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet("AREAS");

        List<Map<String,Object>> data = areaRepository.findAllList();
        List<Integer> widths = Arrays.asList(3000, 12000, 12000, 3000, 3000);
        
        if (data == null || data.size() == 0) {
        	data = areaRepository.findAllListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);
    		excelService.createCell(row, colNum++, (String) fila.get("CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIVISION"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_CREACION"), fechaEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_ACTUALIZACION"), fechaEstilo);
        }
        return excelService.crearResource(wb);
	}

	@Override
	public Area softDelete(Area area) {
		return areaRepository.softDelete(area);
	}

	@Override
	public Area softUndelete(Area area) {
		return areaRepository.softUndelete(area);
	}
}
