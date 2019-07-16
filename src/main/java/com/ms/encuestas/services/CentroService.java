package com.ms.encuestas.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Centro;
import com.ms.encuestas.repositories.CentroRepository;
import com.ms.encuestas.repositories.ModuloDatos;
import com.ms.encuestas.repositories.ModuloDatos_Factory;


@Service
public class CentroService implements CentroServiceI {	
	private static ModuloDatos md;
	private static Properties properties;
    @Autowired
    private CentroRepository centroRepository;

	
	public CentroService() {
		/*InputStream is = null;
		try {
			properties = new Properties();
			//LOGGER.info("Obteniendo archivo de propiedades");
			is = getClass().getResourceAsStream("../ms.properties");
			//LOGGER.info("Archivo propiedades obtenido como recurso stream");
			properties.load(is);
		} catch (Exception e) {
			//LOGGER.fatal("No se ha podido cargar el fichero de propiedades correctamente. Error: " + e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				//LOGGER.error("Error al cerrar el InputStream del path properties. Error: " + e.getMessage());
			}
		}
		
		// Se crea el modulo de datos
		try {
			md = new ModuloDatos_Factory().getMD(properties);
		} catch (Exception e) {
			//LOGGER.fatal("Error al crear ModuloDatos: " + e.getMessage());
		}*/

		
	}
	
	public List<Centro> findAll() throws Exception {
		return centroRepository.findAll();
		//return md.obtenerCentros();
	}

	public Centro findById(Long id) {
		return centroRepository.findById(id);
	}
	
	public Centro findByCodigo(String codigo) {
		return centroRepository.findByCodigo(codigo);
	}
	
	public Centro save(Centro centro) {
		return null;
	}
	
	public void delete(Centro centro) {
		return;
	}
}
