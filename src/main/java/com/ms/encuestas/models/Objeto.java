package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;

public class Objeto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String nombre;
	private Objeto objetoPadre;
	private double porcentaje;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private Date fechaEliminacion;
	private boolean estado;

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
	
	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
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
	
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
}
