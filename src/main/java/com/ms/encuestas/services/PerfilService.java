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
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Filtro;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.ObjetoObjetos;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.ObjetoRepository;
import com.ms.encuestas.repositories.PerfilRepository;
import com.ms.encuestas.repositories.TipoRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class PerfilService implements PerfilServiceI {
	private Logger logger = LoggerFactory.getLogger(PerfilService.class);
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;	
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private TipoRepository tipoRepository;
	@Autowired
	private CentroRepository centroRepository;
	@Autowired
	private ObjetoRepository objetoRepository;
	
	@Override
	public Long count() {
		return perfilRepository.count();
	}

	@Override
	public List<Perfil> findAll() {
		return perfilRepository.findAll();
	}
	
	@Override
	public Perfil findById(Long id) {
		return perfilRepository.findById(id);
	}

	@Override
	public int save(Perfil perfil) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Perfil perfil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void processExcel(InputStream file) {
		List<Tipo> perfilTipos = tipoRepository.getPerfilTypes();
		List<Centro> centros = centroRepository.findAll();
		List<Objeto> lineas = objetoRepository.findAllLineas();
		List<Objeto> canales = objetoRepository.findAllCanales();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
           XSSFSheet hoja = libro.getSheet("PERFILES");
           Iterator<Row> filas = hoja.iterator();
           
           /*if (!menuControlador.navegador.validarFilaNormal(filas.next(), new ArrayList(Arrays.asList("CODIGO","NOMBRE","ATRIBUIBLE","TIPO GASTO","CLASE GASTO")))) {
               menuControlador.navegador.mensajeError(titulo,menuControlador.MENSAJE_UPLOAD_HEADER);
               return null;
           }*/
	   		int numFilasOmitir = 6;
	   		while (numFilasOmitir-- > 0) filas.next();
           
           DataFormatter dataFormatter = new DataFormatter();
           while (filas.hasNext()) {
        	   Iterator<Cell> celdas = filas.next().cellIterator();
        	   
               String codigo = dataFormatter.formatCellValue(celdas.next());
               String nombre = dataFormatter.formatCellValue(celdas.next());
               String tipoNombre = dataFormatter.formatCellValue(celdas.next());
               Tipo tipo = perfilTipos.stream().filter(item -> tipoNombre.equals(item.getNombre())).findAny().orElse(null);
               String accion = dataFormatter.formatCellValue(celdas.next());
               
        	   if (tipo == null) {
        		   String msj = String.format("No se encontró el tipo de perfil. No se puede procesar el perfil {0}", codigo);
        		   logger.info(msj);
        		   return;
        	   }
        	   
        	   XSSFSheet hojaDetalle = libro.getSheet(codigo);
               
        	   Perfil perfil = new Perfil();
        	   perfil.setCodigo(codigo);
        	   perfil.setNombre(nombre);
        	   perfil.setTipo(tipo);
        	   Long perfilId = new Long(0);

               if (accion.equals("CREAR")) {
            	   perfilId = perfilRepository.insert(perfil);
               } else if (accion.equals("ACTUALIZAR")) {
            	   perfilId = perfilRepository.update(perfil);
               } else if (accion.equals("ELIMINAR")) {
            	   perfilRepository.delete(perfil);
            	   logger.info(String.format("Se eliminó el perfil %s.", codigo));
            	   return;
               } else {
            	   logger.info(String.format("No se realizó ninguna acción en el perfil %s porque la acción '%s' no existe.", codigo, accion));
            	   return;
               }
        	   
        	   if (tipoNombre.equals("STAFF")) {
        		   List<Centro> lstCentros = processExcelLstCentros(perfilId, hojaDetalle.iterator(), centros);
        		   perfilRepository.insertLstCentros(perfilId, lstCentros);
        	   } else {
        		   List<LineaCanal> lstLineasCanales = processExcelLstLineasCanales(perfilId, hojaDetalle.iterator(), lineas, canales);
        		   perfilRepository.insertLstLineasCanales(perfilId, lstLineasCanales);
        	   }
        	   logger.info(String.format("Se creó el perfil %s.", codigo));
           }
           libro.close();
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
	}
	
	private List<Centro> processExcelLstCentros(Long perfilId, Iterator<Row> filas, List<Centro> centros) {
   		int numFilasOmitir = 6;
   		while (numFilasOmitir-- > 0) filas.next();
   		
        DataFormatter dataFormatter = new DataFormatter();
        List<Centro> lstCentros = new ArrayList<Centro>();
        while (filas.hasNext()) {
     	   	Iterator<Cell> celdas = filas.next().cellIterator();
     	   
            String codigo = dataFormatter.formatCellValue(celdas.next());
            String nombre = dataFormatter.formatCellValue(celdas.next());

            Centro centro = centros.stream().filter(item -> codigo.equals(item.getCodigo())).findAny().orElse(null);
            if (centro == null) {
            	logger.error(String.format("El centro de costos con código %s no existe.", codigo));
            	continue;
            }
            lstCentros.add(centro);            	
        }
		return lstCentros;
	}
	
	private List<LineaCanal> processExcelLstLineasCanales(Long perfilId, Iterator<Row> filas, List<Objeto> lineas, List<Objeto> canales) {
   		int numFilasOmitir = 6;
   		while (numFilasOmitir-- > 0) filas.next();
   		
        DataFormatter dataFormatter = new DataFormatter();
        List<LineaCanal> lstLineasCanales = new ArrayList<LineaCanal>();
        while (filas.hasNext()) {
     	   	Iterator<Cell> celdas = filas.next().cellIterator();
     	   
            String lineaCodigo = dataFormatter.formatCellValue(celdas.next());
            String lineaNombre = dataFormatter.formatCellValue(celdas.next());
            String canalCodigo = dataFormatter.formatCellValue(celdas.next());
            String canalNombre = dataFormatter.formatCellValue(celdas.next());
            
            Objeto linea = lineas.stream().filter(item -> lineaCodigo.equals(item.getCodigo())).findAny().orElse(null);
            Objeto canal = canales.stream().filter(item -> canalCodigo.equals(item.getCodigo())).findAny().orElse(null);
            if (linea == null) {
            	logger.error(String.format("La línea con código %s no existe.", lineaCodigo));
            	continue;
            }
            if (canal == null) {
            	logger.error(String.format("El canal con código %s no existe.", canalCodigo));
            	continue;
            }
            lstLineasCanales.add(new LineaCanal(linea, canal));
        }
        logger.info("Para el perfil " + perfilId);
        logger.info("tamanho=" + lstLineasCanales.size());
		return lstLineasCanales;
	}
	
	@Override
	public Resource downloadExcel() {		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Perfiles.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("PERFILES");

        List<Map<String,Object>> data = perfilRepository.findAllList();
        
        if (data == null || data.size() == 0) {
        	data = perfilRepository.findAllListEmpty();
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
        	row.createCell(colNum).setCellValue((String) fila.get("TIPO"));sh.setColumnWidth(colNum++, 3000);
        	row.createCell(colNum).setCellValue((String) fila.get("DIMENSION1_CODIGO"));sh.setColumnWidth(colNum++, 3000);
        	row.createCell(colNum).setCellValue((String) fila.get("DIMENSION1_NOMBRE"));sh.setColumnWidth(colNum++, 8000);
        	row.createCell(colNum).setCellValue((String) fila.get("DIMENSION2_CODIGO"));sh.setColumnWidth(colNum++, 3000);
        	row.createCell(colNum).setCellValue((String) fila.get("DIMENSION2_NOMBRE"));sh.setColumnWidth(colNum++, 8000);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
}
