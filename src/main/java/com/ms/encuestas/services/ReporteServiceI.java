package com.ms.encuestas.services;

import org.springframework.core.io.Resource;

public interface ReporteServiceI {
	public Resource generarReporteControl(Long procesoId, String fileName);
}
