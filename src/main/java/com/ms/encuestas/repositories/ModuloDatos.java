package com.ms.encuestas.repositories;

//import sim.persistencia.*;
//import sim.persistencia.dic.*;

import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.ms.encuestas.LoggerWrapper;
import com.ms.encuestas.models.Centro;


public abstract class ModuloDatos {

	protected static final LoggerWrapper LOGGER = LoggerWrapper.getLogger(ModuloDatos.class);

	// Clase con metodos utiles para queries
	protected static ModuloDatos_Utils utils;

	// Esquema de trabajo
	protected static String esquema;
	protected static final Boolean autocommit = false;
	protected static Integer max_lote;

	// Variables para conexion mediante context en el servidor de aplicaciones
	protected static DataSource ds;
	protected static Boolean conexionContext;

	// Variables para conexion mediante driver (libreria en buildpath del proyecto)
	protected static Driver dbDriver;
	protected static String dsName;
	protected static String driverName;
	protected static String dbUri;
	protected static String userName;

	// Variables para manejar archivos (ficheros)

	protected static Character separador;
	protected static Pattern patron_cabecera;

	/*// Clases Diccionarios que solo seran inicializadas al construirse el modulo de
	// datos, mejor rendimiento
	protected static ArrayList<Pais> dic_paises;
	protected static ArrayList<TipoAmbito> dic_tipo_ambito;
	protected static ArrayList<TipoMagnitud> dic_tipo_magnitud;
	protected static ArrayList<MotorCalculo> dic_motor_calculo;
	protected static ArrayList<TipoObtencionResultados> dic_tipo_obtencion_resultados;
	protected static ArrayList<TipoFlujo> dic_tipo_flujo;
	protected static ArrayList<TipoMetodologia> dic_tipo_metodologia;
	protected static ArrayList<TipoPeriodicidad> dic_tipo_periodicidad;
	protected static ArrayList<TipoVariable> dic_tipo_variable;
	protected static ArrayList<TipoCampo> dic_tipo_campo;
	protected static ArrayList<TipoBloque> dic_tipo_bloque;
	protected static ArrayList<TipoEjecucion> dic_tipo_ejecucion;
	protected static ArrayList<TipoIndicador> dic_tipo_indicador;
	protected static ArrayList<TipoCartera> dic_tipo_cartera;*/

	public ModuloDatos(Properties properties, ModuloDatos_Utils utils) throws Exception {
		LOGGER.info("Creando ModuloDatos");

		// Se establece la clase con metodos utiles para queries
		ModuloDatos.utils = utils;

		// Se busca la conexion a BBDD
		conectarBBDD(properties);

		// Se determina el esquema de BBDD
		esquema = properties.getProperty("JDBC_Schema");

		// Se determina el esquema de BBDD
		userName = properties.getProperty("SAS_JDBC_userName");

		// Numero maximo de filas para ejecutar proceso batch
		max_lote = Integer.valueOf(properties.getProperty("Maximo_Batch"));

		// Se determinan las variables para el manejo de archivos
		separador = properties.getProperty("Separador_Campos_Archivo").charAt(0);
		patron_cabecera = Pattern.compile(properties.getProperty("Patron_Regex_Cabecera"));

		// Inicializar clases diccionarios
		/*obtenerPaisesBD();
		obtenerTiposAmbitoBD();
		obtenerTiposMagnitudBD();
		obtenerMotoresCalculoBD();
		obtenerTiposObtencionResultadosBD();
		obtenerTiposFlujoBD();
		obtenerTiposPeriodicidadBD();
		obtenerTiposVariableBD();
		obtenerTiposCampoBD();
		obtenerTiposBloqueBD();
		obtenerTiposEjecucionBD();
		obtenerTiposIndicadorBD();*/

		LOGGER.info("ModuloDatos creado");
	}

	// Metodos encapsulados
	private void conectarBBDD(Properties properties) throws Exception {
		if (properties.getProperty("Buscar_Conexion_BD") == null) {
			LOGGER.error(
					"No se ha establecido la propiedad Buscar_Conexion_BD, no se ha podido inicializar ModuloDatos");
		} else {
			if (properties.getProperty("Buscar_Conexion_BD").equalsIgnoreCase("DRIVER")) {
				conexionContext = false;
				registrarDataSourceDriver(properties);
			} else if (properties.getProperty("Buscar_Conexion_BD").equalsIgnoreCase("CONTEXT")) {
				conexionContext = true;
				obtenerDataSourceContext(properties);
			} else {
				LOGGER.error("No se ha configurado como DRIVER o CONTEXT la propiedad Buscar_Conexion_BD");
			}
		}
	}
	
	public ArrayList<Centro> obtenerCentros() throws Exception {
		Connection conn = null;
		ArrayList<Centro> ans = new ArrayList<Centro>();
		conn = obtenerConexion();
		ans = obtenerCentros(conn);
		cerrarConexion(conn);
		return ans;
	}
	
