package com.ms.encuestas.services;

public interface SegCenServicioInjI {
	public String validarUsuarioApp(String strPUsuario, String strPContrasenia, String strPCodigoAplicacion, Integer intPMayor, Integer intPMinor, Integer intPVersion, String strPIP, String strPHostName);
}
