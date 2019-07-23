package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String contrasenha;
	private String nombre;
	private String nombreCompleto;
	private Date fechaCreacion;
	private List<Rol> lstRoles;
	private Posicion posicion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<Rol> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
}
