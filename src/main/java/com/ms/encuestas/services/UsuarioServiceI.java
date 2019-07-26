package com.ms.encuestas.services;

import java.util.List;

import com.ms.encuestas.models.Usuario;

public interface UsuarioServiceI {
	public Long count();
	public List<Usuario> findAll();
	public Usuario findByCodigo(String codigo);
	public Usuario findByCodigoWithPosicion(String codigo);
	public Usuario findByCodigoWithPosicionFull(String codigo);
	public Usuario save(Usuario usuario);
	public void delete(Usuario usuario);
	public void deleteById(String codigo);
	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String usuarioCodigo);
}
