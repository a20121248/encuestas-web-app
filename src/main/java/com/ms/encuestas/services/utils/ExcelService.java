package com.ms.encuestas.services.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.encuestas.models.Justificacion;
import com.ms.encuestas.repositories.JustificacionRepository;
import com.ms.encuestas.services.UsuarioService;

@Service
public class ExcelService implements ExcelServiceI {
	private Logger logger = LoggerFactory.getLogger(ExcelService.class);

	@Override
	@Transactional(readOnly = true)
	public int crearArchivo(SXSSFWorkbook wb, String rutaArchivo) {		
        try {
	        FileOutputStream out = new FileOutputStream(rutaArchivo);
	        wb.write(out);
	        out.close();
	        wb.dispose();
	        return 1;
        } catch (IOException ex) {
        	logger.info(ex.getMessage());
        	return 0;
        }
	}
}