	public ArrayList<Centro> obtenerCentros(Connection conn) throws Exception {
		ArrayList<Centro> centros = new ArrayList<Centro>();
		Centro centro = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from centros order by id";
		try {
			stmt = crearStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				centro = new Centro();
				centro.setId(rs.getLong("id"));
				centro.setCodigo(rs.getString("codigo"));
				centro.setNombre(rs.getString("nombre"));
				centro.setAbreviatura(rs.getString("abreviatura"));
				centro.setNivel(rs.getInt("nivel"));
				centros.add(centro);
			}
		} catch (Exception ex) {
			LOGGER.error("Error al obtener escenarios: " + ex.getMessage());
			throw ex;
		} finally {
			cerrarResultSet(rs);
			cerrarStatement(stmt);
		}
		return centros;
	}

	private void registrarDataSourceDriver(Properties properties) throws Exception {
		try {
			// Se leen las propiedades para el DataSource por DRIVER
			driverName = properties.getProperty("JDBC_DriverName");
			dbUri = properties.getProperty("JDBC_DbUri");

			// Se instancia el driver
			ModuloDatos.dbDriver = (Driver) Class.forName(driverName).newInstance();

			// Se registra el driver
			DriverManager.registerDriver(ModuloDatos.dbDriver);
		} catch (Exception e) {
			LOGGER.fatal("Error al obtener datos o registrar el driver: " + driverName + ". Error: " + e.getMessage());
		}
	}

	private void obtenerDataSourceContext(Properties properties) throws Exception {
		try {
			// Se leen las propiedades para el DataSource por CONTEXT
			dsName = properties.getProperty("JDBC_DataSource");

			// Se inicializa el context
			Context ctx = new InitialContext();

			// Se busca el Datasource en el context
			ModuloDatos.ds = (DataSource) ctx.lookup("java:comp/env/jdbc/" + dsName);
			LOGGER.info("DataSource " + dsName + " enlazado satisfactoriamente");
		} catch (Exception e) {
			LOGGER.fatal("Error al intentar obtener el DataSource " + dsName + ": " + e.getMessage());
		}
	}

	protected Connection obtenerConexion() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		// Primero obtener la conexion
		try {
			if (!conexionContext) {
				conn = DriverManager.getConnection(dbUri);
				conn.setAutoCommit(autocommit);
			} else if (conexionContext) {
				conn = ds.getConnection();
				conn.setAutoCommit(autocommit);
			} else {
				LOGGER.warn("Revisar la propiedad Buscar_Conexion_BD");
			}
		} catch (Exception e) {
			LOGGER.fatal("Error obteniendo conexion: " + e.getMessage());
		}
		// Luego alterar la sesion de la conexion para establecer configuracion
		// especifica (por ejemplo el nombre del schema)
		stmt = crearStatement(conn);
		alterarSesion(stmt);
		cerrarStatement(stmt);

		return conn;
	}

	protected void alterarSesion(Statement stmt) throws Exception {
		try {
			stmt.execute("alter session set current_schema=" + esquema);
		} catch (Exception ex) {
			LOGGER.fatal("Error al alterar sesion actual");
		}
	}

	protected void cerrarConexion(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error cerrando conexion: " + e.getMessage() + ". Conexion: " + conn);
		}
	}

	protected Statement crearStatement(Connection conn) throws Exception {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			alterarSesion(stmt);
		} catch (Exception e) {
			LOGGER.fatal("Error al crear el statement: " + e.getMessage());
		}
		return stmt;
	}

	protected void cerrarResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error al cerrar el resultset: " + e.getMessage());
		}
	}

	protected void cerrarStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error al cerrar el statement: " + e.getMessage());
		}
	}

	protected void cerrarPreparedStatement(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error al cerrar el prepared statement: " + e.getMessage());
		}
	}

	protected void ejecutarCommit(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar commit: " + e.getMessage() + ". Conexion: " + conn);
		}
	}

	protected void ejecutarRollback(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar rollback: " + e.getMessage() + ". Conexion: " + conn);
		}
	}

	private static void cerrarBufferedReader(BufferedReader br) {
		try {
			if (br != null) {
				br.close();
			}
		} catch (Exception ex) {
			LOGGER.error("Error al cerrar BufferedReader: " + ex.getMessage());
		}
	}

	// Se utiliza para separar campos que tienen doble comillas para delimitar su
	// contenido de inicio a fin, a fin de diferenciar el separador
	private static ArrayList<String> separarCampos(String texto) throws Exception {
		ArrayList<String> palabras = new ArrayList<String>();
		boolean sin_separador = true;
		int inicio = 0;
		for (int i = 0; i < texto.length() - 1; i++) {
			if (texto.charAt(i) == separador && sin_separador) {
				palabras.add(texto.substring(inicio, i));
				inicio = i + 1;
			} else if (texto.charAt(i) == '"') {
				sin_separador = !sin_separador;
			}
		}
		palabras.add(texto.substring(inicio));
		return palabras;
	}

	protected abstract Integer obtenerSiguienteId(String nombre_tabla) throws Exception;

	/* Fin de metodos encapsulados del modulo de datos */

}
