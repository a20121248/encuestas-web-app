package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.List;

public class EncuestaObjeto implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Objeto> lstItems;
	private Justificacion justificacion;
	private String observaciones;

	public List<Objeto> getLstItems() {
		return lstItems;
	}

	public void setLstItems(List<Objeto> lstItems) {
		this.lstItems = lstItems;
	}

	public Justificacion getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(Justificacion justificacion) {
		this.justificacion = justificacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
