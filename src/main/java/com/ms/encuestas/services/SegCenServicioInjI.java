package com.ms.encuestas.services;

import com.ms.encuestas.services.segcen.ISegCenServicios;

public interface SegCenServicioInjI {
	public String validarUsuarioApp(String strPUsuario, String strPContrasenia, String strPCodigoAplicacion, Integer intPMayor, Integer intPMinor, Integer intPVersion, String strPIP, String strPHostName);
	public ISegCenServicios getSegCenServicios();
	public void setSegCenServicios(ISegCenServicios segCenServicios);
}
