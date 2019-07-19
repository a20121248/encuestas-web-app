package com.ms.encuestas.models.custom;

import java.io.Serializable;
import java.util.Date;

public class UsuarioDatos implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombreCompleto;
	private String posicionCodigo;
	private String posicionNombre;
	private String areaNombre;
	private String centroCodigo;
	private String centroNombre;
	private int centroNivel;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getPosicionCodigo() {
		return posicionCodigo;
	}

	public void setPosicionCodigo(String posicionCodigo) {
		this.posicionCodigo = posicionCodigo;
	}

	public String getPosicionNombre() {
		return posicionNombre;
	}

	public void setPosicionNombre(String posicionNombre) {
		this.posicionNombre = posicionNombre;
	}

	public String getAreaNombre() {
		return areaNombre;
	}

	public void setAreaNombre(String areaNombre) {
		this.areaNombre = areaNombre;
	}

	public String getCentroCodigo() {
		return centroCodigo;
	}

	public void setCentroCodigo(String centroCodigo) {
		this.centroCodigo = centroCodigo;
	}

	public String getCentroNombre() {
		return centroNombre;
	}

	public void setCentroNombre(String centroNombre) {
		this.centroNombre = centroNombre;
	}

	public int getCentroNivel() {
		return centroNivel;
	}

	public void setCentroNivel(int centroNivel) {
		this.centroNivel = centroNivel;
	}
}
