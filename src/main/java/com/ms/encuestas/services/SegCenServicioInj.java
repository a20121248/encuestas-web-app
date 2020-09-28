package com.ms.encuestas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ms.encuestas.services.segcen.ISegCenServicios;
import com.ms.encuestas.services.segcen.SegCenServicio;

@Service
public class SegCenServicioInj implements SegCenServicioInjI {
	private Logger logger = LoggerFactory.getLogger(SegCenServicioInj.class);
	SegCenServicio segCenServicio;
	ISegCenServicios segCenServicios;
	
	public SegCenServicioInj() {
		logger.info("Ejecutando constructor de SegCenServicio");
		try {
			segCenServicio = new SegCenServicio();
	    	segCenServicios = segCenServicio.getBasicHttpBindingISegCenServicios();			
		} catch(javax.xml.ws.WebServiceException e) {
    		logger.error("No se pudo conectar al servicio de directorio activo.");
    		logger.error(e.getMessage());
    		//throw new AuthenticationServiceException("No se pudo conectar al servicio de directorio activo.");
    	} catch(Exception ex) {
    		logger.error("No se pudo conectar al servicio de directorio activo. Exception");
    		logger.error(ex.getMessage());
    	}
	}
	
	@Override
	public String validarUsuarioApp(String strPUsuario, String strPContrasenia, String strPCodigoAplicacion,
			Integer intPMayor, Integer intPMinor, Integer intPVersion, String strPIP, String strPHostName) {
		String response = segCenServicios.validarUsuarioApp(strPUsuario, strPContrasenia, strPCodigoAplicacion, intPMayor, intPMinor, intPVersion, strPIP, strPHostName);
    	return response;
	}

}
