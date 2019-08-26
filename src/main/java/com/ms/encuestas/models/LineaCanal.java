package com.ms.encuestas.models;

import java.io.Serializable;

public class LineaCanal implements Serializable {
	private static final long serialVersionUID = 1L;
	private Objeto linea;
	private Objeto canal;

	public LineaCanal(Objeto linea, Objeto canal) {
		this.linea = linea;
		this.canal = canal;
	}

	public Objeto getLinea() {
		return linea;
	}

	public void setLinea(Objeto linea) {
		this.linea = linea;
	}

	public Objeto getCanal() {
		return canal;
	}

	public void setCanal(Objeto canal) {
		this.canal = canal;
	}
}
