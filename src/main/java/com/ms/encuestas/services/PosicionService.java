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
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.PerfilRepository;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.repositories.ProcesoRepository;
import com.ms.encuestas.repositories.TipoRepository;
import com.ms.encuestas.repositories.UsuarioRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;
import com.ms.encuestas.services.utils.LoggerServiceI;

@Service
public class PosicionService implements PosicionServiceI {
	private Logger logger = LoggerFactory.getLogger(PosicionService.class);
	@Autowired
	private LoggerServiceI log;
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private PosicionRepository posicionRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private CentroRepository centroRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private ProcesoRepository procesoRepository;

	public Long count() {
		return posicionRepository.count();
	}

	public List<Posicion> findAll() {
		return posicionRepository.findAll();
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
		return;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int deleteDatos(Proceso proceso) {
		int val = posicionRepository.deleteDatosProceso(proceso.getId());
		logger.info(String.format("Se eliminaron los datos del proceso '%s.'", proceso.getNombre()));
		return val;
	}
	
	@Override
	public void processExcelDatos(Proceso proceso, InputStream file) {
		List<Posicion> posiciones = posicionRepository.findAll();
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<Area> areas = areaRepository.findAll();
		List<Centro> centros = centroRepository.findAll();
		List<Perfil> perfiles = perfilRepository.findAll();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
           XSSFSheet hoja = libro.getSheet("DATOS_POSICIONES");
           if (hoja == null) {
        	   logger.error("La hoja DATOS_POSICIONES no existe.");
           }
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
        	   
               String posicionCodigo = dataFormatter.formatCellValue(celdas.next());
               String posicionNombre = dataFormatter.formatCellValue(celdas.next());
               Posicion posicion = posiciones.stream().filter(item -> posicionCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (posicion == null) {
            	   logger.error(String.format("No se encontró la posición con código '%s'.", posicionCodigo));
            	   continue;
               } else if (!posicionNombre.equals(posicion.getNombre())) {
            	   logger.error(String.format("La posición con nombre '%s' no coincide con la registrada con código '%s'.", posicionNombre, posicionCodigo));
            	   continue;
               }
               
               String usuarioCodigo = dataFormatter.formatCellValue(celdas.next());
               String usuarioNombre = dataFormatter.formatCellValue(celdas.next());
               Usuario usuario = usuarios.stream().filter(item -> usuarioCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (usuario == null) {
            	   logger.error(String.format("No se encontró el colaborador con matrícula '%s'.", usuarioCodigo));
               } else if (!usuarioNombre.equals(usuario.getNombreCompleto())) {
            	   logger.error(String.format("El colaborador con nombre '%s' no coincide con el registrado con matrícula '%s'.", usuarioNombre, usuarioCodigo));
               }
               
               String areaCodigo = dataFormatter.formatCellValue(celdas.next());
               String areaNombre = dataFormatter.formatCellValue(celdas.next());
               Area area = areas.stream().filter(item -> areaCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (areaCodigo == null) {
            	   logger.error(String.format("No se encontró el área con código '%s'.", areaCodigo));
            	   continue;
               } else if (!areaNombre.equals(area.getNombre())) {
            	   logger.error(String.format("El área con nombre '%s' no coincide con el registrado con código '%s'.", areaNombre, areaCodigo));
            	   continue;
               }
               
               String centroCodigo = dataFormatter.formatCellValue(celdas.next());
               String centroNombre = dataFormatter.formatCellValue(celdas.next());
               Centro centro = centros.stream().filter(item -> centroCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (centroCodigo == null) {
            	   logger.error(String.format("No se encontró el centro de costos con código '%s'.", centroCodigo));
            	   continue;
               } else if (!centroNombre.equals(centro.getNombre())) {
            	   logger.error(String.format("El centro de costos con nombre '%s' no coincide con el registrado con código '%s'.", centroNombre, centroCodigo));
            	   continue;
               }
               
               String perfilCodigo = dataFormatter.formatCellValue(celdas.next());
               String perfilNombre = dataFormatter.formatCellValue(celdas.next());
               Perfil perfil = perfiles.stream().filter(item -> perfilCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (perfil == null) {
            	   logger.error(String.format("No se encontró el perfi con código '%s'.", perfilCodigo));
            	   continue;
               } else if (!perfilNombre.equals(perfil.getNombre())) {
            	   logger.error(String.format("El perfil con nombre'%s' no coincide con el registrado con código '%s'.", perfilNombre, perfilCodigo));
            	   continue;
               }
               
               String responsableUsuarioCodigo = dataFormatter.formatCellValue(celdas.next());
               String responsableUsuarioNombre = dataFormatter.formatCellValue(celdas.next());
               Usuario responsableUsuario = usuarios.stream().filter(item -> usuarioCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (responsableUsuario == null) {
            	   logger.error(String.format("No se encontró el colaborador responsable con matrícula '%s'.", responsableUsuarioCodigo));
               } else if (!responsableUsuarioNombre.equals(responsableUsuario.getNombreCompleto())) {
            	   logger.error(String.format("El colaborador responsable '%s' no coincide con el registrado con matrícula '%s'.", responsableUsuarioNombre, responsableUsuarioCodigo));
               }
               
               String responsablePosicionCodigo = dataFormatter.formatCellValue(celdas.next());
               String responsablePosicionNombre = dataFormatter.formatCellValue(celdas.next());
               Posicion responsablePosicion = posiciones.stream().filter(item -> responsablePosicionCodigo.equals(item.getCodigo())).findAny().orElse(null);
               if (responsablePosicion == null) {
            	   logger.error(String.format("No se encontró la posición del responsable con código '%s'.", responsablePosicionCodigo));
            	   continue;
               } else if (!posicionNombre.equals(posicion.getNombre())) {
            	   logger.error(String.format("La posición del responsable con nombre '%s' no coincide con la registrada con código '%s'.", responsablePosicionNombre, responsablePosicionCodigo));
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
            	   logger.info(String.format("Se registró al colaborador '%s' con responsable '%s' en la encuesta '%s'.", usuarioNombre, responsableUsuarioNombre, proceso.getNombre()));
               } else {
            	   logger.info(String.format("No se encontró la acción '%s'.", accion));
            	   continue;
               }
           }
           libro.close();
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
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
