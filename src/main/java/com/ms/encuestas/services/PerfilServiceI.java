package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import com.ms.encuestas.models.Perfil;

public interface PerfilServiceI {
	public Long count();
	public List<Perfil> findAll();
	public Perfil findById(Long id);
	public int save(Perfil perfil);
	public void delete(Perfil perfil);
	public void deleteById(Long id);
	public void processExcel(InputStream file);
}
