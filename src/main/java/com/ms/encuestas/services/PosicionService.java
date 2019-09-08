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
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class PosicionService implements PosicionServiceI {
	private Logger logger = LoggerFactory.getLogger(PosicionService.class);
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private PosicionRepository posicionRepository;
	@Autowired
	private UsuarioServiceI usuarioService;
	@Autowired
	private AreaServiceI areaService;
	@Autowired
	private CentroServiceI centroService;
	@Autowired
	private PerfilServiceI perfilService;

	@Override
	public Long count() {
		return posicionRepository.count();
	}
	
	@Override
	public Long countDatos(Long procesoId) {
		return posicionRepository.countDatos(procesoId);
	}
	
	@Override
	public List<String> findAllCodigos() {
		List<String> codigos = posicionRepository.findAllCodigos();
		if (codigos == null) {
			logger.info("No existe ninguna posición registrada en la base de datos.");
			codigos = new ArrayList<String>();
		}
		return codigos; 
	}

	public List<Posicion> findAll() {
		try {
			return posicionRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.error("No se encontraron posiciones en la base de datos.");
			return new ArrayList<Posicion>();
		}
	}

	public Posicion findByCodigo(String codigo) {
		return posicionRepository.findByCodigo(codigo);
	}

	public Posicion findByCodigoWithAreaAndCentro(String codigo) {
		return posicionRepository.findByCodigoWithAreaAndCentro(codigo);
	}
	
	public Posicion findByCodigoWithArea(String codigo) {
		return posicionRepository.findByCodigoWithArea(codigo);
	}
	
	public Posicion findByCodigoWithCentro(String codigo) {
		return posicionRepository.findByCodigoWithCentro(codigo);
	}

	public Posicion save(Posicion posicion) {
		return null;
	}

	public void delete(Posicion posicion) {
		posicionRepository.delete(posicion);
	}

	public void deleteById(Long id) {
		posicionRepository.deleteById(id);
	}
	
	@Override
	public void deleteDatos(Proceso proceso) {
		posicionRepository.deleteDatosProceso(proceso.getId());
		logger.info(String.format("Se eliminaron los datos del proceso '%s'.", proceso.getNombre()));
	}
	
	@Override
	public void processExcelDatos(Proceso proceso, InputStream file) {
		logger.info("======================INICIANDO CARGA DE DATOS DE LAS POSICIONES====================================");
		List<Posicion> posiciones = findAll();
		List<Usuario> usuarios = usuarioService.findAll();
		List<Area> areas = areaService.findAll();		
		List<Centro> centros = centroService.findAll();
		List<Perfil> perfiles = perfilService.findAll();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("DATOS_POSICIONES");
        	if (hoja == null) {
        		logger.error("No se pudo procesar el Excel porque la hoja DATOS_POSICIONES no existe.");
        		logger.info("======================FIN DE CARGA DE DATOS DE LAS POSICIONES====================================");
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
	   			
	   			String posicionCodigo = dataFormatter.formatCellValue(celdas.next());
	   			if (posicionCodigo.equals("")) break;
	   			String posicionNombre = dataFormatter.formatCellValue(celdas.next());
	   			Posicion posicion = posiciones.stream().filter(item -> posicionCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (posicion == null) {
	   				logger.error(String.format("FILA %d: La posición con código '%s' no existe o fue eliminada.", numFila, posicionCodigo));
	   				continue;
	   			} else if (!posicionNombre.equals(posicion.getNombre())) {
	   				logger.error(String.format("FILA %d: La posición con nombre '%s' no coincide con la registrada con código '%s'.", numFila, posicionNombre, posicionCodigo));
	   				continue;
	   			}
	   			
	   			String usuarioCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String usuarioNombre = dataFormatter.formatCellValue(celdas.next());
	   			Usuario usuario = usuarios.stream().filter(item -> usuarioCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (usuario == null) {
	   				logger.error(String.format("FILA %d: El colaborador con matrícula '%s' no existe o fue eliminado.", numFila, usuarioCodigo));
	   				continue;
	   			} else if (!usuarioNombre.equals(usuario.getNombreCompleto())) {
	   				logger.error(String.format("FILA %d: El colaborador con nombre '%s' no coincide con el registrado con matrícula '%s'.", numFila, usuarioNombre, usuarioCodigo));
	   				continue;
	   			}

	   			String areaCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String areaNombre = dataFormatter.formatCellValue(celdas.next());
	   			Area area = areas.stream().filter(item -> areaCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (areaCodigo == null) {
	   				logger.error(String.format("FILA %d: El área con código '%s' no existe o fue eliminado.", numFila, areaCodigo));
	   				continue;
	   			} else if (!areaNombre.equals(area.getNombre())) {
	   				logger.error(String.format("FILA %d: El área con nombre '%s' no coincide con el registrado con código '%s'.", numFila, areaNombre, areaCodigo));
	   				continue;
	   			}
	   			
	   			String centroCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String centroNombre = dataFormatter.formatCellValue(celdas.next());
	   			Centro centro = centros.stream().filter(item -> centroCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (centroCodigo == null) {
	   				logger.error(String.format("FILA %d: El centro de costos con código '%s' no existe o fue eliminado.", numFila, centroCodigo));
	   				continue;
	   			} else if (!centroNombre.equals(centro.getNombre())) {
	   				logger.error(String.format("FILA %d: El centro de costos con nombre '%s' no coincide con el registrado con código '%s'.", numFila, centroNombre, centroCodigo));
	   				continue;
	   			}
               
	   			String perfilCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String perfilNombre = dataFormatter.formatCellValue(celdas.next());
	   			Perfil perfil = perfiles.stream().filter(item -> perfilCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (perfil == null) {
	   				logger.error(String.format("FILA %d: El perfil con código '%s' no existe o fue eliminado.", numFila, perfilCodigo));
	   				continue;
	   			} else if (!perfilNombre.equals(perfil.getNombre())) {
	   				logger.error(String.format("FILA %d: El perfil con nombre '%s' no coincide con el registrado con código '%s'.", numFila, perfilNombre, perfilCodigo));
	   				continue;
	   			}
	   			
	   			String responsableUsuarioCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String responsableUsuarioNombre = dataFormatter.formatCellValue(celdas.next());
	   			Usuario responsableUsuario = usuarios.stream().filter(item -> responsableUsuarioCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (responsableUsuario == null) {
	   				logger.error(String.format("FILA %d: El colaborador responsable con matrícula '%s' no existe o fue eliminado.", numFila, responsableUsuarioCodigo));
	   				continue;
	   			} else if (!responsableUsuarioNombre.equals(responsableUsuario.getNombreCompleto())) {
	   				logger.error(String.format("FILA %d: El colaborador responsable con nombre '%s' no coincide con el registrado con matrícula '%s'.", numFila, responsableUsuarioNombre, responsableUsuarioCodigo));
	   				continue;
	   			}
               
	   			String responsablePosicionCodigo = dataFormatter.formatCellValue(celdas.next());
	   			String responsablePosicionNombre = dataFormatter.formatCellValue(celdas.next());
	   			Posicion responsablePosicion = posiciones.stream().filter(item -> responsablePosicionCodigo.equals(item.getCodigo())).findAny().orElse(null);
	   			if (responsablePosicion == null) {
	   				logger.error(String.format("FILA %d: La posición del responsable con código '%s' no existe o fue eliminada.", numFila, responsablePosicionCodigo));
	   				continue;
	   			} else if (!posicionNombre.equals(posicion.getNombre())) {
	   				logger.error(String.format("FILA %d: La posición del responsable con nombre '%s' no coincide con la registrada con código '%s'.", numFila, responsablePosicionNombre, responsablePosicionCodigo));
	   				continue;
	   			}
                              
	   			// Datos de la posicion
	   			DatosPosicion datos = new DatosPosicion();
	   			datos.setPosicion(posicion);
	   			datos.setUsuario(usuario);
	   			datos.setArea(area);
	   			datos.setCentro(centro);
	   			datos.setPerfil(perfil);
	   			datos.setResponsablePosicion(responsablePosicion);
	   			String accion = dataFormatter.formatCellValue(celdas.next());
	   			if (accion.equals("CREAR")) {
	   				posicionRepository.insertDatos(proceso, datos);
	   				logger.info(String.format("FILA %d: Se registró al colaborador '%s' con responsable '%s' en la encuesta '%s'.", numFila, usuarioNombre, responsableUsuarioNombre, proceso.getNombre()));
	   			} else if (accion.equals("ELIMINAR")) {
	   				posicionRepository.deleteDatosColaborador(proceso.getId(), usuario.getCodigo());
	   				logger.info(String.format("FILA %d: Se quitó al colaborador '%s' en la encuesta '%s'.", numFila, usuarioNombre, proceso.getNombre()));	   			
	   			} else {
	   		        logger.info(String.format("FILA %d: No se encontró la acción '%s'.", numFila, accion));
	   			}
	   		}
	   		libro.close();
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }
        logger.info("======================FIN DE CARGA DE DATOS DE LAS POSICIONES====================================");
	}

	@Override
	public Posicion findByProcesoIdAndUsuarioCodigo(Long procesoId, String usuarioCodigo) {
		try {
			return posicionRepository.findByProcesoIdAndUsuarioCodigo(procesoId, usuarioCodigo);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró una posición asociada al usuario '%s' para el proceso con ID=%d.", usuarioCodigo, procesoId));
			return null;
		}
	}

	@Override
	public Resource downloadExcelDatos() {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Datos de las posiciones.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("DATOS_POSICIONES");

        List<Map<String,Object>> data = posicionRepository.findAllDatosList();
        
        if (data == null || data.size() == 0) {
        	data = posicionRepository.findAllDatosListEmpty();
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
    		row.createCell(colNum).setCellValue((String) fila.get("POSICION_CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("POSICION_NOMBRE"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("MATRICULA"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("NOMBRE_COMPLETO"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("AREA_CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("AREA_NOMBRE"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("CENTRO_CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("CENTRO_NOMBRE"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("PERFIL_CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("PERFIL_NOMBRE"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("RESPONSABLE_MATRICULA"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("RESPONSABLE_NOMBRE_COMPLETO"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((String) fila.get("RESPONSABLE_POSICION_CODIGO"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("RESPONSABLE_POSICION_NOMBRE"));sh.setColumnWidth(colNum++, 6000);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_CREACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_ACTUALIZACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
	
	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE POSICIONES====================================");
		List<String> posicionCodigos = findAllCodigos();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("POSICIONES");
        	if (hoja == null) {
				logger.error("No se pudo procesar el Excel porque la hoja POSICIONES no existe.");
				logger.info("======================FIN DE CARGA DE POSICIONES====================================");
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
	   			String accion = dataFormatter.formatCellValue(celdas.next());
               
	   			Posicion posicion = new Posicion();
	   			posicion.setCodigo(codigo);
        	   	posicion.setNombre(nombre);

	   			if (accion.equals("CREAR")) {
	   				if (!posicionCodigos.contains(codigo)) {
	   					posicionRepository.insert(posicion);
	   					logger.info(String.format("FILA %d: Se creó la posición '%s'.", numFila, codigo));
	   				} else {
	   					logger.info(String.format("FILA %d: No se pudo crear la posición '%s' porque el código '%s' ya fue usado.", numFila, nombre, codigo));
	   				}
	   			} else if (accion.equals("EDITAR")) {
	   				Posicion posicionBuscado = posicionRepository.findByCodigo(codigo);
	   				if (posicionBuscado != null) {
	   					posicionRepository.update(posicion);
	   					logger.info(String.format("FILA %d: Se editó la posición con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar la posición con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else if (accion.equals("ELIMINAR")) {
	   				Posicion posicionBuscado = posicionRepository.findByCodigo(codigo);
	   				if (posicionBuscado != null) {
	   					posicionRepository.delete(posicionBuscado);
	   					logger.info(String.format("FILA %d: Se eliminó la posición con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo eliminar la posición con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else {
	   				logger.error(String.format("FILA %d: No se realizó ninguna acción en la posición con código '%s' porque la acción '%s' no existe.", numFila, codigo, accion));
	   			}           	
           }
           libro.close();
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
       logger.info("======================FIN DE CARGA DE POSICIONES====================================");
	}
	
	@Override
	public Resource downloadExcel() {
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Posiciones.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("POSICIONES");

        List<Map<String,Object>> data = posicionRepository.findAllList();
        
        if (data == null || data.size() == 0) {
        	data = posicionRepository.findAllListEmpty();
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
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_CREACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_ACTUALIZACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
}
