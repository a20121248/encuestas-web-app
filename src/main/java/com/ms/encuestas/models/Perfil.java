package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String nombre;
	private Tipo tipo;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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
