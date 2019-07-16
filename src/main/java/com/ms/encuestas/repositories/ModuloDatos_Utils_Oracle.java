package com.ms.encuestas.repositories;

import java.util.Properties;

public class ModuloDatos_Utils_Oracle extends ModuloDatos_Utils {

	protected ModuloDatos_Utils_Oracle(Properties properties) {
		super(properties);
	}

	@Override
	public String formatear_fecha(String campo, String formato) {
		if (formato.equals("YYYY")) {
			return "to_char(" + campo + ",'YYYY')";
		} else if (formato.equals("MM")) {
			return "to_char(" + campo + ",'MM')";
		} else if (formato.equals("YYYYMM")) {
			return "to_char(" + campo + ",'YYYYMM')";
		} else {
			return null;
		}
	}

	@Override
	public String concat(String valor1, String valor2) {
		if (valor1 == null && valor2 == null) {
			return "";
		}
		if (valor1 == null) {
			return valor2;
		}
		if (valor2 == null) {
			return valor1;
		}
		return " concat(" + valor1 + "," + valor2 + ") ";

	}

	@Override
	public String a_date(String cadena, String formato) {
		if (formato.equalsIgnoreCase("DD-mm-YYYY")) {
			return "to_date('" + cadena + "','" + formato + "')";
		} else {
			return "to_date('" + cadena + "','" + formato + "')";
		}
	}

	@Override
	public String a_overflow(String campo, int posiciones, int decimales) {
		return "to_binary_float(" + campo + ")";
	}

	@Override
	public String encomillar_flag(int flag) {
		return "" + flag + "";

	}

	@Override
	public String a_char(String fecha, String formato) {
		return "to_char(" + fecha + ",'" + formato + "')";
	}

	@Override
	public String a_number(String campo) {
		return "to_number(" + campo + ")";
	}

	@Override
	public String a_distinto() {
		return "!=";
	}

	@Override
	public String formateaNumeros(double numero, int numeroDecimales, String separador) {
		return "" + numero;
	}

	@Override
	public String a_char(String cadena, int longitud) {
		return "" + cadena;
	}

	@Override
	public String Get_Consulta_Top(String par_consulta, int num) {
		return "select * from (" + par_consulta + ") where rownum <= " + num;
	}

	@Override
	public String a_null(String consulta) {
		return consulta;

	}

	@Override
	public String encomillar(int valor_flag) {
		return "'" + valor_flag + "' ";
	}

	@Override
	public int compareTo(String s1, String s2) {
		return s1.compareTo(s2);
	}

	@Override
	public String Fecha_As_Int(String fecha, String formato) {
		String cadena = "";
		if (formato.equalsIgnoreCase("YYYY")) {
			cadena = " cast( " + Fecha_As_Char(fecha, formato) + ")as int) ";
		}
		if (formato.equalsIgnoreCase("YYYYMM")) {
			cadena = " cast( " + Fecha_As_Char(fecha, formato) + ")as int) ";
		}
		if (formato.equalsIgnoreCase("YYYYMMDD")) {
			cadena = " cast( " + Fecha_As_Char(fecha, formato) + ")as int) ";
		}
		return cadena;
	}

	@Override
	public String Fecha_As_Char(String fecha, String formato) {
		String cadena = "";
		cadena = " to_char(" + fecha + ",'" + formato + "') ";
		return cadena;
	}

	@Override
	public String Suma_Mes_A_Fecha(String campo_fecha, String numero) {
		String cadena = "  ADD_MONTHS ( " + campo_fecha + ", " + numero + ") ";
		return cadena;
	}

	@Override
	public String Suma_dias_A_Fecha(String campo_fecha, String numero) {
		String cadena = "  ( " + campo_fecha + " + ( " + numero + ")) ";
		return cadena;
	}

	@Override
	public String diferencia_fecha_dias(String fecha_inicio, String fecha_fin) {
		return "coalesce((" + fecha_fin + " - " + fecha_inicio + ") ,0)";
	}

	@Override
	public String Limitar_Decimales() {
		return null;
	}

	@Override
	public String a_date2(String cadena, String formato) {
		return this.a_date(cadena, formato);
	}

	@Override
	public String convert_CLOB(String campo) {
		return "to_char(" + campo + ")";
	}

}
