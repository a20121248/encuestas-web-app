package com.ms.encuestas.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable/*, UserDetails*/ {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String usuarioVida;
	private String usuarioGenerales;
	private String contrasenha;
	private String nombreCompleto;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private List<Rol> lstRoles;
	private Posicion posicion;
	private boolean estado;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getUsuarioVida() {
		return usuarioVida;
	}

	public void setUsuarioVida(String usuarioVida) {
		this.usuarioVida = usuarioVida;
	}

	public String getUsuarioGenerales() {
		return usuarioGenerales;
	}

	public void setUsuarioGenerales(String usuarioGenerales) {
		this.usuarioGenerales = usuarioGenerales;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public List<Rol> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	} 

	
	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return contrasenha;
	}

	@Override
	public String getUsername() {
		return codigo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}*/
}
