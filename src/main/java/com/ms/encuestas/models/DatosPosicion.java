package com.ms.encuestas.models;

import java.io.Serializable;

public class DatosPosicion implements Serializable {
	private static final long serialVersionUID = 1L;
	private Posicion posicion;
	private Usuario usuario;
	private Area area;
	private Centro centro;
	private Perfil perfil;
	private Posicion responsablePosicion;

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Posicion getResponsablePosicion() {
		return responsablePosicion;
	}

	public void setResponsablePosicion(Posicion responsablePosicion) {
		this.responsablePosicion = responsablePosicion;
	}
}
