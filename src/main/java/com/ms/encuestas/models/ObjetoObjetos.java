package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ObjetoObjetos implements Serializable {
	private static final long serialVersionUID = 1L;
	private Objeto objeto;
	private List<Objeto> lstObjetos;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public List<Objeto> getLstObjetos() {
		return lstObjetos;
	}

	public void setLstObjetos(List<Objeto> lstObjetos) {
		this.lstObjetos = lstObjetos;
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
