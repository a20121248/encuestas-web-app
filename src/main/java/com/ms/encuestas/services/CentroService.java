package com.ms.encuestas.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.JustificacionRepository;
import com.ms.encuestas.repositories.TipoRepository;

@Service
public class CentroService implements CentroServiceI {
	private Logger logger = LoggerFactory.getLogger(CentroService.class);
	
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
}
