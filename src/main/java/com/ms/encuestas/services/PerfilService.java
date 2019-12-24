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

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.PerfilRepository;
import com.ms.encuestas.repositories.TipoRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

@Service
public class PerfilService implements PerfilServiceI {
	private Logger logger = LoggerFactory.getLogger(PerfilService.class);

	@Autowired
	private ExcelServiceI excelService;	
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private TipoRepository tipoRepository;
	@Autowired
	private CentroServiceI centroService;
	@Autowired
	private ObjetoServiceI objetoService;
	
	@Override
	public Long count() {
		return perfilRepository.count();
	}

	@Override
	public List<Perfil> findAll() {
		try {
			return perfilRepository.findAll();
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún perfil registrado en la base de datos.");
			return new ArrayList<Perfil>();
		}
	}
	
	@Override
	public Perfil findById(Long id) {
		try {
			return perfilRepository.findById(id);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el perfil con ID='%d' porque está repetido en la base de datos.", id));
			return null;
		}
	}
	
	@Override
	public Perfil findByCodigo(String codigo) {
		try {
			return perfilRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el perfil con código '%s' porque está repetido en la base de datos.", codigo));
			return null;
		}
	}

	@Override
	public int save(Perfil perfil) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Perfil perfil) {
		perfilRepository.delete(perfil);
	}
	
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void deleteAll() {
		perfilRepository.deleteAll();
		logger.info("Se eliminaron todos los perfiles de la base de datos.");
	}
	
	@Override
	public List<String> findAllCodigos() {
		List<String> codigos = perfilRepository.findAllCodigos();
		if (codigos == null) {
			logger.info("No existe ningún perfil registrado en la base de datos.");
			codigos = new ArrayList<String>();
		}
		return codigos; 
	}
	
	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE PERFILES====================================");
		List<Tipo> perfilTipos = tipoRepository.getPerfilTypes();
		List<Centro> centros = centroService.findAll();
		List<Objeto> lineas = objetoService.findAllLineas();
		List<Objeto> canales = objetoService.findAllCanales();
		List<String> perfilCodigos = findAllCodigos();
		List<String> perfilCodigosLeidos = new ArrayList<String>();
		
        try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
           XSSFSheet hoja = libro.getSheet("PERFILES");
			if (hoja == null) {
				logger.error("No se pudo procesar el Excel porque la hoja PERFILES no existe.");
				logger.info("======================FIN CARGA DE PERFILES====================================");
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
               if (codigo == null) {
        		   logger.info(String.format("FILA %d: No se puede procesar el perfil porque el campo código está vacío.", numFila));
        		   continue;
               } else if (codigo.equals("")) {
            	   break;
               }
               
               String nombre = dataFormatter.formatCellValue(celdas.next());
               if (nombre == null) {
        		   logger.info(String.format("FILA %d: No se puede procesar el perfil porque el campo nombre está vacío.", numFila));
        		   continue;
               }
               
               String tipoNombre = dataFormatter.formatCellValue(celdas.next());
               if (tipoNombre == null) {
        		   logger.info(String.format("FILA %d: No se puede procesar el perfil porque el campo tipo está vacío.", numFila));
        		   continue;
               }
               Tipo tipo = perfilTipos.stream().filter(item -> tipoNombre.equals(item.getNombre())).findAny().orElse(null);
        	   if (tipo == null) {
        		   logger.info(String.format("FILA %d: No se puede procesar el perfil con código '%s' porque el tipo '%s' no existe.", numFila, codigo, tipoNombre));
        		   continue;
        	   }
        	   
               String accion = dataFormatter.formatCellValue(celdas.next());
               if (accion == null) {
        		   logger.info(String.format("FILA %d: No se puede procesar el perfil porque el campo tipo está vacío.", numFila));
        		   continue;
               }
        	   
        	   XSSFSheet hojaDetalle;
               
        	   Perfil perfil = new Perfil();
        	   perfil.setCodigo(codigo);
        	   perfil.setNombre(nombre);
        	   perfil.setTipo(tipo);

               if (accion.equals("CREAR")) {
            	   	if (perfilCodigosLeidos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: El perfil con código '%s' ya fue procesado para crearse en este Excel. Corregir el archivo y probar una nueva carga.", numFila, codigo));
	   				} else if (perfilCodigos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: No se puede crear el perfil '%s' porque el código '%s' ya fue usado.", numFila, nombre, codigo));
	   				} else {
	   					hojaDetalle = libro.getSheet(codigo);
	   					if (hojaDetalle == null) {
	   						logger.error(String.format("FILA %d: No se encontró una hoja con nombre '%s'. No se puede crear el perfil.", numFila, codigo));
	   					} else {
	   						perfil = perfilRepository.insert(perfil);
	   						perfilCodigosLeidos.add(codigo);
	   						int cantRegistros = -1;
	   						if (tipoNombre.equals("STAFF")) {
	   							List<Centro> lstCentros = processExcelLstCentros(codigo, perfil, hojaDetalle.iterator(), centros);
	   							perfilRepository.insertLstCentros(perfil.getId(), lstCentros);
	   							cantRegistros = lstCentros.size();
	   						} else {
	   							List<LineaCanal> lstLineasCanales = processExcelLstLineasCanales(codigo, perfil, hojaDetalle.iterator(), lineas, canales);
	   							perfilRepository.insertLstLineasCanales(perfil.getId(), lstLineasCanales);
	   							cantRegistros = lstLineasCanales.size();
	   						}
	   						logger.info(String.format("FILA %d: Se creó el perfil '%s' de tipo '%s' con %d registros.", numFila, codigo, tipoNombre, cantRegistros));
	   					}
	   				}
               	} else if (accion.equals("EDITAR")) {
               		hojaDetalle = libro.getSheet(codigo);
            	   if (hojaDetalle == null) {
            		   logger.error(String.format("FILA %d: No se encontró una hoja con nombre '%s'. No se puede actualizar el perfil.", numFila, codigo));
            	   } else {            		   
            		   Perfil perfilBuscado = findByCodigo(codigo);
            		   if (perfilBuscado != null) {
            			   perfil.setId(perfilBuscado.getId());
            			   perfil = perfilRepository.update(perfil);
            			   int cantRegistros = -1;
            			   if (tipoNombre.equals("STAFF")) {
            				   List<Centro> lstCentros = processExcelLstCentros(codigo, perfil, hojaDetalle.iterator(), centros);
            				   perfilRepository.deleteDetalleCentros(perfil.getId());
            				   perfilRepository.deleteDetalleLineasCanales(perfil.getId());
            				   perfilRepository.insertLstCentros(perfil.getId(), lstCentros);
            				   cantRegistros = lstCentros.size();
            			   } else {
            				   List<LineaCanal> lstLineasCanales = processExcelLstLineasCanales(codigo, perfil, hojaDetalle.iterator(), lineas, canales);
            				   perfilRepository.deleteDetalleCentros(perfil.getId());
            				   perfilRepository.deleteDetalleLineasCanales(perfil.getId());
            				   perfilRepository.insertLstLineasCanales(perfil.getId(), lstLineasCanales);
            				   cantRegistros = lstLineasCanales.size();
            			   }
            			   logger.info(String.format("FILA %d: Se actualizó el perfil con código '%s' al tipo %s con %d registros.", numFila, codigo, tipoNombre, cantRegistros));
            		   } else {
            			   logger.error(String.format("FILA %d: No se pudo actualizar el perfil con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
            		   }            	   
            	   }
               } else if (accion.equals("ELIMINAR")) {
            	   Perfil perfilBuscado = findByCodigo(codigo);
            	   if (perfilBuscado != null) {
            		   perfilRepository.delete(perfilBuscado);
            		   logger.info(String.format("FILA %d: Se eliminó el perfil con código '%s'.", numFila, codigo));
            	   } else {
            		   logger.error(String.format("FILA %d: No se pudo eliminar el perfil con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
            	   }
               } else {
            	   logger.error(String.format("FILA %d: No se realizó ninguna acción en el perfil con código '%s' porque la acción '%s' no existe.", numFila, codigo, accion));
               }           	
           }
           libro.close();
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
       logger.info("======================FIN DE CARGA DE PERFILES====================================");
	}
	
	private List<Centro> processExcelLstCentros(String hojaNombre, Perfil perfil, Iterator<Row> filas, List<Centro> centros) {
        int numFilasOmitir = 6;
        DataFormatter dataFormatter = new DataFormatter();
        List<Centro> lstCentros = new ArrayList<Centro>();
        boolean hayError = false;
        
        Iterator<Cell> celdas = filas.next().cellIterator();
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilCodigo = dataFormatter.formatCellValue(celdas.next());
        if (perfilCodigo == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El código está vacío.", hojaNombre, 2));
        } else if (!perfilCodigo.equals(perfil.getCodigo())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El código '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 2, perfilCodigo, perfil.getCodigo()));
        }
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilNombre = dataFormatter.formatCellValue(celdas.next());
        if (perfilNombre == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El nombre está vacío.", hojaNombre, 3));
        } else if (!perfilNombre.equals(perfil.getNombre())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El nombre '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 3, perfilNombre, perfil.getNombre()));
        }
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilTipo = dataFormatter.formatCellValue(celdas.next());
        if (perfilTipo == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El tipo está vacío.", hojaNombre, 4));
        } else if (!perfilTipo.equals(perfil.getTipo().getNombre())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El tipo '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 4, perfilTipo, perfil.getTipo().getNombre()));
        }
   		filas.next(); //linea blanco
   		filas.next(); //titulo tabla
   		
   		if (hayError) {
        	return lstCentros;
   		}
   		
        for (int numFila = numFilasOmitir+1; filas.hasNext(); ++numFila) {
     	   	celdas = filas.next().cellIterator();
     	   
            String codigo = dataFormatter.formatCellValue(celdas.next());
            String nombre = dataFormatter.formatCellValue(celdas.next());

            Centro centro = centros.stream().filter(item -> codigo.equals(item.getCodigo())).findAny().orElse(null);
            if (centro == null) {
            	logger.error(String.format("HOJA %s, FILA %d: El centro de costos con código '%s' no existe.", hojaNombre, numFila, codigo));
            	continue;
            } else if (!nombre.equals(centro.getNombre())) {
   				logger.error(String.format("HOJA %s, FILA %d: El centro de costos con nombre '%s' no coincide con el registrado con código '%s'.", hojaNombre, numFila, nombre, codigo));
   				continue;
   			}
            lstCentros.add(centro);
        }
		return lstCentros;
	}
	
	private List<LineaCanal> processExcelLstLineasCanales(String hojaNombre, Perfil perfil, Iterator<Row> filas, List<Objeto> lineas, List<Objeto> canales) {
        int numFilasOmitir = 6;
        DataFormatter dataFormatter = new DataFormatter();
        List<LineaCanal> lstLineasCanales = new ArrayList<LineaCanal>();
        boolean hayError = false;
        
        Iterator<Cell> celdas = filas.next().cellIterator();
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilCodigo = dataFormatter.formatCellValue(celdas.next());
        if (perfilCodigo == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El código está vacío.", hojaNombre, 2));
        } else if (!perfilCodigo.equals(perfil.getCodigo())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El código '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 2, perfilCodigo, perfil.getCodigo()));
        }
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilNombre = dataFormatter.formatCellValue(celdas.next());
        if (perfilNombre == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El nombre está vacío.", hojaNombre, 3));
        } else if (!perfilNombre.equals(perfil.getNombre())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El nombre '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 3, perfilNombre, perfil.getNombre()));
        }
        
        celdas = filas.next().cellIterator();
        celdas.next(); // primera columna
        String perfilTipo = dataFormatter.formatCellValue(celdas.next());
        if (perfilTipo == null) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El tipo está vacío.", hojaNombre, 4));
        } else if (!perfilTipo.equals(perfil.getTipo().getNombre())) {
        	hayError = true;
        	logger.error(String.format("HOJA %s, FILA %d: El tipo '%s' no coincide con '%s', el cual es indicado en la hoja PERFILES.", hojaNombre, 4, perfilTipo, perfil.getTipo().getNombre()));
        }
   		filas.next(); //linea blanco
   		filas.next(); //titulo tabla
   		
   		if (hayError) {
        	return lstLineasCanales;
   		}

        for (int numFila = numFilasOmitir+1; filas.hasNext(); ++numFila) {
     	   	celdas = filas.next().cellIterator();
     	   
            String lineaCodigo = dataFormatter.formatCellValue(celdas.next());
            String lineaNombre = dataFormatter.formatCellValue(celdas.next());
            String canalCodigo = dataFormatter.formatCellValue(celdas.next());
            String canalNombre = dataFormatter.formatCellValue(celdas.next());
            
            Objeto linea = lineas.stream().filter(item -> lineaCodigo.equals(item.getCodigo())).findAny().orElse(null);
            if (linea == null) {
            	logger.error(String.format("HOJA %s, FILA %d: La línea con código '%s' no existe.", hojaNombre, numFila, lineaCodigo));
            	continue;
            } else if (!lineaNombre.equals(linea.getNombre())) {
   				logger.error(String.format("HOJA %s, FILA %d: La línea con nombre '%s' no coincide con la registrada con código '%s'.", hojaNombre, numFila, lineaNombre, lineaCodigo));
   				continue;
   			}
            
            Objeto canal = canales.stream().filter(item -> canalCodigo.equals(item.getCodigo())).findAny().orElse(null);
            if (canal == null) {
            	logger.error(String.format("HOJA %s, FILA %d: El canal con código '%s' no existe.", hojaNombre, numFila, canalCodigo));
            	continue;
            } else if (!canalNombre.equals(canal.getNombre())) {
   				logger.error(String.format("HOJA %s, FILA %d: El canal con nombre '%s' no coincide con el registrado con código '%s'.", hojaNombre, numFila, canalNombre, canalCodigo));
   				continue;
   			}
            lstLineasCanales.add(new LineaCanal(linea, canal));
        }
		return lstLineasCanales;
	}
	
	@Override
	public Resource downloadExcel() {
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet("PERFILES");

        List<Map<String,Object>> data = perfilRepository.findAllList();
        List<Integer> widths = Arrays.asList(3000, 8000, 3000, 3000, 8000, 3000, 8000, 3000, 3000);
        
        if (data == null || data.size() == 0) {
        	data = perfilRepository.findAllListEmpty();
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
    		excelService.createCell(row, colNum++, (String) fila.get("TIPO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION1_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION1_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION2_CODIGO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("DIMENSION2_NOMBRE"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_CREACION"), fechaEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_ACTUALIZACION"), fechaEstilo);
        }
        return excelService.crearResource(wb);
	}
}
