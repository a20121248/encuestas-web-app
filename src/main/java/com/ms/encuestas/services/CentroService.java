package com.ms.encuestas.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.CentroRepository;
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

	public Long count(Long empresaId) {
		return centroRepository.count(empresaId);
	}

	@Override
	public List<String> findAllCodigos() {
		List<String> codigos = centroRepository.findAllCodigos();
		if (codigos == null) {
			logger.info("No existe ningún centro de costos registrado en la base de datos.");
			codigos = new ArrayList<String>();
		}
		return codigos; 
	}
	
	@Override
	public List<Tipo> findAllTipos() {
		List<Tipo> tipos = tipoRepository.getCentroTypes();
		if (tipos == null) {
			logger.info("No existe ningún tipo de centro de costos registrado en la base de datos.");
			tipos = new ArrayList<Tipo>();
		}
		return tipos;
	}
	
	@Override
	public List<Centro> findAll() {
		try {
			return centroRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún centro de costos registrado en la base de datos.");
			return new ArrayList<Centro>();
		}
	}

	@Override
	public Centro findById(Long id) {
		try {
			return centroRepository.findById(id);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el centro de costos con ID='%d' porque está repetido en la base de datos.", id));
			return null;
		}
	}
	
	@Override
	public Centro findByCodigo(String codigo) {
		try {
			return centroRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el centros de costos con código '%s' porque está repetido en la base de datos.", codigo));
			return null;
		}
	}

	@Override
	public Centro insert(Centro centro) {
		Long empresaId = new Long(1); // Pacifico Seguros
		return centroRepository.insert(centro, empresaId);
	}
	
	@Override
	public Centro update(Centro centro) {
		Long empresaId = new Long(1); // Pacifico Seguros
		return centroRepository.update(centro, empresaId);
	}

	@Override
	public void delete(Centro centro) {
		centroRepository.delete(centro);
	}
	
	@Override
	public void deleteById(Long id) {
		centroRepository.deleteById(id);
	}
	
	@Override
	public void deleteAllCentros() {
		Long empresaId = new Long(1); // Pacifico Seguros
		centroRepository.deleteAll(empresaId);
		logger.info("Se eliminaron todos los centros de costos.");
	}
	
	@Override
	public void deleteAllLineasEps() {
		Long empresaId = new Long(2); // Pacifico EPS
		centroRepository.deleteAll(empresaId);
		logger.info("Se eliminaron todas las líneas EPS.");
	}

	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE CENTROS DE COSTOS====================================");
		List<Tipo> centroTipos = tipoRepository.getCentroTypes();
		List<String> centroCodigos = findAllCodigos();
		List<String> centroCodigosLeidos = new ArrayList<String>();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("CENTROS");
        	if (hoja == null) {
				logger.error("No se pudo procesar el Excel porque la hoja CENTROS no existe.");
				logger.info("======================FIN DE CARGA DE CENTROS DE COSTOS====================================");
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
               	if (codigo.equals("")) break;
               	String nombre = dataFormatter.formatCellValue(celdas.next());
               	String tipoNombre = dataFormatter.formatCellValue(celdas.next());
               	Tipo tipo = centroTipos.stream().filter(item -> tipoNombre.equals(item.getNombre())).findAny().orElse(null);
               	int nivel = Integer.parseInt(dataFormatter.formatCellValue(celdas.next()));
               	String grupo = dataFormatter.formatCellValue(celdas.next());
               	String accion = dataFormatter.formatCellValue(celdas.next());
               
        	   	Centro centro = new Centro();
        	   	centro.setCodigo(codigo);
        	   	centro.setNombre(nombre);
        	   	centro.setTipo(tipo);
        	   	centro.setNivel(nivel);
        	   	centro.setGrupo(grupo);
        	   	Long empresaId = new Long(1);
        	   	
               	if (accion.equals("CREAR")) {
               		if (centroCodigosLeidos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: La posición con código '%s' ya fue procesada para crearse en este Excel. Corregir el archivo y probar una nueva carga.", numFila, codigo));
	   				} else if (centroCodigos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: No se pudo crear el centro de costos '%s' porque el código '%s' ya fue usado.", numFila, nombre, codigo));
	   				} else {
	   					centroRepository.insert(centro, empresaId);
	   					centroCodigosLeidos.add(codigo);
	   					logger.info(String.format("FILA %d: Se creó el centro de costos '%s'.", numFila, codigo));
	   				}
               	} else if (accion.equals("EDITAR")) {
	   				Centro centroBuscado = findByCodigo(codigo);
	   				if (centroBuscado != null) {
	   					centro.setId(centroBuscado.getId());
	   					centroRepository.update(centro, empresaId);
	   					logger.info(String.format("FILA %d: Se editó el centro de costos con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar el centro de costos con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
               	} else if (accion.equals("ELIMINAR")) {
	   				Centro centroBuscado = findByCodigo(codigo);
	   				if (centroBuscado != null) {
	   					centroRepository.delete(centroBuscado);
	   					logger.info(String.format("FILA %d: Se eliminó el usuario con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo eliminar el usuario con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
               	} else {
            	   	logger.error(String.format("No se realizó ninguna acción en el centro de costos %s porque la acción '%s' no existe.", codigo, accion));
               	}
           	}
           	libro.close();
        } catch (IOException e) {
           	logger.error(e.getMessage());
       	}
        logger.info("======================FIN DE CARGA DE CENTROS DE COSTOS====================================");
	}
	
	@Override
	public Resource downloadExcel() {		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Centros de costos.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("CENTROS");

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
