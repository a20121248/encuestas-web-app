package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.List;

public class EncuestaCanal implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Empresa> lstItems;
	private Justificacion justificacion;
	private String observaciones;

	public List<Empresa> getLstItems() {
		return lstItems;
	}

	public void setLstItems(List<Empresa> lstItems) {
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
