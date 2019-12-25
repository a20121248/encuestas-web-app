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
	public Usuario findByUsuarioRed(String usuarioRed);
	public Usuario findByCodigo(String codigo);
	public Usuario findByCodigoAndProceso(String codigo, Long procesoId);
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId);
	public Usuario findByCodigoWithPosicion(String codigo);
	public Usuario insert(Usuario usuario);
	public Usuario update(Usuario usuario);
	public void deleteByCodigo(String codigo);
	public Usuario softDelete(Usuario usuario);
	public Usuario softUndelete(Usuario usuario);
	public List<Usuario> findUsuariosDependientes(Long procesoId, String usuarioCodigo);
	public List<Usuario> findUsuariosDependientesCompletados(Long procesoId, String usuarioCodigo);
	public List<Usuario> findUsuariosDependientesReplicar(Long procesoId, String posicionCodigo, String responsablePosicionCodigo, Long perfilId);
	List<GrantedAuthority> getRolesByCodigo(String codigo);
	public void processExcel(InputStream file);
	public Resource downloadExcel();
	public void deleteAll();
}
