package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;

public class Division implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private Date fechaCreacion;

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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
