package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.List;

public class Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	private Proceso proceso;
	private List<Centro> centros;
	private List<Area> areas;
	private List<Tipo> estados;

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
	
	public List<Tipo> getEstados() {
		return estados;
	}

	public void setEstados(List<Tipo> estados) {
		this.estados = estados;
	}
}
