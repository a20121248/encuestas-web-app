package com.ms.encuestas.repositories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import com.ms.encuestas.LoggerWrapper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModuloDatos_Factory {
	private static final LoggerWrapper LOGGER = LoggerWrapper.getLogger(ModuloDatos_Factory.class);

	public ModuloDatos_Factory() {
	}

	public ModuloDatos getMD(Properties properties) {
		ModuloDatos md = null;
		try {
			Class clase_md = null;
			clase_md = Class.forName(properties.getProperty("ModuloDatosClassName"));
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = Properties.class;
			Constructor constructor_md = clase_md.getConstructor(parameterTypes);
			Object[] parameterValues = new Object[1];
			parameterValues[0] = properties;
			md = (ModuloDatos) constructor_md.newInstance(parameterValues);
		} catch (ClassNotFoundException ex) {
			LOGGER.error("Error clase no encontrada Modulo de Datos: " + ex.getMessage());
		} catch (NoSuchMethodException ex) {
			LOGGER.error("Error no existe metodo Modulo de Datos: " + ex.getMessage());
		} catch (SecurityException ex) {
			LOGGER.error("Error de seguridad Modulo de Datos: " + ex.getMessage());
		} catch (InstantiationException ex) {
			LOGGER.error("Error de instanciamiento Modulo de Datos: " + ex.getMessage());
		} catch (IllegalAccessException ex) {
			LOGGER.error("Error acceso incorrecto Modulo de Datos: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error arugmentos incorrectos Modulo de Datos: " + ex.getMessage());
		} catch (InvocationTargetException ex) {
			LOGGER.error("Error clase no Encontrada Modulo de Datos: " + ex.getMessage());
		}
		return md;
	}
}