package com.ms.encuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.encuestas.models.Rol;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.repositories.RolRepository;
import com.ms.encuestas.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService, UsuarioServiceI {
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private PosicionRepository posicionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String usuarioCodigo) {
		return usuarioRepository.findUsuariosDependientesByCodigo(procesoId, usuarioCodigo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String codigo) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByCodigo(codigo);
		
		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario " + codigo +" en el sistema.");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario " + codigo +" en el sistema.");
		}		
		
		usuario.setLstRoles(rolRepository.findAllByUsuarioCodigo(codigo));
		List<GrantedAuthority> authorities = usuario.getLstRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> logger.info("Rol: " + authority.getAuthority()))
				.collect(Collectors.toList());
	
		return new User(usuario.getCodigo(), usuario.getContrasenha(), true, true, true, true, authorities);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return usuarioRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigo(String codigo) {
		try {
			return usuarioRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos.", codigo));
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigoAndProceso(String codigo, Long procesoId) {
		return usuarioRepository.findByCodigoAndProceso(codigo, procesoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId) {
		return usuarioRepository.findByPosicionCodigo(posicionCodigo, procesoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigoWithPosicion(String codigo) {
		return usuarioRepository.findByCodigoWithPosicion(codigo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigoWithPosicionFull(String codigo) {
		Usuario usuario = usuarioRepository.findByCodigoWithPosicion(codigo);
		usuario.setPosicion(posicionRepository.findByCodigoWithAreaAndCentro(usuario.getPosicion().getCodigo()));
		return usuario;
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(String codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GrantedAuthority> getRolesByCodigo(String codigo) {
		List<Rol> roles = usuarioRepository.findRolesByCodigo(codigo);
		if (roles != null && !roles.isEmpty()) {
			return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Usuario findByUsuarioGenerales(String usuarioRed) {
		try {
			return usuarioRepository.findByUsuarioGenerales(usuarioRed);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos de Generales.", usuarioRed));
			return null;
		}
	}

	@Override
	public Usuario findByUsuarioVida(String usuarioRed) {
		try {
			return usuarioRepository.findByUsuarioVida(usuarioRed);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos de Vida.", usuarioRed));
			return null;
		}
	}
}
