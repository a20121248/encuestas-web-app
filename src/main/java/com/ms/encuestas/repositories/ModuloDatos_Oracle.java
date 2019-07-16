package com.ms.encuestas.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ModuloDatos_Oracle extends ModuloDatos {

    private static ModuloDatos_Utils_Oracle utils_singleton;

    public ModuloDatos_Oracle(Properties properties) throws Exception {
        super(properties, getInstance(properties));
    }

    private static ModuloDatos_Utils_Oracle getInstance(Properties properties) throws Exception {
        utils_singleton = new ModuloDatos_Utils_Oracle(properties);
        return utils_singleton;
    }
    
    protected Integer obtenerSiguienteId(String nombre_sequencia) throws Exception {
    	int siguiente_id = 0;
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
		String sql = "Select " + nombre_sequencia + ".NEXTVAL from dual";		
		try {
			conn = obtenerConexion();
			stmt = crearStatement(conn);
			rs = stmt.executeQuery(sql);
			
			rs.next();
			siguiente_id = rs.getInt("NEXTVAL");
			
		} catch (Exception ex) {
			ejecutarRollback(conn);
			LOGGER.error("Error al obtener siguiente id del sequence: " + nombre_sequencia + " : " + ex.getMessage());
		} finally {
			cerrarResultSet(rs);
			cerrarStatement(stmt);
			cerrarConexion(conn);
		}		
    	return siguiente_id;
    }
}