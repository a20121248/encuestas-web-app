package com.ms.encuestas.services;

import org.springframework.core.io.Resource;

import com.ms.encuestas.models.Filtro;

public interface ReporteServiceI {
	public Resource generarReporteControl(Filtro filtro);
	public Resource generarReporteEmpresas(Filtro filtro);
	public Resource generarReporteConsolidado(Filtro filtro);
}
