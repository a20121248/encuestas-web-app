package com.ms.encuestas.repositories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public abstract class ModuloDatos_Utils {

    protected static Properties properties;

    protected ModuloDatos_Utils(Properties properties) {
        ModuloDatos_Utils.properties = properties;
    }

    public static java.sql.Date getDate(String date_s) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat(properties.getProperty("Patron_Fechas"));
        Date today = dt.parse(date_s);
        return new java.sql.Date(today.getTime());
    }

    public static Double formatDecimal(String value) {
        String separadorDecimal = properties.getProperty("SeparadorDecimal");
        Double valor = 0.0;
        if (separadorDecimal != null && value != null) {
            if (separadorDecimal.equalsIgnoreCase(",")) {
                valor = Double.parseDouble(value.replace(".", ","));
            } else {
                valor = Double.parseDouble(value.replace(",", "."));
            }
        }
        return valor;
    }

    public static String getDateFormat(java.sql.Date date_s) throws ParseException {
        // Create an instance of ateFormat used for formatting 
        DateFormat df = new SimpleDateFormat(properties.getProperty("Patron_Fechas"));
        String reportDate;
        if (date_s != null) {
            reportDate = df.format(date_s);
        } else {
            reportDate = null;
        }
        return reportDate;
    }

    public static String getDateFormat(Date date_s) throws ParseException {
        // Create an instance of ateFormat used for formatting 
        DateFormat df = new SimpleDateFormat(properties.getProperty("Patron_Fechas"));
        String reportDate;
        if (date_s != null) {
            reportDate = df.format(date_s);
        } else {
            reportDate = null;
        }
        return reportDate;
    }

    public static String getDateRegFormat(java.sql.Date date_s) throws ParseException {
        // Create an instance of ateFormat used for formatting 
        DateFormat df = new SimpleDateFormat(properties.getProperty("Patron_Fechas_Java"));
        String reportDate;
        if (date_s != null) {
            reportDate = df.format(date_s);
        } else {
            reportDate = null;
        }
        return reportDate;
    }

    public static String getDateRegFormat(Date date_s) throws ParseException {
        // Create an instance of ateFormat used for formatting 
        DateFormat df = new SimpleDateFormat(properties.getProperty("Patron_Fechas_Java"));
        String reportDate;
        if (date_s != null) {
            reportDate = df.format(date_s);
        } else {
            reportDate = null;
        }
        return reportDate;
    }

    public double formateaNumDeci(double numero) {
        double num = numero / Integer.parseInt(properties.getProperty("Factor_visualizar"));
        return num;
    }

    public abstract String formatear_fecha(String campo, String formato);

    public abstract String concat(String valor1, String valor2);

    public abstract String a_date(String cadena, String formato);

    public abstract String a_overflow(String campo, int posiciones, int decimales);

    public abstract String encomillar_flag(int flag);

    public abstract String a_char(String fecha, String formato);

    public abstract String a_number(String campo);

    public abstract String a_distinto();

    public abstract String formateaNumeros(double numero, int numeroDecimales, String separador);

    public abstract String a_char(String cadena, int longitud);

    public abstract String Get_Consulta_Top(String par_consulta, int num);

    public abstract String a_null(String consulta);

    public abstract String encomillar(int valor_flag);

    public abstract int compareTo(String s1, String s2);

    public abstract String Fecha_As_Int(String fecha, String formato);

    public abstract String Fecha_As_Char(String fecha, String formato);

    public abstract String Suma_dias_A_Fecha(String campo_fecha, String numero);

    public abstract String Suma_Mes_A_Fecha(String campo_fecha, String numero);

    public abstract String diferencia_fecha_dias(String fecha_inicio, String fecha_fin);

    public abstract String Limitar_Decimales();

    public abstract String a_date2(String cadena, String formato);

    public abstract String convert_CLOB(String campo); 
    

}