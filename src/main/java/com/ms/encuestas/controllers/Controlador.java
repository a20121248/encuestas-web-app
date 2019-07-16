package com.ms.encuestas.controllers;

//import sim.autenticacion.ModuloLDAP;
import com.ms.encuestas.repositories.ModuloDatos;
import com.ms.encuestas.repositories.ModuloDatos_Factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Properties;

import com.ms.encuestas.LoggerWrapper;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.Usuario;

//JJWT
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;

public class Controlador {
	private static final LoggerWrapper LOGGER = LoggerWrapper.getLogger(Controlador.class);
	private static Properties properties;
	private static Controlador controlador;
	private static ModuloDatos md;

	//private static ModuloLDAP ldap;
	
	private static String developer_role;
	private static String developer_role_inv;

	public Controlador() throws Exception {

		LOGGER.info("Creando controlador");

		// Se obtiene el archivo de propiedades del sistema
		InputStream is = null;
		try {
			properties = new Properties();
			LOGGER.info("Obteniendo archivo de propiedades");
			is = getClass().getResourceAsStream("../ms.properties");
			LOGGER.info("Archivo propiedades obtenido como recurso stream");
			properties.load(is);
		} catch (Exception e) {
			LOGGER.fatal("No se ha podido cargar el fichero de propiedades correctamente. Error: " + e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error al cerrar el InputStream del path properties. Error: " + e.getMessage());
			}
		}

		// Se crea el modulo de datos
		try {
			md = new ModuloDatos_Factory().getMD(properties);
		} catch (Exception e) {
			LOGGER.fatal("Error al crear ModuloDatos: " + e.getMessage());
		}

		/*// Se crea el modulo de LDAP
		try {
			if (properties.getProperty("Modulo_LDAP").equalsIgnoreCase("SI")) {
				ldap = new ModuloLDAP(properties);
			} else {
				LOGGER.warn(
						"Modulo LDAP desactivado, si se desea activar cambiar la propiedad a SI y reiniciar el servicio");
			}
		} catch (Exception e) {
			LOGGER.fatal("Error al crear Modulo LDAP: " + e.getMessage());
		}*/
	
		LOGGER.info("Controlador creado");

		if (properties.getProperty("Developer") != null) {
			developer_role = properties.getProperty("Developer_Role");
			developer_role_inv = properties.getProperty("Developer_Role_Inv");
		}
	}

	public static synchronized Controlador obtenerInstancia() throws Exception {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	// Comprobar si un Array contiene lo mismo que otro Array (no importa orden ni
	// duplicados)
	private static <T> boolean listEqualsIgnoreOrder(ArrayList<T> list1, ArrayList<T> list2) {
		return new HashSet<>(list1).equals(new HashSet<>(list2));
	}

	// Autenticacion
	public Usuario loguearUsuario(Usuario usuario) throws Exception {
		/*Usuario usuarioRet = null;
		//Perfil perfilUsuario = null;
		Long expiracion = null;

		try {
			// Logueo en AD - LDAP.......
			usuarioRet = new Usuario();
			if (properties.getProperty("Developer") != null
					&& properties.getProperty("Developer").equalsIgnoreCase("SI")
					&& usuario.getId_usuario().equals("admin") && usuario.getContrasena().equals("admin")) {
				usuarioRet.setNombre_usuario("Developer User");
				perfilUsuario = md.obtenerPerfilConPermisosPorNombreAD(developer_role);
				usuarioRet.setPerfil_usuario(perfilUsuario);
			} else if (properties.getProperty("Developer") != null
					&& properties.getProperty("Developer").equalsIgnoreCase("SI")
					&& usuario.getId_usuario().equals("admininv") && usuario.getContrasena().equals("admininv")) {
				usuarioRet.setNombre_usuario("Developer User (Inv.)");
				perfilUsuario = md.obtenerPerfilConPermisosPorNombreAD(developer_role_inv);
				usuarioRet.setPerfil_usuario(perfilUsuario);
			} else {
				usuarioRet = ldap.validarUsuario(usuario);
				perfilUsuario = md
						.obtenerPerfilConPermisosPorNombreAD(usuarioRet.getPerfil_usuario().getNombre_perfil_ad());
				usuarioRet.setPerfil_usuario(perfilUsuario);
			}

			// Generacion del token
			expiracion = System.currentTimeMillis() + Integer.valueOf(properties.getProperty("LifetimeToken"));

			String token = Jwts.builder().setSubject("APP_SIMIFRS9").setIssuer(usuarioRet.getId_usuario())
					.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(expiracion))
					.signWith(SignatureAlgorithm.HS512, "secretpass").compact();

			usuarioRet.setToken(token);
			usuarioRet.setExpiracion(expiracion);

		} catch (Exception ex) {
			throw ex; // Invalid Signing configuration / Couldn't convert Claims.
		}

		return usuarioRet;*/
		return null;
	}
	
	// Métodos de Centros
	public ArrayList<Centro> obtenerCentros() throws Exception {
		return md.obtenerCentros();
	}

	// Administracion sistema
	// Metodos de tarea

	// Perfil y Rol
/*
	public void crearPerfil(Perfil perfil) throws Exception {
		md.crearPerfil(perfil);
	}

	public void editarPerfil(Perfil perfil) throws Exception {
		md.editarPerfil(perfil);
	}

	public void borrarPerfil(Integer id_perfil) throws Exception {
		md.borrarPerfil(id_perfil);
	}

	public ArrayList<Perfil> obtenerPerfiles() throws Exception {
		return md.obtenerPerfiles();
	}

	public ArrayList<Permiso> obtenerPermisos(Integer id_tipo_ambito) throws Exception {
		return md.obtenerPermisos(id_tipo_ambito);
	}

	public ArrayList<Perfil> obtenerPerfilesPermisos() throws Exception {
		return md.obtenerPerfilesPermisos();
	}

	public ArrayList<Permiso> obtenerPermisosPorIdPerfil(Integer id_perfil) throws Exception {
		return md.obtenerPermisosPorIdPerfil(id_perfil);
	}
*/
	
}