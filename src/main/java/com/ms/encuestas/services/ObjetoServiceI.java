package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Objeto;

public interface ObjetoServiceI {
	public Long countLineas();
	public Long countCanales();
	public Long countProductos();
	public Long countSubcanales();
	public List<Objeto> findAllLineas();
	public List<Objeto> findAllCanales();
	public List<Objeto> findAllProductos();
	public List<Objeto> findAllSubcanales();
	public void deleteAllLineas();
	public void deleteAllCanales();
	public void deleteAllProductos();
	public void deleteAllSubcanales();
	public Objeto insertLinea(Objeto objeto);
	public Objeto insertCanal(Objeto objeto);
	public Objeto insertProducto(Objeto objeto);
	public Objeto insertSubcanal(Objeto objeto);
	public Objeto updateLinea(Objeto objeto);
	public Objeto updateCanal(Objeto objeto);
	public Objeto updateProducto(Objeto objeto);
	public Objeto updateSubcanal(Objeto objeto);
	public Objeto findById(Long id);
	public void deleteById(Long id);
	public Objeto softDelete(Objeto objeto);
	public Objeto softUndelete(Objeto objeto);
}
