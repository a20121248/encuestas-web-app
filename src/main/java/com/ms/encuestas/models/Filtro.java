package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.List;

public class Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	Proceso proceso;
	List<Centro> centros;
	List<Area> areas;

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public List<Centro> getCentros() {
		return centros;
	}

	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}
