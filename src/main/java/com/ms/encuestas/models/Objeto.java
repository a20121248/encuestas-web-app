package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;

public class Objeto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String nombre;
	private Objeto objetoPadre;
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

	public Objeto getObjetoPadre() {
		return objetoPadre;
	}

	public void setObjetoPadre(Objeto objetoPadre) {
		this.objetoPadre = objetoPadre;
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
