package com.ms.encuestas.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;

import com.ms.encuestas.models.Usuario;

public interface UsuarioServiceI {
	public Long count();
	public List<String> findAllCodigos();
	public List<Usuario> findAll();
	public Usuario findByUsuarioGenerales(String usuario);
	public Usuario findByUsuarioVida(String usuario);
	public Usuario findByCodigo(String codigo);
	public Usuario findByCodigoAndProceso(String codigo, Long procesoId);
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId);
	public Usuario findByCodigoWithPosicion(String codigo);
	public Usuario findByCodigoWithPosicionFull(String codigo);
	public Usuario save(Usuario usuario);
	public void delete(Usuario usuario);
	public void deleteById(String codigo);
	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String usuarioCodigo);
	List<GrantedAuthority> getRolesByCodigo(String codigo);
	public void processExcel(InputStream file);
	public Resource downloadExcel();
}
