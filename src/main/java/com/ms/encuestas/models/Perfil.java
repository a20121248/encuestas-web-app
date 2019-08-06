package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private String descripcion;
	private Tipo perfilTipo;
	private List<Centro> lstCentros;
	private List<ObjetoObjetos> lstLineasCanales;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Tipo getPerfilTipo() {
		return perfilTipo;
	}

	public void setPerfilTipo(Tipo perfilTipo) {
		this.perfilTipo = perfilTipo;
	}
	
	public List<Centro> getLstCentros() {
		return lstCentros;
	}

	public void setLstCentros(List<Centro> lstCentros) {
		this.lstCentros = lstCentros;
	}

	public List<ObjetoObjetos> getLstLineasCanales() {
		return lstLineasCanales;
	}

	public void setLstLineasCanales(List<ObjetoObjetos> lstLineasCanales) {
		this.lstLineasCanales = lstLineasCanales;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}
