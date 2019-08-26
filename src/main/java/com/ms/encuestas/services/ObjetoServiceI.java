package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Objeto;

public interface ObjetoServiceI {
	public Long count(Long objetoTipoId);
	public List<Objeto> findAll(Long objetoTipoId);
	public Objeto findById(Long id);
	public int save(Objeto objeto);
	public void delete(Objeto objeto);
	public void deleteById(Long id);
}
